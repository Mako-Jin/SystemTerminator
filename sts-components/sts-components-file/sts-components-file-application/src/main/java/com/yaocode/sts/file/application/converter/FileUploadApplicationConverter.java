package com.yaocode.sts.file.application.converter;

import com.yaocode.sts.common.basic.enums.YesNoEnums;
import com.yaocode.sts.common.tools.JSONUtils;
import com.yaocode.sts.common.tools.StringUtils;
import com.yaocode.sts.file.application.model.command.CompleteMultipartCommand;
import com.yaocode.sts.file.application.model.command.FastUploadCommand;
import com.yaocode.sts.file.application.model.command.InitMultipartCommand;
import com.yaocode.sts.file.application.model.command.UploadBatchCommand;
import com.yaocode.sts.file.application.model.command.UploadFileCommand;
import com.yaocode.sts.file.application.model.command.UploadPartCommand;
import com.yaocode.sts.file.application.model.dto.FileObjectDto;
import com.yaocode.sts.file.application.model.result.CancelMultipartResult;
import com.yaocode.sts.file.application.model.result.FileExistenceResult;
import com.yaocode.sts.file.application.model.result.FileInfoResult;
import com.yaocode.sts.file.application.model.result.MultipartInitResult;
import com.yaocode.sts.file.application.model.result.MultipartSessionResult;
import com.yaocode.sts.file.application.model.result.UploadPartResult;
import com.yaocode.sts.file.application.model.result.UploadProgressResult;
import com.yaocode.sts.file.application.model.result.UploadResult;
import com.yaocode.sts.file.infrastructure.entity.FileChunkEntity;
import com.yaocode.sts.file.infrastructure.entity.UploadSessionEntity;
import com.yaocode.sts.file.core.constants.FileConstants;
import com.yaocode.sts.file.core.enums.ChunkStatusEnums;
import com.yaocode.sts.file.core.enums.FileExtensionEnums;
import com.yaocode.sts.file.core.enums.FileStatusEnums;
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
import java.time.ZoneId;
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
        return FileUtils.getFileType(command.getFileName());
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

    // ==================== Multipart 转换 ====================

    /**
     * 构建新的分片实体
     */
    default FileChunkEntity toNewFileChunkEntity(
            UploadPartCommand command,
            UploadSessionEntity sessionEntity,
            Integer chunkNumber,
            long chunkSize
    ) {
        FileChunkEntity entity = new FileChunkEntity();
        entity.setUploadId(command.getUploadId());
        entity.setFileId(command.getFileId());
        entity.setChunkNumber(chunkNumber);
        entity.setChunkSize(chunkSize);
        entity.setChunkMd5(command.getChunkMd5());
        entity.setStorageType(sessionEntity.getStorageType());
        entity.setChunkStatus(ChunkStatusEnums.UPLOADING.getCode());
        entity.setUploadStartTime(LocalDateTime.now());
        entity.setTenantId(command.getTenantId());
        return entity;
    }

    /**
     * 构建分片上传结果（统一入口，处理进度计算）
     */
    default UploadPartResult toUploadPartResult(
            String uploadId,
            String fileId,
            Integer chunkNumber,
            Integer totalChunks,
            int completedChunks
    ) {
        int progress = calculateProgress(totalChunks, completedChunks);
        return UploadPartResult.builder()
                .uploadId(uploadId)
                .fileId(fileId)
                .chunkNumber(chunkNumber)
                .totalChunks(totalChunks)
                .success(true)
                .uploadedChunks(completedChunks)
                .progress(progress)
                .build();
    }

    /**
     * 计算上传进度百分比（上限100）
     */
    @Named("calculateProgress")
    default int calculateProgress(Integer totalChunks, int completedChunks) {
        if (totalChunks == null || totalChunks <= 0) {
            return 0;
        }
        return Math.min(100, (int) Math.round(completedChunks * 100.0 / totalChunks));
    }

    /**
     * 解析分片大小：优先使用客户端提供的文件大小，否则使用会话配置的默认分片大小
     */
    @Named("resolveChunkSize")
    default long resolveChunkSize(UploadPartCommand command, UploadSessionEntity sessionEntity) {
        FileObjectDto file = command.getFile();
        if (file != null && file.getFileSize() != null) {
            return file.getFileSize();
        }
        return sessionEntity.getChunkSize();
    }

    /**
     * 根据命令和计算参数构建上传会话实体
     */
    default UploadSessionEntity toUploadSessionEntity(
            InitMultipartCommand command,
            String uploadId,
            String fileId,
            Long fileSize,
            Long chunkSize,
            int totalChunks,
            Integer storageTypeCode,
            LocalDateTime expireTime
    ) {
        UploadSessionEntity entity = new UploadSessionEntity();
        entity.setUploadId(uploadId);
        entity.setFileId(fileId);
        entity.setFileName(command.getFileName());
        entity.setFileSize(fileSize);
        entity.setStorageType(storageTypeCode);
        entity.setTotalChunks(totalChunks);
        entity.setChunkSize(chunkSize);
        entity.setCompletedChunks(0);
        entity.setUploadStatus(UploadStatusEnums.UPLOADING.getCode());
        entity.setLastActiveTime(LocalDateTime.now());
        entity.setExpireTime(expireTime);
        entity.setTenantId(command.getTenantId());
        entity.setFileMd5(command.getFileMd5());
        entity.setFileSha256(command.getFileSha256());
        entity.setFileType(FileTypeEnums.fromExtension(command.getFileType()).getCode());
        entity.setTags(toJsonArray(command.getTags()));
        entity.setDescription(command.getDescription());
        entity.setIsPublic(command.getIsPublic());
        entity.setMetadata(command.getMetadata() != null ? JSONUtils.toJson(command.getMetadata()) : null);
        return entity;
    }

    /**
     * 构建分片初始化返回结果
     */
    default MultipartInitResult toMultipartInitResult(
            InitMultipartCommand command,
            String uploadId,
            String fileId,
            Long chunkSize,
            int totalChunks,
            LocalDateTime expireTime
    ) {
        return MultipartInitResult.builder()
                .uploadId(uploadId)
                .fileId(fileId)
                .fileName(command.getFileName())
                .fileSize(command.getFileSize())
                .chunkSize(chunkSize)
                .totalChunks(totalChunks)
                .expireTime(expireTime)
                .storageType(command.getStorageType())
                .fileMd5(command.getFileMd5())
                .fileSha256(command.getFileSha256())
                .fileType(command.getFileType())
                .tags(command.getTags())
                .description(command.getDescription())
                .isPublic(command.getIsPublic())
                .metadata(command.getMetadata() != null ? JSONUtils.toJson(command.getMetadata()) : null)
                .isDuplicate(false)
                .isResume(false)
                .uploadedChunks(0)
                .build();
    }

    /**
     * 从已存在的活动会话实体构建续传结果
     */
    default MultipartInitResult toResumeInitResult(UploadSessionEntity sessionEntity) {
        int completedChunks = sessionEntity.getCompletedChunks() != null ? sessionEntity.getCompletedChunks() : 0;
        return MultipartInitResult.builder()
                .uploadId(sessionEntity.getUploadId())
                .fileId(sessionEntity.getFileId())
                .fileName(sessionEntity.getFileName())
                .fileSize(sessionEntity.getFileSize())
                .chunkSize(sessionEntity.getChunkSize())
                .totalChunks(sessionEntity.getTotalChunks())
                .expireTime(sessionEntity.getExpireTime())
                .storageType(sessionEntity.getStorageType())
                .fileMd5(sessionEntity.getFileMd5())
                .fileSha256(sessionEntity.getFileSha256())
                .fileType(FileTypeEnums.fromCode(sessionEntity.getFileType()).getName())
                .tags(sessionEntity.getTags())
                .description(sessionEntity.getDescription())
                .isPublic(sessionEntity.getIsPublic())
                .metadata(sessionEntity.getMetadata())
                .isDuplicate(false)
                .isResume(true)
                .uploadedChunks(completedChunks)
                .build();
    }

    /**
     * 构建秒传重复文件结果
     */
    default MultipartInitResult toDuplicateInitResult(
            InitMultipartCommand command,
            String fileId
    ) {
        return MultipartInitResult.builder()
                .uploadId(null)
                .fileId(fileId)
                .fileName(command.getFileName())
                .fileSize(command.getFileSize())
                .chunkSize(command.getChunkSize())
                .totalChunks(0)
                .expireTime(null)
                .storageType(command.getStorageType())
                .fileMd5(command.getFileMd5())
                .fileSha256(command.getFileSha256())
                .fileType(command.getFileType())
                .tags(command.getTags())
                .description(command.getDescription())
                .isPublic(command.getIsPublic())
                .metadata(command.getMetadata() != null ? JSONUtils.toJson(command.getMetadata()) : null)
                .isDuplicate(true)
                .duplicateFileId(fileId)
                .isResume(false)
                .uploadedChunks(0)
                .build();
    }

    /**
     * 从分片会话实体构建分片上传结果
     */
    default UploadResult toUploadResultFromMultipart(
            CompleteMultipartCommand command,
            String fileId,
            String fileUrl,
            Long fileSize,
            String fileMd5,
            String message,
            long processingTime
    ) {
        return UploadResult.builder()
                .fileId(fileId)
                .fileName(command.getFileName())
                .fileSize(fileSize)
                .fileMd5(fileMd5)
                .fileUrl(fileUrl)
                .storageType(null)
                .tenantId(command.getTenantId())
                .uploadStatus(UploadStatusEnums.COMPLETED.getCode())
                .uploadStatusDesc(UploadStatusEnums.COMPLETED.getDesc())
                .isDuplicate(false)
                .uploadTime(LocalDateTime.now())
                .processingTime(processingTime)
                .message(message)
                .build();
    }

    /**
     * 从分片会话实体构建分片上传结果
     */
    default UploadResult toUploadResultFromMultipart(
            FileBasicInfoEntity existed,
            CompleteMultipartCommand command,
            String message,
            long processingTime
    ) {
        return UploadResult.builder()
                .fileId(existed.getFileId())
                .fileName(command.getFileName() != null ? command.getFileName() : existed.getFileName())
                .fileSize(existed.getFileSize())
                .fileMd5(existed.getFileMd5())
                .fileSha256(existed.getFileSha256())
                .fileUrl(existed.getStorageUrl())
                .storageType(existed.getStorageType())
                .tenantId(existed.getTenantId())
                .uploadStatus(UploadStatusEnums.COMPLETED.getCode())
                .uploadStatusDesc(UploadStatusEnums.COMPLETED.getDesc())
                .isDuplicate(false)
                .uploadTime(LocalDateTime.now())
                .processingTime(processingTime)
                .message(message)
                .build();
    }

    /**
     * 从分片会话实体构建分片进度结果
     */
    default UploadProgressResult toUploadProgressResult(
            UploadSessionEntity sessionEntity,
            Long uploadedSize,
            String status,
            String message
    ) {
        Integer totalChunks = sessionEntity.getTotalChunks();
        int completedChunks = sessionEntity.getCompletedChunks() != null
                ? sessionEntity.getCompletedChunks() : 0;
        int progress = totalChunks > 0 ? (int) Math.round((completedChunks * 100.0) / totalChunks) : 0;

        return UploadProgressResult.builder()
                .uploadId(sessionEntity.getUploadId())
                .fileId(sessionEntity.getFileId())
                .fileName(sessionEntity.getFileName())
                .fileSize(sessionEntity.getFileSize())
                .chunkSize(sessionEntity.getChunkSize())
                .totalChunks(totalChunks)
                .uploadedChunks(completedChunks)
                .progress(progress)
                .uploadedSize(uploadedSize)
                .status(status)
                .lastActiveTime(sessionEntity.getLastActiveTime() != null
                        ? sessionEntity.getLastActiveTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() : null)
                .message(message)
                .build();
    }

    /**
     * 从分片会话实体构建分片会话结果
     */
    default MultipartSessionResult toMultipartSessionResult(UploadSessionEntity sessionEntity) {
        Integer totalChunks = sessionEntity.getTotalChunks();
        int completedChunks = sessionEntity.getCompletedChunks() != null
                ? sessionEntity.getCompletedChunks() : 0;
        int progress = totalChunks > 0 ? (int) Math.round((completedChunks * 100.0) / totalChunks) : 0;

        return MultipartSessionResult.builder()
                .uploadId(sessionEntity.getUploadId())
                .fileId(sessionEntity.getFileId())
                .fileName(sessionEntity.getFileName())
                .fileSize(sessionEntity.getFileSize())
                .chunkSize(sessionEntity.getChunkSize())
                .totalChunks(totalChunks)
                .uploadedChunks(completedChunks)
                .progress(progress)
                .status(UploadStatusEnums.fromCode(sessionEntity.getUploadStatus()).getDesc())
                .expireTime(sessionEntity.getExpireTime())
                .lastActiveTime(sessionEntity.getLastActiveTime() != null
                        ? sessionEntity.getLastActiveTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() : null)
                .createTime(sessionEntity.getCreateTime())
                .fileMd5(sessionEntity.getFileMd5())
                .fileSha256(sessionEntity.getFileSha256())
                .fileType(FileTypeEnums.fromCode(sessionEntity.getFileType()).getName())
                .tags(sessionEntity.getTags())
                .description(sessionEntity.getDescription())
                .isPublic(sessionEntity.getIsPublic())
                .metadata(sessionEntity.getMetadata())
                .build();
    }

    /**
     * 从分片完成命令和存储信息构建FileBasicInfoEntity
     */
    default FileBasicInfoEntity toFileInfoEntityFromMultipart(
            CompleteMultipartCommand command,
            UploadSessionEntity sessionEntity,
            String fileId,
            String filePath,
            String fileUrl,
            String fileMd5
    ) {
        LocalDateTime now = LocalDateTime.now();
        FileBasicInfoEntity entity = new FileBasicInfoEntity();
        entity.setFileId(fileId);
        entity.setFileName(command.getFileName() != null ? command.getFileName() : sessionEntity.getFileName());
        entity.setFilePath(filePath);
        entity.setFileSize(sessionEntity.getFileSize());
        entity.setFileMd5(fileMd5);
        entity.setStorageType(sessionEntity.getStorageType());
        entity.setStorageUrl(fileUrl);
        entity.setFileType(FileUtils.getFileType(entity.getFileName()));
        entity.setFileExtension(FileUtils.getFileExtensionCode(entity.getFileName()));
        entity.setTags(toJsonArray(command.getTags()));
        entity.setDescription(command.getDescription());
        entity.setIsPublic(command.getIsPublic());
        entity.setUploadStatus(UploadStatusEnums.COMPLETED.getCode());
        entity.setUploadProgress(100);
        entity.setUploadStartTime(now);
        entity.setUploadEndTime(now);
        entity.setUploadTime(now);
        return entity;
    }

    // ==================== FastUpload 转换 ====================

    /**
     * 构建秒传引用实体：复用原文件的物理存储信息，使用新的业务元数据
     */
    default FileBasicInfoEntity toFastUploadEntity(
            FastUploadCommand command,
            FileBasicInfoEntity originalEntity,
            String newFileId
    ) {
        LocalDateTime now = LocalDateTime.now();
        FileBasicInfoEntity entity = new FileBasicInfoEntity();
        entity.setFileId(newFileId);
        // 存储信息：完全复用原文件
        entity.setFilePath(originalEntity.getFilePath());
        entity.setFileSize(originalEntity.getFileSize());
        entity.setFileMd5(originalEntity.getFileMd5());
        entity.setFileSha256(originalEntity.getFileSha256());
        entity.setStorageType(originalEntity.getStorageType());
        entity.setStorageBucket(originalEntity.getStorageBucket());
        entity.setStorageRegion(originalEntity.getStorageRegion());
        entity.setStorageUrl(originalEntity.getStorageUrl());
        entity.setStorageMetadata(originalEntity.getStorageMetadata());
        entity.setFileType(originalEntity.getFileType());
        entity.setFileExtension(originalEntity.getFileExtension());
        // 业务信息：使用命令中的值
        entity.setFileName(command.getFileName());
        entity.setTags(toJsonArray(command.getTags()));
        entity.setDescription(command.getDescription());
        entity.setIsPublic(command.getIsPublic());
        // 状态信息
        entity.setUploadStatus(UploadStatusEnums.COMPLETED.getCode());
        entity.setUploadProgress(100);
        entity.setUploadStartTime(now);
        entity.setUploadEndTime(now);
        entity.setUploadTime(now);
        entity.setFileStatus(FileStatusEnums.NORMAL.getCode());
        entity.setIsEncrypted(YesNoEnums.NO.getCode());
        entity.setIsCompressed(YesNoEnums.NO.getCode());
        entity.setDownloadCount(0L);
        entity.setViewCount(0L);
        entity.setVersion(FileConstants.INITIAL_VERSION_NUMBER);
        return entity;
    }

    /**
     * 构建秒传返回结果
     */
    default UploadResult toFastUploadResult(
            String newFileId,
            FastUploadCommand command,
            FileBasicInfoEntity originalEntity,
            String message,
            long processingTime
    ) {
        LocalDateTime now = LocalDateTime.now();
        return UploadResult.builder()
                .fileId(newFileId)
                .fileName(command.getFileName())
                .fileSize(originalEntity.getFileSize())
                .fileMd5(originalEntity.getFileMd5())
                .fileSha256(originalEntity.getFileSha256())
                .fileUrl(originalEntity.getStorageUrl())
                .storageType(originalEntity.getStorageType())
                .tenantId(command.getTenantId())
                .uploadStatus(UploadStatusEnums.COMPLETED.getCode())
                .uploadStatusDesc(UploadStatusEnums.COMPLETED.getDesc())
                .isDuplicate(true)
                .duplicateFileId(originalEntity.getFileId())
                .sourceFileId(originalEntity.getFileId())
                .uploadTime(now)
                .processingTime(processingTime)
                .message(message)
                .build();
    }

    /**
     * 构建取消分片上传结果
     */
    default CancelMultipartResult toCancelMultipartResult(
            String uploadId,
            String fileId,
            int cancelledChunks,
            int totalUploadedChunks,
            String reason,
            String message
    ) {
        return CancelMultipartResult.builder()
                .uploadId(uploadId)
                .fileId(fileId)
                .success(true)
                .cancelledChunks(cancelledChunks)
                .totalUploadedChunks(totalUploadedChunks)
                .cancelledAt(LocalDateTime.now())
                .reason(reason)
                .message(message)
                .build();
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