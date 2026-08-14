package com.yaocode.sts.file.application.service.handler;

import com.yaocode.sts.common.basic.enums.EnableEnums;
import com.yaocode.sts.file.application.converter.FileUploadApplicationConverter;
import com.yaocode.sts.file.application.model.command.UploadFileCommand;
import com.yaocode.sts.file.application.model.dto.FileUploadDto;
import com.yaocode.sts.file.application.model.result.FileExistenceResult;
import com.yaocode.sts.file.application.model.result.UploadResult;
import com.yaocode.sts.file.application.service.DuplicateStrategyService;
import com.yaocode.sts.file.core.enums.FileErrorCodeEnums;
import com.yaocode.sts.file.core.enums.UploadStatusEnums;
import com.yaocode.sts.file.core.exception.FileHashException;
import com.yaocode.sts.file.core.model.ExecuteResult;
import com.yaocode.sts.file.core.model.FileExistenceContext;
import com.yaocode.sts.file.core.model.FileUploadContext;
import com.yaocode.sts.file.infrastructure.dao.FileBaseInfoDao;
import com.yaocode.sts.file.infrastructure.dao.FileDeduplicationDao;
import com.yaocode.sts.file.infrastructure.entity.FileDeduplicationEntity;
import com.yaocode.sts.file.infrastructure.entity.FileBasicInfoEntity;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * 重复文件检查处理器
 * <p>
 * 职责：
 * <ul>
 *   <li>检查文件是否已存在（基于 MD5 + 大小 + 租户）</li>
 *   <li>验证哈希一致性（MD5 匹配但 SHA-256 不一致时拒绝）</li>
 *   <li>调用 {@link DuplicateStrategyService} 执行对应策略</li>
 *   <li>策略短路时直接设置 completed = true，跳过后续上传</li>
 * </ul>
 * </p>
 *
 * @author yaocode
 * @since 1.0.0
 */
@Slf4j
@Component
@Order(4)
public class FileDeduplicationHandler implements FileUploadHandler {

    @Resource
    private FileDeduplicationDao fileDeduplicationDao;
    @Resource
    private FileBaseInfoDao fileBaseInfoDao;
    @Resource
    private FileUploadApplicationConverter converter;
    @Resource
    private DuplicateStrategyService duplicateStrategyService;

    @Override
    public void handle(FileUploadDto fileUploadDto) {
        UploadFileCommand command = fileUploadDto.getCommand();

        // 未启用去重，直接跳过
        if (!Objects.equals(EnableEnums.ENABLED.getCode(), command.getEnableDeduplication())) {
            return;
        }

        String fileMd5 = fileUploadDto.getFileMd5();
        String fileSha256 = fileUploadDto.getFileSha256();

        FileExistenceResult existCheck = checkFileExists(
                fileMd5, command.getFileSize(),
                command.getStorageType(), command.getTenantId()
        );

        if (existCheck == null || !existCheck.getExists()) {
            log.debug("文件不存在，走正常上传流程: fileId={}", fileUploadDto.getFileId());
            return;
        }

        // 验证哈希一致性
        if (!isHashConsistent(fileSha256, existCheck.getFileSha256())) {
            log.warn("MD5匹配但SHA-256不匹配, md5={}", fileMd5);
            throw new FileHashException(FileErrorCodeEnums.FILE_HASH_MISMATCH, fileMd5);
        }

        // 标记为重复文件
        fileUploadDto.setDuplicate(true);
        fileUploadDto.setExistenceResult(existCheck);

        // 构建上下文
        FileUploadContext uploadContext = converter.toFileUploadContext(
                command, fileMd5, fileSha256, fileUploadDto.getStorageType().getCode()
        );
        fileUploadDto.setUploadContext(uploadContext);

        FileExistenceContext existenceContext = converter.toFileExistenceContextOrDefault(existCheck);
        fileUploadDto.setExistenceContext(existenceContext);

        // 执行策略
        ExecuteResult strategyResult = duplicateStrategyService.execute(
                uploadContext, existenceContext, fileUploadDto.getTempFile(), fileMd5, fileSha256
        );

        if (strategyResult != null) {
            // 策略短路：复用 / 覆盖 / 新版本 等场景，跳过后续上传
            fileUploadDto.setCompleted(true);
            fileUploadDto.setResult(convertToUploadResult(strategyResult, fileUploadDto));
            log.info("重复文件策略已完成: fileId={}, strategy={}",
                    fileUploadDto.getFileId(), strategyResult.getMessage());
        }
    }

    // ========== 内部方法 ==========

    public FileExistenceResult checkFileExists(
            String fileMd5, Long fileSize, Integer storageType, String tenantId
    ) {
        // 1. 先去重表查询（快速索引）
        String fingerprint = buildFingerprint(fileMd5, fileSize, storageType, tenantId);
        FileDeduplicationEntity dedup = fileDeduplicationDao.selectByFingerprint(fingerprint);
        // 2. 去重表没有记录，查 file_base_info 表（兜底）
        List<FileBasicInfoEntity> duplicates = fileBaseInfoDao.selectByMd5AndTenant(fileMd5, tenantId);

        if (dedup != null) {
            // 去重表有记录，直接根据 dedup 查询文件详情构建返回结果
            FileBasicInfoEntity file = fileBaseInfoDao.selectByFileIdAndTenant(dedup.getFileId(), tenantId);
            if (file != null) {
                FileExistenceResult result = buildExistenceResult(file);
                result.setExists(true);

                // 检查是否有多个相同MD5的文件
                result.setIsDuplicate(duplicates.size() > 1);
                result.setDuplicateFiles(converter.toFileInfoResultList(duplicates));

                log.debug("通过去重表发现重复文件: md5={}, fileId={}", fileMd5, dedup.getFileId());
                return result;
            } else {
                // 去重表有记录但文件详情不存在（数据不一致），清理脏数据
                log.warn("去重记录存在但文件详情不存在，清理脏数据: fileId={}, fingerprint={}", dedup.getFileId(), fingerprint);
                fileDeduplicationDao.removeById(dedup.getDeduplicationId());
            }
        }

        if (!CollectionUtils.isEmpty(duplicates)) {
            // 进一步按文件大小和存储类型过滤
            FileBasicInfoEntity matchedFile = duplicates.stream()
                    .filter(f -> Objects.equals(f.getFileSize(), fileSize))
                    .filter(f -> Objects.equals(f.getStorageType(), storageType))
                    .findFirst()
                    .orElse(null);

            if (matchedFile != null) {
                // 找到了完全匹配的文件，构建返回结果
                FileExistenceResult result = buildExistenceResult(matchedFile);
                result.setExists(true);
                result.setIsDuplicate(duplicates.size() > 1);
                result.setDuplicateFiles(converter.toFileInfoResultList(duplicates));
                log.debug("通过 file_base_info 发现重复文件: md5={}, fileId={}", fileMd5, matchedFile.getFileId());
                return result;
            }
        }

        // 3. 去重表和详情表都没有匹配的记录，返回不存在
        log.debug(
                "文件不存在: md5={}, fileSize={}, storageType={}, tenantId={}",
                fileMd5, fileSize, storageType, tenantId
        );
        return FileExistenceResult.builder()
                .exists(false)
                .build();
    }

    private boolean isHashConsistent(String fileSha256, String existSha256) {
        if (!StringUtils.hasText(fileSha256) || !StringUtils.hasText(existSha256)) {
            return true;
        }
        return Objects.equals(fileSha256, existSha256);
    }

    private UploadResult convertToUploadResult(ExecuteResult executeResult, FileUploadDto fileUploadDto) {
        if (executeResult == null) {
            return null;
        }
        long processingTime = fileUploadDto.getProcessingTime();
        String uploadStatusDesc = UploadStatusEnums.fromCode(executeResult.getUploadStatus()).getDesc();
        return UploadResult.builder()
                .fileId(executeResult.getFileId())
                .fileName(executeResult.getFileName())
                .fileSize(executeResult.getFileSize())
                .fileMd5(executeResult.getFileMd5())
                .fileSha256(executeResult.getFileSha256())
                .fileUrl(executeResult.getFileUrl())
                .storageType(executeResult.getStorageType() != null ?
                        executeResult.getStorageType() : null)
                .tenantId(executeResult.getTenantId())
                .uploadStatus(executeResult.getUploadStatus())
                .uploadStatusDesc(uploadStatusDesc)
                .isDuplicate(executeResult.getIsDuplicate())
                .duplicateFileId(executeResult.getSourceFileId())
                .sourceFileId(executeResult.getSourceFileId())
                .versionNumber(executeResult.getVersionNumber())
                .uploadTime(LocalDateTime.now())
                .processingTime(processingTime)
                .message(executeResult.getMessage())
                .build();
    }

    /**
     * 构建 FileExistenceResult
     */
    private FileExistenceResult buildExistenceResult(FileBasicInfoEntity file) {
        return FileExistenceResult.builder()
                .exists(true)
                .fileId(file.getFileId())
                .fileName(file.getFileName())
                .fileSize(file.getFileSize())
                .fileMd5(file.getFileMd5())
                .fileSha256(file.getFileSha256())
                .fileUrl(file.getStorageUrl())
                .storageType(file.getStorageType())
                .tenantId(file.getTenantId())
                .userId(file.getCreateUserId())
                .versionNumber(file.getVersionNumber())
                .build();
    }
}