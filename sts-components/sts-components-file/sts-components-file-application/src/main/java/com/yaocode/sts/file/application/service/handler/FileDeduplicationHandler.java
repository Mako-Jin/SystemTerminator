package com.yaocode.sts.file.application.service.handler;

import com.yaocode.sts.file.application.model.command.UploadFileCommand;
import com.yaocode.sts.file.application.model.dto.FileUploadDto;
import com.yaocode.sts.file.application.model.result.FileExistenceResult;
import com.yaocode.sts.file.application.model.result.UploadResult;
import com.yaocode.sts.file.application.service.DuplicateStrategyService;
import com.yaocode.sts.file.application.converter.FileUploadApplicationConverter;
import com.yaocode.sts.file.core.exception.FileException;
import com.yaocode.sts.file.core.model.ExecuteResult;
import com.yaocode.sts.file.core.model.FileExistenceContext;
import com.yaocode.sts.file.core.model.FileUploadContext;
import com.yaocode.sts.file.infrastructure.entity.FileDeduplicationEntity;
import com.yaocode.sts.file.infrastructure.entity.FileInfoEntity;
import com.yaocode.sts.file.infrastructure.mapper.FileDeduplicationMapper;
import com.yaocode.sts.file.infrastructure.mapper.FileInfoMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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
    private FileDeduplicationMapper fileDeduplicationMapper;
    @Resource
    private FileInfoMapper fileInfoMapper;
    @Resource
    private FileUploadApplicationConverter converter;
    @Resource
    private DuplicateStrategyService strategyService;

    @Override
    public void handle(FileUploadDto fileUploadDto) {
        UploadFileCommand command = fileUploadDto.getCommand();

        // 未启用去重，直接跳过
        if (command.getEnableDeduplication() == null || !command.getEnableDeduplication()) {
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
            throw new FileException("FILE_HASH_MISMATCH", "file.hash.mismatch");
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
        ExecuteResult strategyResult = strategyService.execute(
                uploadContext, existenceContext, fileUploadDto.getTempFile(), fileMd5, fileSha256
        );

        if (strategyResult != null) {
            // 策略短路：复用 / 覆盖 / 新版本 等场景，跳过后续上传
            fileUploadDto.setCompleted(true);
            fileUploadDto.setResult(convertToUploadResult(strategyResult));
            log.info("重复文件策略已完成: fileId={}, strategy={}",
                    fileUploadDto.getFileId(), strategyResult.getMessage());
        }
    }

    // ========== 内部方法 ==========

    private FileExistenceResult checkFileExists(
            String fileMd5, Long fileSize, Integer storageType, String tenantId) {
        String fingerprint = buildFingerprint(fileMd5, fileSize, storageType, tenantId);

        FileDeduplicationEntity dedup = fileDeduplicationMapper.selectByFingerprint(fingerprint);
        if (dedup == null) {
            return FileExistenceResult.builder().exists(false).build();
        }

        FileInfoEntity file = fileInfoMapper.selectByFileIdAndTenant(dedup.getFileId(), tenantId);
        if (file == null) {
            return FileExistenceResult.builder().exists(false).build();
        }

        FileExistenceResult result = FileExistenceResult.builder()
                .exists(true)
                .fileId(file.getFileId())
                .fileName(file.getFileName())
                .fileSize(file.getFileSize())
                .fileMd5(file.getFileMd5())
                .fileSha256(file.getFileSha256())
                .fileUrl(file.getStorageUrl())
                .storageType(file.getStorageType())
                .tenantId(file.getTenantId())
                .userId(file.getCreatedUserId())
                .versionNumber(file.getCurrentVersionNumber())
                .build();

        List<FileInfoEntity> duplicates = fileInfoMapper.selectByMd5AndTenant(fileMd5, tenantId);
        result.setIsDuplicate(duplicates.size() > 1);
        result.setDuplicateFiles(converter.toFileInfoResultList(duplicates));

        return result;
    }

    private String buildFingerprint(String fileMd5, Long fileSize, Integer storageType, String tenantId) {
        return fileMd5 + "_" + fileSize + "_" +
                (storageType != null ? storageType : "default") + "_" + tenantId;
    }

    private boolean isHashConsistent(String fileSha256, String existSha256) {
        if (!StringUtils.hasText(fileSha256) || !StringUtils.hasText(existSha256)) {
            return true;
        }
        return Objects.equals(fileSha256, existSha256);
    }

    private UploadResult convertToUploadResult(ExecuteResult executeResult) {
        if (executeResult == null) {
            return null;
        }
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
                .isDuplicate(executeResult.getIsDuplicate())
                .sourceFileId(executeResult.getSourceFileId())
                .versionNumber(executeResult.getVersionNumber())
                .message(executeResult.getMessage())
                .build();
    }
}
