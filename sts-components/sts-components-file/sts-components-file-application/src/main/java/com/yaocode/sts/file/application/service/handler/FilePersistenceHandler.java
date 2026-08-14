package com.yaocode.sts.file.application.service.handler;

import com.yaocode.sts.common.basic.enums.EnableEnums;
import com.yaocode.sts.common.basic.enums.YesNoEnums;
import com.yaocode.sts.common.tools.messages.MessageUtils;
import com.yaocode.sts.file.core.constants.FileI18nKeyConstants;
import com.yaocode.sts.file.application.converter.FileUploadApplicationConverter;
import com.yaocode.sts.file.application.model.command.UploadFileCommand;
import com.yaocode.sts.file.application.model.dto.FileUploadDto;
import com.yaocode.sts.file.application.model.result.UploadResult;
import com.yaocode.sts.file.infrastructure.dao.FileBaseInfoDao;
import com.yaocode.sts.file.infrastructure.dao.FileDeduplicationDao;
import com.yaocode.sts.file.infrastructure.entity.FileBasicInfoEntity;
import com.yaocode.sts.file.infrastructure.entity.FileDeduplicationEntity;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 文件上传持久化处理器
 * <p>
 * 职责：
 * <ul>
 *   <li>将文件元数据写入 {@code file_tbl_file_info}</li>
 *   <li>将去重记录写入 {@code file_deduplication} 表</li>
 *   <li>删除临时文件</li>
 *   <li>构建并返回 {@link UploadResult}</li>
 * </ul>
 * </p>
 *
 * <p>使用 {@code REQUIRES_NEW} 传播级别保证持久化事务独立于外部事务，
 * 实现“短事务”优化。</p>
 *
 * @author yaocode
 * @since 1.0.0
 */
@Slf4j
@Component
@Order(6)
public class FilePersistenceHandler implements FileUploadHandler {

    /**
     * 指纹级锁：同一 fingerprint 并发写入去重表时串行化，避免 DuplicateKeyException
     * <p>锁粒度细，仅锁定相同文件指纹的请求，不影响其他文件上传。</p>
     */
    private final ConcurrentHashMap<String, ReentrantLock> fingerprintLocks = new ConcurrentHashMap<>();

    @Resource
    private FileBaseInfoDao fileBaseInfoDao;
    @Resource
    private FileDeduplicationDao fileDeduplicationDao;
    @Resource
    private FileUploadApplicationConverter converter;
    @Resource
    private FileUploadCleanupHandler cleanupHandler;

    @Resource
    private MessageUtils messageUtils;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void handle(FileUploadDto fileUploadDto) {
        // 策略短路场景，已在 DeduplicationHandler 中设置 result
        if (fileUploadDto.isCompleted() && fileUploadDto.getResult() != null) {
            log.debug("上传已由策略处理完成，跳过持久化: fileId={}", fileUploadDto.getFileId());
            return;
        }

        UploadFileCommand command = fileUploadDto.getCommand();

        // 1. 插入文件信息
        FileBasicInfoEntity entity = converter.toFileInfoEntity(
                command, fileUploadDto.getFileId(), fileUploadDto.getFilePath(),
                fileUploadDto.getFileUrl(), fileUploadDto.getFileMd5(), fileUploadDto.getFileSha256()
        );
        fileBaseInfoDao.save(entity);

        // 2. 插入去重记录
        saveDeduplicationRecord(fileUploadDto);

        // 3. 删除临时文件
        cleanupHandler.cleanupTempFile(fileUploadDto.getTempFile());

        // 4. 构建返回结果
        UploadResult result = converter.toUploadResultFromEntity(entity);
        long processingTime = fileUploadDto.getProcessingTime();
        result.setProcessingTime(processingTime);
        result.setMessage(messageUtils.getMessage(FileI18nKeyConstants.UPLOAD_SUCCESS, processingTime));

        fileUploadDto.setResult(result);

        log.info("上传成功: fileId={}, fileName={}, size={}, time={}ms",
                fileUploadDto.getFileId(), command.getFileName(), command.getFileSize(), processingTime);
    }

    private void saveDeduplicationRecord(FileUploadDto fileUploadDto) {
        UploadFileCommand command = fileUploadDto.getCommand();

        // 1. 未启用去重时，不写入去重表（语义：关闭去重 = 不参与去重判定）
        if (!EnableEnums.isEnabled(command.getEnableDeduplication())) {
            log.debug("去重已禁用，跳过去重记录写入: fileId={}", fileUploadDto.getFileId());
            return;
        }

        // 2. 没有 MD5 也无法生成指纹
        if (!StringUtils.hasText(fileUploadDto.getFileMd5())) {
            return;
        }

        String fingerprint = buildFingerprint(
                fileUploadDto.getFileMd5(),
                command.getFileSize(),
                command.getStorageType(),
                command.getTenantId()
        );

        // 3. 获取指纹级锁：同指纹并发请求串行化，避免竞态导致的 DuplicateKeyException
        ReentrantLock lock = fingerprintLocks.computeIfAbsent(fingerprint, k -> new ReentrantLock());
        lock.lock();
        try {
            // 3a. 锁内二次查询（双检锁模式）：大部分场景直接命中返回
            FileDeduplicationEntity existing = fileDeduplicationDao.selectByFingerprint(fingerprint);
            if (existing != null) {
                log.debug("去重记录已存在，幂等忽略: fingerprint={}, fileId={}", fingerprint, fileUploadDto.getFileId());
                existing.setReferenceCount(existing.getReferenceCount() + 1);
                fileDeduplicationDao.updateById(existing);
                return;
            }

            // 3b. 不存在则插入（MP 通用 insert，跨数据库兼容）
            FileDeduplicationEntity dedup = new FileDeduplicationEntity();
            dedup.setFingerprint(fingerprint);
            dedup.setFileId(fileUploadDto.getFileId());
            dedup.setFileMd5(fileUploadDto.getFileMd5());
            dedup.setFileSha256(fileUploadDto.getFileSha256());
            dedup.setFileSize(command.getFileSize());
            dedup.setStorageType(command.getStorageType());
            dedup.setTenantId(command.getTenantId());
            dedup.setReferenceCount(1);
            dedup.setIsDeleted(YesNoEnums.NO.getCode());
            dedup.setCreateTime(LocalDateTime.now());
            dedup.setUpdateTime(LocalDateTime.now());

            fileDeduplicationDao.save(dedup);
            log.debug("去重记录新增成功: fingerprint={}, fileId={}", fingerprint, fileUploadDto.getFileId());

        } catch (DuplicateKeyException e) {
            // 3c. 最终兜底：极端并发或锁外场景下的唯一索引冲突，安全忽略
            log.warn("去重记录并发冲突，幂等忽略: fingerprint={}, fileId={}", fingerprint, fileUploadDto.getFileId());
        } finally {
            lock.unlock();
            // 3d. 清理无用锁（避免内存泄漏，锁未被引用时移除）
            fingerprintLocks.remove(fingerprint, lock);
        }
    }

}