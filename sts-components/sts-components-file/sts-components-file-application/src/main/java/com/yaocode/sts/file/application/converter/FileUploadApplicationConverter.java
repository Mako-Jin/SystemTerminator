package com.yaocode.sts.file.application.converter;

import com.yaocode.sts.common.tools.JSONUtils;
import com.yaocode.sts.file.application.model.command.UploadFileCommand;
import com.yaocode.sts.file.application.model.result.FileExistenceResult;
import com.yaocode.sts.file.application.model.result.FileInfoResult;
import com.yaocode.sts.file.application.model.result.UploadResult;
import com.yaocode.sts.file.core.enums.FileExtensionEnums;
import com.yaocode.sts.file.core.enums.FileTypeEnums;
import com.yaocode.sts.file.core.enums.UploadStatusEnums;
import com.yaocode.sts.file.core.model.FileExistenceContext;
import com.yaocode.sts.file.core.model.FileUploadContext;
import com.yaocode.sts.file.core.utils.FileUtils;
import com.yaocode.sts.file.infrastructure.entity.FileInfoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Mapper(
    componentModel = "spring",
    imports = {
        LocalDateTime.class,
        UploadStatusEnums.class,
        FileUtils.class,
        FileExtensionEnums.class,
        FileTypeEnums.class,
        JSONUtils.class,
        Arrays.class,
        Collections.class
    }
)
public interface FileUploadApplicationConverter {

    FileUploadApplicationConverter INSTANCE = Mappers.getMapper(FileUploadApplicationConverter.class);

    // ==================== UploadResult 转换 ====================

    @Mapping(target = "uploadStatus", constant = "1")
    @Mapping(target = "isDuplicate", constant = "true")
    @Mapping(target = "duplicateFileId", source = "fileId")
    @Mapping(target = "message", constant = "秒传成功")
    @Mapping(target = "uploadStatusDesc", ignore = true)
    @Mapping(target = "uploadTime", ignore = true)
    @Mapping(target = "processingTime", ignore = true)
    UploadResult toUploadResultFromExistence(FileExistenceResult result);

    @Mapping(target = "uploadStatusDesc", expression = "java(UploadStatusEnums.fromCode(entity.getUploadStatus()).getDesc())")
    @Mapping(target = "uploadTime", source = "uploadTime")
    @Mapping(target = "processingTime", ignore = true)
    @Mapping(target = "isDuplicate", ignore = true)
    @Mapping(target = "duplicateFileId", ignore = true)
    @Mapping(target = "message", constant = "上传成功")
    UploadResult toUploadResultFromEntity(FileInfoEntity entity);

    List<UploadResult> toUploadResultList(List<FileInfoEntity> entities);

    // ==================== FileInfoEntity 构建 ====================

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fileId", source = "fileId")
    @Mapping(target = "fileName", source = "command.fileName")
    @Mapping(target = "filePath", source = "filePath")
    @Mapping(target = "fileSize", source = "command.fileSize")
    @Mapping(target = "fileMd5", source = "fileMd5")
    @Mapping(target = "fileSha256", source = "fileSha256")
    @Mapping(target = "storageType", source = "command.storageType")
    @Mapping(target = "storageUrl", source = "fileUrl")
    @Mapping(target = "storageBucket", expression = "java(getBucket(command))")
    @Mapping(target = "fileType", expression = "java(getFileType(command))")
    @Mapping(target = "fileExtension", expression = "java(getFileExtensionCode(command))")
    @Mapping(target = "tenantId", expression = "java(getTenantId(command))")
    @Mapping(target = "createdUserId", expression = "java(getUserId(command))")
    @Mapping(target = "tags", source = "command.tags")
    @Mapping(target = "description", source = "command.description")
    @Mapping(target = "isPublic", expression = "java(getIsPublic(command))")
    @Mapping(target = "storageMetadata", expression = "java(getStorageMetadata(command))")
    @Mapping(target = "uploadStatus", constant = "1")
    @Mapping(target = "uploadProgress", constant = "100")
    @Mapping(target = "uploadStartTime", expression = "java(LocalDateTime.now())")
    @Mapping(target = "uploadEndTime", expression = "java(LocalDateTime.now())")
    @Mapping(target = "uploadTime", expression = "java(LocalDateTime.now())")
    @Mapping(target = "fileStatus", constant = "1")
    @Mapping(target = "isEncrypted", constant = "0")
    @Mapping(target = "isCompressed", constant = "0")
    @Mapping(target = "downloadCount", constant = "0L")
    @Mapping(target = "viewCount", constant = "0L")
    @Mapping(target = "version", constant = "1")
    @Mapping(target = "createdTime", expression = "java(LocalDateTime.now())")
    @Mapping(target = "updatedTime", expression = "java(LocalDateTime.now())")
    @Mapping(target = "createdUserName", ignore = true)
    @Mapping(target = "updatedUserId", ignore = true)
    @Mapping(target = "updatedUserName", ignore = true)
    @Mapping(target = "currentVersionNumber", ignore = true)
    @Mapping(target = "versionControlEnabled", ignore = true)
    @Mapping(target = "versionCount", ignore = true)
    FileInfoEntity toFileInfoEntity(
            UploadFileCommand command,
            String fileId,
            String filePath,
            String fileUrl,
            String fileMd5,
            String fileSha256
    );

    // ==================== FileInfoResult 转换 ====================

    @Mapping(target = "tags", expression = "java(parseTags(entity.getTags()))")
    @Mapping(target = "fileStatusDesc", ignore = true)
    @Mapping(target = "uploadStatusDesc", ignore = true)
    @Mapping(target = "filePath", ignore = true)
    @Mapping(target = "fileSha256", ignore = true)
    FileInfoResult toFileInfoResult(FileInfoEntity entity);

    List<FileInfoResult> toFileInfoResultList(List<FileInfoEntity> entities);

    // ==================== Context 构建 ====================

    @Mapping(target = "fileExtension", expression = "java(FileUtils.getFileExtension(command.getFileName()))")
    @Mapping(target = "attributes", ignore = true)
    FileUploadContext toFileUploadContext(
            UploadFileCommand command,
            String fileMd5,
            String fileSha256,
            Integer storageType
    );

    @Mapping(target = "duplicateFiles", ignore = true)
    FileExistenceContext toFileExistenceContext(FileExistenceResult result);

    default FileExistenceContext toFileExistenceContextOrDefault(FileExistenceResult result) {
        if (result == null) {
            return FileExistenceContext.builder().exists(false).build();
        }
        return toFileExistenceContext(result);
    }

    // ==================== 辅助方法 ====================

    @Named("getBucket")
    default String getBucket(UploadFileCommand command) {
        return command.getBusinessType() != null && !command.getBusinessType().isEmpty()
                ? command.getBusinessType() : "default";
    }

    @Named("getFileType")
    default Integer getFileType(UploadFileCommand command) {
        String fileName = command.getFileName();
        FileExtensionEnums extEnum = FileUtils.getFileExtensionEnums(fileName);
        return extEnum.getFileType().getCode();
    }

    @Named("getFileExtensionCode")
    default Integer getFileExtensionCode(UploadFileCommand command) {
        String fileName = command.getFileName();
        FileExtensionEnums extEnum = FileUtils.getFileExtensionEnums(fileName);
        return extEnum.getCode();
    }

    @Named("getTenantId")
    default String getTenantId(UploadFileCommand command) {
        return command.getTenantId() != null && !command.getTenantId().isEmpty()
                ? command.getTenantId() : "default";
    }

    @Named("getUserId")
    default String getUserId(UploadFileCommand command) {
        return command.getUserId() != null && !command.getUserId().isEmpty()
                ? command.getUserId() : "system";
    }

    @Named("getIsPublic")
    default Integer getIsPublic(UploadFileCommand command) {
        return command.getIsPublic() != null && command.getIsPublic() ? 1 : 0;
    }

    @Named("getStorageMetadata")
    default String getStorageMetadata(UploadFileCommand command) {
        if (command.getMetadata() != null && !command.getMetadata().isEmpty()) {
            return JSONUtils.toJson(command.getMetadata());
        }
        return null;
    }

    @Named("parseTags")
    default List<String> parseTags(String tags) {
        if (tags == null || tags.isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.asList(tags.split(","));
    }
}
