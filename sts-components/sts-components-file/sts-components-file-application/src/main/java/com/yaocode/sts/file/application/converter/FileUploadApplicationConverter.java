package com.yaocode.sts.file.application.converter;

import com.yaocode.sts.common.domain.context.RequestContextHolder;
import com.yaocode.sts.common.tools.JSONUtils;
import com.yaocode.sts.common.tools.StringUtils;
import com.yaocode.sts.file.application.model.command.UploadBatchCommand;
import com.yaocode.sts.file.application.model.command.UploadFileCommand;
import com.yaocode.sts.file.application.model.dto.FileObjectDto;
import com.yaocode.sts.file.application.model.result.FileExistenceResult;
import com.yaocode.sts.file.application.model.result.FileInfoResult;
import com.yaocode.sts.file.application.model.result.UploadResult;
import com.yaocode.sts.file.core.enums.FileExtensionEnums;
import com.yaocode.sts.file.core.enums.FileTypeEnums;
import com.yaocode.sts.file.core.enums.UploadStatusEnums;
import com.yaocode.sts.file.core.model.FileExistenceContext;
import com.yaocode.sts.file.core.model.FileUploadContext;
import com.yaocode.sts.file.core.utils.FileUtils;
import com.yaocode.sts.file.infrastructure.entity.FileBasicInfoEntity;
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

    @Mapping(target = "fileUrl", source = "storageUrl")
    @Mapping(target = "uploadStatusDesc", expression = "java(UploadStatusEnums.fromCode(entity.getUploadStatus()).getDesc())")
    @Mapping(target = "uploadTime", source = "uploadTime")
    @Mapping(target = "processingTime", ignore = true)
    @Mapping(target = "isDuplicate", constant = "false")
    @Mapping(target = "duplicateFileId", ignore = true)
    @Mapping(target = "message", constant = "上传成功")
    UploadResult toUploadResultFromEntity(FileBasicInfoEntity entity);

    List<UploadResult> toUploadResultList(List<FileBasicInfoEntity> entities);

    // ==================== FileInfoEntity 构建 ====================

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
    @Mapping(target = "tags", expression = "java(toJsonArray(command.getTags()))")
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
    @Mapping(target = "isDeleted", constant = "0")
    @Mapping(target = "version", constant = "1")
    @Mapping(target = "versionNumber", ignore = true)
    @Mapping(target = "versionControlEnabled", ignore = true)
    FileBasicInfoEntity toFileInfoEntity(
            UploadFileCommand command,
            String fileId,
            String filePath,
            String fileUrl,
            String fileMd5,
            String fileSha256
    );

    // ==================== FileInfoResult 转换 ====================

    @Mapping(target = "tags", expression = "java(parseJsonArray(entity.getTags()))")
    @Mapping(target = "filePath", ignore = true)
    @Mapping(target = "fileSha256", ignore = true)
    FileInfoResult toFileInfoResult(FileBasicInfoEntity entity);

    List<FileInfoResult> toFileInfoResultList(List<FileBasicInfoEntity> entities);

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
        return StringUtils.hasText(command.getBucket())
                ? command.getBucket() : "default";
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

    @Named("getIsPublic")
    default Integer getIsPublic(UploadFileCommand command) {
        return command.getIsPublic();
    }

    @Named("getStorageMetadata")
    default String getStorageMetadata(UploadFileCommand command) {
        if (command.getMetadata() != null && !command.getMetadata().isEmpty()) {
            return JSONUtils.toJson(command.getMetadata());
        }
        return null;
    }

    /**
     * 将前端传入的逗号分隔标签字符串转为 JSON 数组存储。
     * 支持两种输入格式：
     *   1) 逗号分隔字符串："合同,重要,2024" → ["合同","重要","2024"]
     *   2) 已经是 JSON 数组：   '["合同","重要"]' → 直接使用
     */
    @Named("toJsonArray")
    default String toJsonArray(String tags) {
        if (tags == null || tags.trim().isEmpty()) {
            return null;
        }
        String trimmed = tags.trim();
        // 如果已经是 JSON 数组格式，直接返回
        if (trimmed.startsWith("[") && trimmed.endsWith("]")) {
            return trimmed;
        }
        // 逗号分隔：先拆分为 List，再序列化为 JSON 数组
        List<String> list = Arrays.stream(trimmed.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
        if (list.isEmpty()) {
            return null;
        }
        return JSONUtils.toJson(list);
    }

    /**
     * 解析数据库中存储的 JSON 数组标签为 List<String>。
     * 兼容历史数据：若不是 JSON 数组则回退到逗号分隔解析。
     */
    @Named("parseJsonArray")
    default List<String> parseJsonArray(String tags) {
        if (tags == null || tags.isEmpty()) {
            return Collections.emptyList();
        }
        String trimmed = tags.trim();
        // 正常 JSON 数组
        if (trimmed.startsWith("[") && trimmed.endsWith("]")) {
            try {
                List<String> result = JSONUtils.parseArray(trimmed, String.class);
                if (result != null && !result.isEmpty()) {
                    return result;
                }
            } catch (Exception e) {
                // 解析失败回退
            }
        }
        // 兼容历史逗号分隔格式
        return Arrays.stream(trimmed.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
    }

    /**
     * 将批量命令 + 单个文件对象 构建为单文件上传命令
     */
    default UploadFileCommand buildUploadFileCommand(UploadBatchCommand batchCommand, FileObjectDto file) {
        return UploadFileCommand.builder()
                .file(file)
                .fileName(file.getFileName())
                .fileSize(file.getFileSize())
                .fileMd5(file.getMd5())
//                .fileSha256(file.getSha256())
                .storageType(batchCommand.getStorageType())
                .bucket(batchCommand.getBucket())
                .tags(batchCommand.getTags())
                .description(batchCommand.getDescription())
                .isPublic(batchCommand.getIsPublic())
                .enableDeduplication(batchCommand.getEnableDeduplication())
//                .versionRemark(batchCommand.getVersionRemark())
//                .preferredStorages(batchCommand.getPreferredStorages())
//                .strategy(batchCommand.getStrategy())
                .tenantId(batchCommand.getTenantId())
                .userId(batchCommand.getUserId())
//                .username(batchCommand.getUsername())
                .build();
    }

}