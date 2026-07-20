package com.yaocode.sts.components.file.runtime.converter;

import com.yaocode.sts.common.tools.StringUtils;
import com.yaocode.sts.components.file.core.enums.FileStatusEnums;
import com.yaocode.sts.components.file.core.enums.UploadStatusEnums;
import com.yaocode.sts.components.file.runtime.entity.FileInfoEntity;
import com.yaocode.sts.components.file.runtime.model.command.UploadFileCommand;
import com.yaocode.sts.components.file.runtime.model.result.FileExistenceResult;
import com.yaocode.sts.components.file.runtime.model.result.UploadResult;
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

    public FileInfoEntity toFileInfoEntity(
            UploadFileCommand command, String fileId,
            String filePath, String fileUrl, String fileMd5
    ) {
        FileInfoEntity entity = new FileInfoEntity();
        entity.setFileId(fileId);
        entity.setFileName(command.getFile().getFileName());
        entity.setFilePath(filePath);
        entity.setFileSize(command.getFile().getFileSize());
        entity.setFileMd5(fileMd5);
        entity.setStorageType(command.getStorageType());
        entity.setStorageUrl(fileUrl);
        entity.setTenantId(command.getTenantId());
        entity.setCreatedUserId(command.getUserId());
        entity.setFileStatus(FileStatusEnums.NORMAL.getCode());
        entity.setUploadStatus(UploadStatusEnums.COMPLETED.getCode());
        entity.setUploadProgress(100);
        entity.setUploadTime(LocalDateTime.now());
        entity.setCreatedTime(LocalDateTime.now());
        entity.setUpdatedTime(LocalDateTime.now());

        if (StringUtils.hasText(command.getTags())) {
            entity.setTags(command.getTags());
        }
        if (StringUtils.hasText(command.getDescription())) {
            entity.setDescription(command.getDescription());
        }
        if (command.getIsPublic() != null) {
            entity.setIsPublic(command.getIsPublic() ? 1 : 0);
        }

        return entity;
    }

}
