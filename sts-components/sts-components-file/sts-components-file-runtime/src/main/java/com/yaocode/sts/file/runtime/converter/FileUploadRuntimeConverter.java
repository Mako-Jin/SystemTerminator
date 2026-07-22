package com.yaocode.sts.file.runtime.converter;

import com.yaocode.sts.common.tools.JSONUtils;
import com.yaocode.sts.common.tools.StringUtils;
import com.yaocode.sts.file.core.enums.FileExtensionEnums;
import com.yaocode.sts.file.core.enums.FileStatusEnums;
import com.yaocode.sts.file.core.enums.FileTypeEnums;
import com.yaocode.sts.file.core.enums.UploadStatusEnums;
import com.yaocode.sts.file.core.utils.FileUtils;
import com.yaocode.sts.file.runtime.entity.FileInfoEntity;
import com.yaocode.sts.file.runtime.model.command.UploadFileCommand;
import com.yaocode.sts.file.runtime.model.result.FileExistenceResult;
import com.yaocode.sts.file.runtime.model.result.UploadResult;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class FileUploadRuntimeConverter {

    public UploadResult toUploadResult(FileExistenceResult result) {
        return UploadResult.builder()
                .fileId(result.getFileId())
                .fileName(result.getFileName())
                .fileSize(result.getFileSize())
                .fileMd5(result.getFileMd5())
                .fileUrl(result.getFileUrl())
                .storageType(result.getStorageType())
                .uploadStatus(UploadStatusEnums.COMPLETED.getCode())
                .isDuplicate(true)
                .duplicateFileId(result.getFileId())
                .message("秒传成功")
                .build();
    }

    public UploadResult toUploadResult(FileInfoEntity entity) {
        return UploadResult.builder()
                .fileId(entity.getFileId())
                .fileName(entity.getFileName())
                .fileSize(entity.getFileSize())
                .fileMd5(entity.getFileMd5())
                .fileUrl(entity.getStorageUrl())
                .storageType(entity.getStorageType())
                .tenantId(entity.getTenantId())
                .uploadStatus(entity.getUploadStatus())
                .uploadStatusDesc(UploadStatusEnums.fromCode(entity.getUploadStatus()).getDesc())
                .uploadTime(entity.getUploadTime())
                .message("上传成功")
                .build();
    }

    /**
     * 上传命令转文件信息实体（完整版）
     */
    public FileInfoEntity toFileInfoEntity(
            UploadFileCommand command,
            String fileId,
            String filePath,
            String fileUrl,
            String fileMd5
    ) {
        LocalDateTime now = LocalDateTime.now();
        String fileName = command.getFileName();

        // 获取文件类型信息
        FileExtensionEnums extensionEnum = FileUtils.getFileExtensionEnums(fileName);
        FileTypeEnums fileType = extensionEnum.getFileType();

        FileInfoEntity entity = new FileInfoEntity();

        // ==================== 1. 基础信息 ====================
        entity.setFileId(fileId);
        entity.setFileName(fileName);
        entity.setFilePath(filePath);
        entity.setFileSize(command.getFileSize());
        entity.setFileMd5(fileMd5);
        entity.setStorageType(command.getStorageType());
        entity.setStorageUrl(fileUrl);

        // ==================== 2. 存储相关 ====================
        // bucket: 优先使用业务类型，否则使用默认值
        String bucket = StringUtils.hasText(command.getBusinessType())
                ? command.getBusinessType()
                : "default";
        entity.setStorageBucket(bucket);

        // ==================== 3. 文件类型信息 ====================
        entity.setFileType(fileType.getCode());
        entity.setFileExtension(extensionEnum.getCode());

        // ==================== 4. 租户和用户信息 ====================
        entity.setTenantId(StringUtils.hasText(command.getTenantId())
                ? command.getTenantId()
                : "default");
        entity.setCreatedUserId(StringUtils.hasText(command.getUserId())
                ? command.getUserId()
                : "system");

        if (StringUtils.hasText(command.getTags())) {
            entity.setTags(command.getTags());
        }
        if (StringUtils.hasText(command.getDescription())) {
            entity.setDescription(command.getDescription());
        }
        if (command.getIsPublic() != null) {
            entity.setIsPublic(command.getIsPublic() ? 1 : 0);
        }

        // ==================== 6. 元数据（JSON格式） ====================
        if (command.getMetadata() != null && !command.getMetadata().isEmpty()) {
            entity.setStorageMetadata(JSONUtils.toJson(command.getMetadata()));
        }

        // ==================== 7. 上传状态和时间 ====================
        entity.setUploadStatus(UploadStatusEnums.COMPLETED.getCode());
        entity.setUploadProgress(100);
        entity.setUploadStartTime(now);
        entity.setUploadEndTime(now);
        entity.setUploadTime(now);

        // ==================== 8. 文件状态 ====================
        entity.setFileStatus(FileStatusEnums.NORMAL.getCode());

        // ==================== 9. 默认值 ====================
        entity.setIsPublic(0);
        entity.setIsEncrypted(0);
        entity.setIsCompressed(0);
        entity.setDownloadCount(0L);
        entity.setViewCount(0L);
        entity.setVersion(1);

        // ==================== 10. 审计字段 ====================
        entity.setCreatedTime(now);
        entity.setUpdatedTime(now);

        return entity;
    }

}
