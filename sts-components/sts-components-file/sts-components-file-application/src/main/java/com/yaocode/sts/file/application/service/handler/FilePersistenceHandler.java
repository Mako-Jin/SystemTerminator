package com.yaocode.sts.file.application.service.handler;

import com.yaocode.sts.file.application.converter.FileUploadApplicationConverter;
import com.yaocode.sts.file.application.model.command.UploadFileCommand;
import com.yaocode.sts.file.application.model.dto.FileUploadDto;
import com.yaocode.sts.file.application.model.result.UploadResult;
import com.yaocode.sts.file.infrastructure.entity.FileDeduplicationEntity;
import com.yaocode.sts.file.infrastructure.entity.FileInfoEntity;
import com.yaocode.sts.file.infrastructure.mapper.FileDeduplicationMapper;
import com.yaocode.sts.file.infrastructure.mapper.FileInfoMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

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

    @Resource
    private FileInfoMapper fileInfoMapper;
    @Resource
    private FileDeduplicationMapper fileDeduplicationMapper;
    @Resource
    private FileUploadApplicationConverter converter;
    @Resource
    private FileUploadCleanupHandler cleanupHandler;

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
        FileInfoEntity entity = converter.toFileInfoEntity(
                command, fileUploadDto.getFileId(), fileUploadDto.getFilePath(),
                fileUploadDto.getFileUrl(), fileUploadDto.getFileMd5(), fileUploadDto.getFileSha256()
        );
        fileInfoMapper.insert(entity);

        // 2. 插入去重记录
        saveDeduplicationRecord(fileUploadDto);

        // 3. 删除临时文件
        cleanupHandler.cleanupTempFile(fileUploadDto.getTempFile());

        // 4. 构建返回结果
        UploadResult result = converter.toUploadResultFromEntity(entity);
        long processingTime = fileUploadDto.getProcessingTime();
        result.setProcessingTime(processingTime);
        result.setMessage("上传成功，耗时: " + processingTime + "ms");

        fileUploadDto.setResult(result);

        log.info("上传成功: fileId={}, fileName={}, size={}, time={}ms",
                fileUploadDto.getFileId(), command.getFileName(), command.getFileSize(), processingTime);
    }

    private void saveDeduplicationRecord(FileUploadDto fileUploadDto) {
        UploadFileCommand command = fileUploadDto.getCommand();
        if (!StringUtils.hasText(fileUploadDto.getFileMd5())) {
            return;
        }

        String fingerprint = fileUploadDto.getFileMd5() + "_" + command.getFileSize() + "_" +
                command.getStorageType() + "_" + command.getTenantId();

        FileDeduplicationEntity dedup = new FileDeduplicationEntity();
        dedup.setFingerprint(fingerprint);
        dedup.setFileId(fileUploadDto.getFileId());
        dedup.setFileMd5(fileUploadDto.getFileMd5());
        dedup.setFileSha256(fileUploadDto.getFileSha256());
        dedup.setFileSize(command.getFileSize());
        dedup.setStorageType(command.getStorageType());
        dedup.setTenantId(command.getTenantId());
        dedup.setReferenceCount(1);
        dedup.setCreatedTime(LocalDateTime.now());
        dedup.setUpdatedTime(LocalDateTime.now());

        fileDeduplicationMapper.insert(dedup);
    }
}
