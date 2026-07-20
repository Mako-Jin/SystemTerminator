package com.yaocode.sts.components.file.web.converter;

import com.yaocode.sts.components.file.core.exception.FileUploadException;
import com.yaocode.sts.components.file.interfaces.model.request.AsyncUploadRequest;
import com.yaocode.sts.components.file.interfaces.model.request.Base64UploadRequest;
import com.yaocode.sts.components.file.interfaces.model.request.CancelMultipartRequest;
import com.yaocode.sts.components.file.interfaces.model.request.CompleteMultipartRequest;
import com.yaocode.sts.components.file.interfaces.model.request.FastUploadRequest;
import com.yaocode.sts.components.file.interfaces.model.request.MultipartInitRequest;
import com.yaocode.sts.components.file.interfaces.model.request.ResumeUploadRequest;
import com.yaocode.sts.components.file.interfaces.model.request.UploadCallbackRequest;
import com.yaocode.sts.components.file.interfaces.model.request.UrlFetchRequest;
import com.yaocode.sts.components.file.interfaces.model.response.AsyncTaskStatusResponse;
import com.yaocode.sts.components.file.interfaces.model.response.AsyncUploadResponse;
import com.yaocode.sts.components.file.interfaces.model.response.FileExistenceResponse;
import com.yaocode.sts.components.file.interfaces.model.response.FileInfoResponse;
import com.yaocode.sts.components.file.interfaces.model.response.HybridUploadResponse;
import com.yaocode.sts.components.file.interfaces.model.response.MultipartInitResponse;
import com.yaocode.sts.components.file.interfaces.model.response.MultipartSessionResponse;
import com.yaocode.sts.components.file.interfaces.model.response.ResumeInfoResponse;
import com.yaocode.sts.components.file.interfaces.model.response.StorageCandidateResponse;
import com.yaocode.sts.components.file.interfaces.model.response.StorageUploadStatsResponse;
import com.yaocode.sts.components.file.interfaces.model.response.TopUploaderResponse;
import com.yaocode.sts.components.file.interfaces.model.response.TrendDataResponse;
import com.yaocode.sts.components.file.interfaces.model.response.TypeUploadStatsResponse;
import com.yaocode.sts.components.file.interfaces.model.response.UploadFileListResponse;
import com.yaocode.sts.components.file.interfaces.model.response.UploadPartResponse;
import com.yaocode.sts.components.file.interfaces.model.response.UploadProgressResponse;
import com.yaocode.sts.components.file.interfaces.model.response.UploadResponse;
import com.yaocode.sts.components.file.interfaces.model.response.UploadStatisticsResponse;
import com.yaocode.sts.components.file.interfaces.model.response.UploadStatusResponse;
import com.yaocode.sts.components.file.runtime.model.command.AsyncUploadCommand;
import com.yaocode.sts.components.file.runtime.model.command.AutoUploadCommand;
import com.yaocode.sts.components.file.runtime.model.command.Base64UploadCommand;
import com.yaocode.sts.components.file.runtime.model.command.CancelAsyncTaskCommand;
import com.yaocode.sts.components.file.runtime.model.command.CancelMultipartCommand;
import com.yaocode.sts.components.file.runtime.model.command.CompleteMultipartCommand;
import com.yaocode.sts.components.file.runtime.model.command.FastUploadCommand;
import com.yaocode.sts.components.file.runtime.model.command.HybridUploadCommand;
import com.yaocode.sts.components.file.runtime.model.command.InitMultipartCommand;
import com.yaocode.sts.components.file.runtime.model.command.PauseUploadCommand;
import com.yaocode.sts.components.file.runtime.model.command.ResumableUploadCommand;
import com.yaocode.sts.components.file.runtime.model.command.ResumeUploadCommand;
import com.yaocode.sts.components.file.runtime.model.command.StreamUploadCommand;
import com.yaocode.sts.components.file.runtime.model.command.UploadBatchCommand;
import com.yaocode.sts.components.file.runtime.model.command.UploadCallbackCommand;
import com.yaocode.sts.components.file.runtime.model.command.UploadFileCommand;
import com.yaocode.sts.components.file.runtime.model.command.UploadPartCommand;
import com.yaocode.sts.components.file.runtime.model.command.UrlFetchCommand;
import com.yaocode.sts.components.file.runtime.model.dto.FileObjectDto;
import com.yaocode.sts.components.file.runtime.model.query.AsyncTaskListQuery;
import com.yaocode.sts.components.file.runtime.model.query.AsyncTaskQuery;
import com.yaocode.sts.components.file.runtime.model.query.FileExistenceQuery;
import com.yaocode.sts.components.file.runtime.model.query.MultipartSessionQuery;
import com.yaocode.sts.components.file.runtime.model.query.ResumeInfoQuery;
import com.yaocode.sts.components.file.runtime.model.query.UploadFileListQuery;
import com.yaocode.sts.components.file.runtime.model.query.UploadProgressQuery;
import com.yaocode.sts.components.file.runtime.model.query.UploadStatisticsQuery;
import com.yaocode.sts.components.file.runtime.model.query.UploadStatusQuery;
import com.yaocode.sts.components.file.runtime.model.result.AsyncTaskResult;
import com.yaocode.sts.components.file.runtime.model.result.AsyncUploadResult;
import com.yaocode.sts.components.file.runtime.model.result.FileExistenceResult;
import com.yaocode.sts.components.file.runtime.model.result.FileInfoResult;
import com.yaocode.sts.components.file.runtime.model.result.HybridUploadResult;
import com.yaocode.sts.components.file.runtime.model.result.MultipartInitResult;
import com.yaocode.sts.components.file.runtime.model.result.MultipartSessionResult;
import com.yaocode.sts.components.file.runtime.model.result.ResumeInfoResult;
import com.yaocode.sts.components.file.runtime.model.result.StorageCandidateResult;
import com.yaocode.sts.components.file.runtime.model.result.StorageUploadStatsResult;
import com.yaocode.sts.components.file.runtime.model.result.TopUploaderResult;
import com.yaocode.sts.components.file.runtime.model.result.TrendDataResult;
import com.yaocode.sts.components.file.runtime.model.result.TypeUploadStatsResult;
import com.yaocode.sts.components.file.runtime.model.result.UploadFileResult;
import com.yaocode.sts.components.file.runtime.model.result.UploadPartResult;
import com.yaocode.sts.components.file.runtime.model.result.UploadProgressResult;
import com.yaocode.sts.components.file.runtime.model.result.UploadResult;
import com.yaocode.sts.components.file.runtime.model.result.UploadStatisticsResult;
import com.yaocode.sts.components.file.runtime.model.result.UploadStatusResult;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 文件上传转换器
 * <p>
 * 负责 HTTP Request/Response 与 Service Command/Query/Result 之间的转换
 * </p>
 *
 * @author yaocode
 * @since 1.0.0
 */
@Component
public class FileUploadConverter {

    // ==================== 上下文信息获取 ====================

    private String getCurrentTenantId() {
        // TODO: 从 SecurityContext 获取
        return "default";
    }

    private String getCurrentUserId() {
        // TODO: 从 SecurityContext 获取
        return "system";
    }

    private String getCurrentUserName() {
        // TODO: 从 SecurityContext 获取
        return "system";
    }

    // ==================== MultipartFile → FileObject ====================

    /**
     * 将 MultipartFile 转换为 FileObject
     */
    public FileObjectDto toFileObject(MultipartFile multipartFile) {
        if (multipartFile == null || multipartFile.isEmpty()) {
            return null;
        }

        try {
            return FileObjectDto.builder()
                    .fileName(multipartFile.getOriginalFilename())
                    .fileSize(multipartFile.getSize())
                    .inputStream(multipartFile.getInputStream())
                    .contentType(multipartFile.getContentType())
                    .originalFilename(multipartFile.getOriginalFilename())
                    .build();
        } catch (IOException e) {
            throw new FileUploadException("读取文件流失败", e);
        }
    }

    /**
     * 将 MultipartFile 列表转换为 FileObject 列表
     */
    public List<FileObjectDto> toFileObjectList(List<MultipartFile> multipartFiles) {
        if (multipartFiles == null || multipartFiles.isEmpty()) {
            return Collections.emptyList();
        }

        List<FileObjectDto> fileObjects = new ArrayList<>();
        for (MultipartFile file : multipartFiles) {
            FileObjectDto fileObject = toFileObject(file);
            if (fileObject != null) {
                fileObjects.add(fileObject);
            }
        }
        return fileObjects;
    }


    // ==================== Request → Command 转换 ====================

    /**
     * 普通上传 Request → Command
     */
    public UploadFileCommand toUploadFileCommand(
            MultipartFile file,
            Integer storageType,
            String businessId,
            String businessType,
            Boolean enableDeduplication,
            String tags,
            String description,
            Boolean isPublic,
            java.util.Map<String, String> metadata
    ) {

        FileObjectDto fileObject = toFileObject(file);

        return UploadFileCommand.builder()
                .file(fileObject)
                .fileName(fileObject != null ? fileObject.getFileName() : null)
                .fileSize(fileObject != null ? fileObject.getFileSize() : 0)
                .storageType(storageType)
                .businessId(businessId)
                .businessType(businessType)
                .enableDeduplication(enableDeduplication != null ? enableDeduplication : true)
                .tags(tags)
                .description(description)
                .isPublic(isPublic != null ? isPublic : false)
                .metadata(metadata)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    /**
     * 批量上传 Request → Command
     */
    public UploadBatchCommand toUploadBatchCommand(
            List<MultipartFile> files,
            String storageType,
            String businessId,
            String businessType,
            String tags,
            String description,
            Boolean isPublic
    ) {

        List<FileObjectDto> fileObjects = toFileObjectList(files);

        return UploadBatchCommand.builder()
                .files(fileObjects)
                .storageType(storageType)
                .businessId(businessId)
                .businessType(businessType)
                .tags(tags)
                .description(description)
                .isPublic(isPublic != null ? isPublic : false)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    /**
     * 分片初始化 Request → Command
     */
    public InitMultipartCommand toInitMultipartCommand(MultipartInitRequest request) {
        return InitMultipartCommand.builder()
                .fileName(request.getFileName())
                .fileSize(request.getFileSize())
                .chunkSize(request.getChunkSize() != null ? request.getChunkSize() : 10 * 1024 * 1024L)
                .fileMd5(request.getFileMd5())
                .fileType(request.getFileType())
                .storageType(request.getStorageType())
                .businessId(request.getBusinessId())
                .businessType(request.getBusinessType())
                .tags(request.getTags())
                .description(request.getDescription())
                .isPublic(request.getIsPublic() != null ? request.getIsPublic() : false)
                .metadata(request.getMetadata())
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    /**
     * 上传分片 Request → Command
     */
    public UploadPartCommand toUploadPartCommand(
            String uploadId,
            String fileId,
            Integer chunkNumber,
            Integer totalChunks,
            MultipartFile file,
            String chunkMd5
    ) {

        return UploadPartCommand.builder()
                .uploadId(uploadId)
                .fileId(fileId)
                .chunkNumber(chunkNumber)
                .totalChunks(totalChunks)
                .file(toFileObject(file))
                .chunkMd5(chunkMd5)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    /**
     * 完成分片 Request → Command
     */
    public CompleteMultipartCommand toCompleteMultipartCommand(CompleteMultipartRequest request) {
        return CompleteMultipartCommand.builder()
                .uploadId(request.getUploadId())
                .fileId(request.getFileId())
                .fileName(request.getFileName())
                .description(request.getDescription())
                .tags(request.getTags())
                .isPublic(request.getIsPublic() != null ? request.getIsPublic() : false)
                .metadata(request.getMetadata())
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    /**
     * 取消分片 Request → Command
     */
    public CancelMultipartCommand toCancelMultipartCommand(CancelMultipartRequest request) {
        return CancelMultipartCommand.builder()
                .uploadId(request.getUploadId())
                .fileId(request.getFileId())
                .reason(request.getReason())
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    /**
     * 恢复上传 Request → Command
     */
    public ResumeUploadCommand toResumeUploadCommand(ResumeUploadRequest request) {
        return ResumeUploadCommand.builder()
                .fileId(request.getFileId())
                .uploadId(request.getUploadId())
                .fileMd5(request.getFileMd5())
                .fileSize(request.getFileSize())
                .missingChunks(request.getMissingChunks())
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    /**
     * 暂停上传 Request → Command
     */
    public PauseUploadCommand toPauseUploadCommand(String fileId) {
        return PauseUploadCommand.builder()
                .fileId(fileId)
                .reason("用户暂停")
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    /**
     * 流式上传 Request → Command
     */
    public StreamUploadCommand toStreamUploadCommand(
            String fileName,
            Long fileSize,
            String storageType,
            String businessId,
            String businessType,
            String tags,
            String description,
            Boolean isPublic,
            java.io.InputStream inputStream,
            Boolean chunked
    ) {

        return StreamUploadCommand.builder()
                .fileName(fileName)
                .fileSize(fileSize)
                .storageType(storageType)
                .businessId(businessId)
                .businessType(businessType)
                .tags(tags)
                .description(description)
                .isPublic(isPublic != null ? isPublic : false)
                .inputStream(inputStream)
                .chunked(chunked != null ? chunked : false)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    /**
     * 秒传 Request → Command
     */
    public FastUploadCommand toFastUploadCommand(FastUploadRequest request) {
        return FastUploadCommand.builder()
                .fileName(request.getFileName())
                .fileMd5(request.getFileMd5())
                .fileSize(request.getFileSize())
                .storageType(request.getStorageType())
                .businessId(request.getBusinessId())
                .businessType(request.getBusinessType())
                .tags(request.getTags())
                .description(request.getDescription())
                .isPublic(request.getIsPublic() != null ? request.getIsPublic() : false)
                .metadata(request.getMetadata())
                .fileType(request.getFileType())
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    /**
     * 异步上传 Request → Command
     */
    public AsyncUploadCommand toAsyncUploadCommand(AsyncUploadRequest request) {
        return AsyncUploadCommand.builder()
                .fileName(request.getFileName())
                .fileSize(request.getFileSize())
                .fileMd5(request.getFileMd5())
                .storageType(request.getStorageType())
                .businessId(request.getBusinessId())
                .businessType(request.getBusinessType())
                .tags(request.getTags())
                .description(request.getDescription())
                .isPublic(request.getIsPublic() != null ? request.getIsPublic() : false)
                .metadata(request.getMetadata())
                .fileContentBase64(request.getFileContentBase64())
                .fileUrl(request.getFileUrl())
                .callbackUrl(request.getCallbackUrl())
                .callbackMethod(request.getCallbackMethod() != null ? request.getCallbackMethod() : "POST")
                .callbackHeaders(request.getCallbackHeaders())
                .priority(request.getPriority() != null ? request.getPriority() : 5)
                .timeoutSeconds(request.getTimeoutSeconds() != null ? request.getTimeoutSeconds() : 300)
                .deleteAfterComplete(request.getDeleteAfterComplete() != null ? request.getDeleteAfterComplete() : false)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    /**
     * 取消异步任务 Request → Command
     */
    public CancelAsyncTaskCommand toCancelAsyncTaskCommand(String taskId) {
        return CancelAsyncTaskCommand.builder()
                .taskId(taskId)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    /**
     * 上传回调 Request → Command
     */
    public UploadCallbackCommand toUploadCallbackCommand(UploadCallbackRequest request) {
        return UploadCallbackCommand.builder()
                .taskId(request.getTaskId())
                .fileId(request.getFileId())
                .fileName(request.getFileName())
                .fileSize(request.getFileSize())
                .fileUrl(request.getFileUrl())
                .status(request.getStatus())
                .message(request.getMessage())
                .data(request.getData())
                .timestamp(request.getTimestamp())
                .signature(request.getSignature())
                .tenantId(getCurrentTenantId())
                .build();
    }

    /**
     * Base64上传 Request → Command
     */
    public Base64UploadCommand toBase64UploadCommand(Base64UploadRequest request) {
        return Base64UploadCommand.builder()
                .fileName(request.getFileName())
                .base64Content(request.getBase64Content())
                .storageType(request.getStorageType())
                .businessId(request.getBusinessId())
                .businessType(request.getBusinessType())
                .tags(request.getTags())
                .description(request.getDescription())
                .isPublic(request.getIsPublic() != null ? request.getIsPublic() : false)
                .metadata(request.getMetadata())
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    /**
     * URL拉取 Request → Command
     */
    public UrlFetchCommand toUrlFetchCommand(UrlFetchRequest request) {
        return UrlFetchCommand.builder()
                .fileUrl(request.getFileUrl())
                .fileName(request.getFileName())
                .storageType(request.getStorageType())
                .businessId(request.getBusinessId())
                .businessType(request.getBusinessType())
                .tags(request.getTags())
                .description(request.getDescription())
                .isPublic(request.getIsPublic() != null ? request.getIsPublic() : false)
                .timeout(request.getTimeout() != null ? request.getTimeout() : 60)
                .headers(request.getHeaders())
                .metadata(request.getMetadata())
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    /**
     * 自动上传 Request → Command
     */
    public AutoUploadCommand toAutoUploadCommand(
            MultipartFile file,
            String storageType,
            String businessId,
            String businessType,
            Long chunkThreshold,
            String tags,
            String description,
            Boolean isPublic
    ) {

        return AutoUploadCommand.builder()
                .file(toFileObject(file))
                .storageType(storageType)
                .businessId(businessId)
                .businessType(businessType)
                .chunkThreshold(chunkThreshold != null ? chunkThreshold : 10 * 1024 * 1024L)
                .tags(tags)
                .description(description)
                .isPublic(isPublic != null ? isPublic : false)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    /**
     * 可恢复上传 Request → Command
     */
    public ResumableUploadCommand toResumableUploadCommand(
            MultipartFile file,
            String fileId,
            String storageType,
            String businessId,
            String businessType,
            String tags,
            String description
    ) {

        return ResumableUploadCommand.builder()
                .file(toFileObject(file))
                .fileId(fileId)
                .storageType(storageType)
                .businessId(businessId)
                .businessType(businessType)
                .tags(tags)
                .description(description)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    /**
     * 混合云上传 Request → Command
     */
    public HybridUploadCommand toHybridUploadCommand(
            MultipartFile file,
            List<String> preferredStorages,
            String storageType,
            String businessId,
            String businessType,
            String strategy,
            String tags,
            String description
    ) {

        return HybridUploadCommand.builder()
                .file(toFileObject(file))
                .preferredStorages(preferredStorages)
                .storageType(storageType)
                .businessId(businessId)
                .businessType(businessType)
                .strategy(strategy != null ? strategy : "auto")
                .tags(tags)
                .description(description)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    // ==================== Query 转换 ====================

    /**
     * 构建上传进度查询
     */
    public UploadProgressQuery toUploadProgressQuery(String uploadId) {
        return UploadProgressQuery.builder()
                .uploadId(uploadId)
                .tenantId(getCurrentTenantId())
                .build();
    }

    /**
     * 构建分片会话查询
     */
    public MultipartSessionQuery toMultipartSessionQuery(Integer page, Integer size) {
        return MultipartSessionQuery.builder()
                .page(page != null ? page : 1)
                .size(size != null ? size : 20)
                .tenantId(getCurrentTenantId())
                .build();
    }

    /**
     * 构建断点续传信息查询
     */
    public ResumeInfoQuery toResumeInfoQuery(String fileId) {
        return ResumeInfoQuery.builder()
                .fileId(fileId)
                .tenantId(getCurrentTenantId())
                .build();
    }

    /**
     * 构建异步任务查询
     */
    public AsyncTaskQuery toAsyncTaskQuery(String taskId) {
        return AsyncTaskQuery.builder()
                .taskId(taskId)
                .tenantId(getCurrentTenantId())
                .build();
    }

    /**
     * 构建异步任务列表查询
     */
    public AsyncTaskListQuery toAsyncTaskListQuery(Integer taskStatus, Integer page, Integer size) {
        return AsyncTaskListQuery.builder()
                .taskStatus(taskStatus)
                .page(page != null ? page : 1)
                .size(size != null ? size : 20)
                .tenantId(getCurrentTenantId())
                .build();
    }

    /**
     * 构建上传状态查询
     */
    public UploadStatusQuery toUploadStatusQuery(String fileId) {
        return UploadStatusQuery.builder()
                .fileId(fileId)
                .tenantId(getCurrentTenantId())
                .build();
    }

    /**
     * 构建用户文件列表查询
     */
    public UploadFileListQuery toUploadFileListQuery(
            Integer page,
            Integer size,
            Integer fileStatus,
            String storageType,
            String fileName,
            String startTime,
            String endTime
    ) {

        return UploadFileListQuery.builder()
                .page(page != null ? page : 1)
                .size(size != null ? size : 20)
                .fileStatus(fileStatus)
                .storageType(storageType)
                .fileName(fileName)
                .startTime(startTime)
                .endTime(endTime)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    public UploadStatisticsQuery toUploadStatisticsQuery(String period) {
        return UploadStatisticsQuery.builder()
                .period(period != null ? period : "week")
                .tenantId(getCurrentTenantId())
                .build();
    }

    /**
     * 构建文件存在性查询
     */
    public FileExistenceQuery toFileExistenceQuery(String fileMd5, Long fileSize, Integer storageType) {
        return FileExistenceQuery.builder()
                .fileMd5(fileMd5)
                .fileSize(fileSize)
                .storageType(storageType)
                .tenantId(getCurrentTenantId())
                .build();
    }

    // ==================== Result → Response 转换 ====================

    /**
     * 上传结果 → 上传响应
     */
    public UploadResponse toUploadResponse(UploadResult result) {
        if (result == null) {
            return null;
        }
        return UploadResponse.builder()
                .fileId(result.getFileId())
                .fileName(result.getFileName())
                .fileSize(result.getFileSize())
                .fileMd5(result.getFileMd5())
                .fileUrl(result.getFileUrl())
                .storageType(result.getStorageType())
                .tenantId(result.getTenantId())
                .uploadStatus(result.getUploadStatus())
                .uploadStatusDesc(result.getUploadStatusDesc())
                .isDuplicate(result.getIsDuplicate())
                .duplicateFileId(result.getDuplicateFileId())
                .uploadTime(result.getUploadTime())
                .processingTime(result.getProcessingTime())
                .message(result.getMessage())
                .build();
    }

    /**
     * 上传结果列表 → 上传响应列表
     */
    public List<UploadResponse> toUploadResponseList(List<UploadResult> results) {
        if (results == null) {
            return null;
        }
        return results.stream()
                .map(this::toUploadResponse)
                .collect(Collectors.toList());
    }

    /**
     * 分片初始化结果 → 分片初始化响应
     */
    public MultipartInitResponse toMultipartInitResponse(MultipartInitResult result) {
        if (result == null) {
            return null;
        }
        return MultipartInitResponse.builder()
                .uploadId(result.getUploadId())
                .fileId(result.getFileId())
                .fileName(result.getFileName())
                .fileSize(result.getFileSize())
                .chunkSize(result.getChunkSize())
                .totalChunks(result.getTotalChunks())
                .expireTime(result.getExpireTime())
                .storageType(result.getStorageType())
                .build();
    }

    public UploadPartResponse toUploadPartResponse(UploadPartResult result) {
        if (result == null) {
            return null;
        }
        return UploadPartResponse.builder()
                .uploadId(result.getUploadId())
                .fileId(result.getFileId())
                .chunkNumber(result.getChunkNumber())
                .totalChunks(result.getTotalChunks())
                .success(result.getSuccess())
                .uploadedChunks(result.getUploadedChunks())
                .progress(result.getProgress())
                .build();
    }

    /**
     * 上传进度结果 → 上传进度响应
     */
    public UploadProgressResponse toUploadProgressResponse(UploadProgressResult result) {
        if (result == null) {
            return null;
        }
        return UploadProgressResponse.builder()
                .uploadId(result.getUploadId())
                .fileId(result.getFileId())
                .fileName(result.getFileName())
                .fileSize(result.getFileSize())
                .chunkSize(result.getChunkSize())
                .totalChunks(result.getTotalChunks())
                .uploadedChunks(result.getUploadedChunks())
                .progress(result.getProgress())
                .uploadedSize(result.getUploadedSize())
                .status(result.getStatus())
                .lastActiveTime(result.getLastActiveTime())
                .estimatedRemainingTime(result.getEstimatedRemainingTime())
                .uploadSpeed(result.getUploadSpeed())
                .message(result.getMessage())
                .build();
    }

    public MultipartSessionResponse toMultipartSessionResponse(MultipartSessionResult result) {
        if (result == null) {
            return null;
        }
        return MultipartSessionResponse.builder()
                .uploadId(result.getUploadId())
                .fileId(result.getFileId())
                .fileName(result.getFileName())
                .fileSize(result.getFileSize())
                .chunkSize(result.getChunkSize())
                .totalChunks(result.getTotalChunks())
                .uploadedChunks(result.getUploadedChunks())
                .progress(result.getProgress())
                .status(result.getStatus())
                .expireTime(result.getExpireTime())
                .lastActiveTime(result.getLastActiveTime())
                .createdTime(result.getCreatedTime())
                .build();
    }

    /**
     * 分片会话结果列表 → 分片会话响应列表
     */
    public List<MultipartSessionResponse> toMultipartSessionResponseList(List<MultipartSessionResult> results) {
        if (results == null) {
            return null;
        }
        return results.stream()
                .map(this::toMultipartSessionResponse)
                .collect(Collectors.toList());
    }

    public ResumeInfoResponse toResumeInfoResponse(ResumeInfoResult result) {
        if (result == null) {
            return null;
        }
        return ResumeInfoResponse.builder()
                .fileId(result.getFileId())
                .fileName(result.getFileName())
                .fileSize(result.getFileSize())
                .uploadId(result.getUploadId())
                .totalChunks(result.getTotalChunks())
                .uploadedChunks(result.getUploadedChunks())
                .progress(result.getProgress())
                .uploadedChunks(result.getUploadedChunks())
                .missingChunks(result.getMissingChunks())
                .canResume(result.getCanResume())
                .status(result.getStatus())
                .build();
    }

    public AsyncUploadResponse toAsyncUploadResponse(AsyncUploadResult result) {
        if (result == null) {
            return null;
        }
        return AsyncUploadResponse.builder()
                .taskId(result.getTaskId())
                .fileId(result.getFileId())
                .fileName(result.getFileName())
                .fileSize(result.getFileSize())
                .status(result.getStatus())
                .progress(result.getProgress())
                .message(result.getMessage())
                .estimatedTime(result.getEstimatedTime())
                .statusUrl(result.getStatusUrl())
                .build();
    }

    public AsyncTaskStatusResponse toAsyncTaskStatusResponse(AsyncTaskResult result) {
        if (result == null) {
            return null;
        }
        return AsyncTaskStatusResponse.builder()
                .taskId(result.getTaskId())
                .fileId(result.getFileId())
                .fileName(result.getFileName())
                .fileSize(result.getFileSize())
                .fileMd5(result.getFileMd5())
                .status(result.getStatus())
                .statusDesc(result.getStatusDesc())
                .progress(result.getProgress())
                .errorMessage(result.getErrorMessage())
                .resultData(result.getResultData())
                .retryCount(result.getRetryCount())
                .startTime(result.getStartTime())
                .endTime(result.getEndTime())
                .elapsedTime(result.getElapsedTime())
                .callbackUrl(result.getCallbackUrl())
                .callbackSuccess(result.getCallbackSuccess())
                .createdTime(result.getCreatedTime())
                .build();
    }

    public List<AsyncTaskStatusResponse> toAsyncTaskStatusResponseList(List<AsyncTaskResult> results) {
        if (results == null) {
            return null;
        }
        return results.stream()
                .map(this::toAsyncTaskStatusResponse)
                .collect(Collectors.toList());
    }

    public FileExistenceResponse toFileExistenceResponse(FileExistenceResult result) {
        if (result == null) {
            return null;
        }
        return FileExistenceResponse.builder()
                .exists(result.getExists())
                .fileId(result.getFileId())
                .fileName(result.getFileName())
                .fileSize(result.getFileSize())
                .fileMd5(result.getFileMd5())
                .fileUrl(result.getFileUrl())
                .storageType(result.getStorageType())
                .isDuplicate(result.getIsDuplicate())
                .duplicateFiles(toFileInfoResponseList(result.getDuplicateFiles()))
                .build();
    }

    public FileInfoResponse toFileInfoResponse(FileInfoResult result) {
        if (result == null) {
            return null;
        }
        return FileInfoResponse.builder()
                .fileId(result.getFileId())
                .fileName(result.getFileName())
                .fileSize(result.getFileSize())
                .fileMd5(result.getFileMd5())
                .fileType(result.getFileType())
                .fileExtension(result.getFileExtension())
                .storageType(result.getStorageType())
                .storageUrl(result.getStorageUrl())
                .fileStatus(result.getFileStatus())
                .fileStatusDesc(result.getFileStatusDesc())
                .downloadCount(result.getDownloadCount())
                .tags(result.getTags())
                .description(result.getDescription())
                .createdUserId(result.getCreatedUserId())
                .createdUserName(result.getCreatedUserName())
                .createdTime(result.getCreatedTime())
                .build();
    }

    public List<FileInfoResponse> toFileInfoResponseList(List<FileInfoResult> results) {
        if (CollectionUtils.isEmpty(results)) {
            return Collections.emptyList();
        }
        return results.stream()
                .map(this::toFileInfoResponse)
                .collect(Collectors.toList());
    }

    public UploadStatusResponse toUploadStatusResponse(UploadStatusResult result) {
        if (result == null) {
            return null;
        }
        return UploadStatusResponse.builder()
                .fileId(result.getFileId())
                .fileName(result.getFileName())
                .fileSize(result.getFileSize())
                .fileMd5(result.getFileMd5())
                .storageType(result.getStorageType())
                .uploadStatus(result.getUploadStatus())
                .uploadStatusDesc(result.getUploadStatusDesc())
                .progress(result.getProgress())
                .uploadId(result.getUploadId())
                .totalChunks(result.getTotalChunks())
                .uploadedChunks(result.getUploadedChunks())
                .uploadStartTime(result.getUploadStartTime())
                .uploadEndTime(result.getUploadEndTime())
                .estimatedRemainingTime(result.getEstimatedRemainingTime())
                .errorMessage(result.getErrorMessage())
                .build();
    }

    public UploadFileListResponse toUploadFileListResponse(UploadFileResult result) {
        if (result == null) {
            return null;
        }
        return UploadFileListResponse.builder()
                .fileId(result.getFileId())
                .fileName(result.getFileName())
                .fileSize(result.getFileSize())
                .fileType(result.getFileType())
                .fileMd5(result.getFileMd5())
                .storageType(result.getStorageType())
                .fileStatus(result.getFileStatus())
                .fileStatusDesc(result.getFileStatusDesc())
                .uploadStatus(result.getUploadStatus())
                .downloadCount(result.getDownloadCount())
                .tags(result.getTags())
                .description(result.getDescription())
                .createdUserId(result.getCreatedUserId())
                .createdUserName(result.getCreatedUserName())
                .createdTime(result.getCreatedTime())
                .build();
    }

    public List<UploadFileListResponse> toUploadFileListResponseList(List<UploadFileResult> results) {
        if (results == null) {
            return null;
        }
        return results.stream()
                .map(this::toUploadFileListResponse)
                .collect(Collectors.toList());
    }

    public UploadStatisticsResponse toUploadStatisticsResponse(UploadStatisticsResult result) {
        if (result == null) {
            return null;
        }
        return UploadStatisticsResponse.builder()
                .totalFiles(result.getTotalFiles())
                .totalSize(result.getTotalSize())
                .todayUploads(result.getTodayUploads())
                .todaySize(result.getTodaySize())
                .weekUploads(result.getWeekUploads())
                .weekSize(result.getWeekSize())
                .monthUploads(result.getMonthUploads())
                .monthSize(result.getMonthSize())
                .storageStats(toStorageUploadStatsResponseMap(result.getStorageStats()))
                .typeStats(toTypeUploadStatsResponseMap(result.getTypeStats()))
                .uploadTrend(toTrendDataResponseList(result.getUploadTrend()))
                .activeUsers(result.getActiveUsers())
                .newUsers(result.getNewUsers())
                .topUploaders(toTopUploaderResponseList(result.getTopUploaders()))
                .build();
    }

    /**
     * 上传排行榜结果列表 → 上传排行榜响应列表
     */
    public List<TopUploaderResponse> toTopUploaderResponseList(List<TopUploaderResult> results) {
        if (CollectionUtils.isEmpty(results)) {
            return Collections.emptyList();
        }
        return results.stream()
                .map(this::toTopUploaderResponse)
                .collect(Collectors.toList());
    }

    /**
     * 上传排行榜结果 → 上传排行榜响应
     */
    public TopUploaderResponse toTopUploaderResponse(TopUploaderResult result) {
        if (result == null) {
            return null;
        }
        return TopUploaderResponse.builder()
                .userId(result.getUserId())
                .userName(result.getUserName())
                .fileCount(result.getFileCount())
                .totalSize(result.getTotalSize())
                .build();
    }

    /**
     * 文件类型统计结果 → 文件类型统计响应
     */
    public TypeUploadStatsResponse toTypeUploadStatsResponse(TypeUploadStatsResult result) {
        if (result == null) {
            return null;
        }
        return TypeUploadStatsResponse.builder()
                .fileType(result.getFileType())
                .fileCount(result.getFileCount())
                .totalSize(result.getTotalSize())
                .percentage(result.getPercentage())
                .build();
    }

    /**
     * 文件类型统计结果 Map → 文件类型统计响应 Map
     */
    public Map<String, TypeUploadStatsResponse> toTypeUploadStatsResponseMap(Map<String, TypeUploadStatsResult> results) {

        if (CollectionUtils.isEmpty(results)) {
            return Collections.emptyMap();
        }

        return results.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> toTypeUploadStatsResponse(entry.getValue())
                ));
    }

    /**
     * 趋势数据结果列表 → 趋势数据响应列表
     */
    public List<TrendDataResponse> toTrendDataResponseList(List<TrendDataResult> results) {
        if (CollectionUtils.isEmpty(results)) {
            return Collections.emptyList();
        }
        return results.stream()
                .map(this::toTrendDataResponse)
                .collect(Collectors.toList());
    }

    /**
     * 趋势数据结果 → 趋势数据响应
     */
    public TrendDataResponse toTrendDataResponse(TrendDataResult result) {
        if (result == null) {
            return null;
        }
        return TrendDataResponse.builder()
                .date(result.getDate())
                .uploadCount(result.getUploadCount())
                .downloadCount(result.getDownloadCount())
                .totalSize(result.getTotalSize())
                .uniqueUsers(result.getUniqueUsers())
                .avgUploadSize(result.getAvgUploadSize())
                .uploadSize(result.getUploadSize())
                .downloadSize(result.getDownloadSize())
                .build();
    }

    public StorageUploadStatsResponse toStorageUploadStatsResponse(StorageUploadStatsResult result) {
        if (result == null) {
            return null;
        }
        return StorageUploadStatsResponse.builder()
                .storageType(result.getStorageType())
                .storageTypeDesc(result.getStorageTypeDesc())
                .fileCount(result.getFileCount())
                .totalSize(result.getTotalSize())
                .percentage(result.getPercentage())
                .build();
    }

    /**
     * 存储上传统计结果Map → 存储上传统计响应Map
     */
    public Map<String, StorageUploadStatsResponse> toStorageUploadStatsResponseMap(Map<String, StorageUploadStatsResult> results) {
        if (CollectionUtils.isEmpty(results)) {
            return Collections.emptyMap();
        }
        return results.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> toStorageUploadStatsResponse(entry.getValue())
                ));
    }

    /**
     * 混合云上传结果 → 混合云上传响应
     */
    public HybridUploadResponse toHybridUploadResponse(HybridUploadResult result) {
        if (result == null) {
            return null;
        }
        HybridUploadResponse response = new HybridUploadResponse();
        // 复制父类属性
        response.setFileId(result.getFileId());
        response.setFileName(result.getFileName());
        response.setFileSize(result.getFileSize());
        response.setFileMd5(result.getFileMd5());
        response.setFileUrl(result.getFileUrl());
        response.setStorageType(result.getStorageType());
        response.setTenantId(result.getTenantId());
        response.setUploadStatus(result.getUploadStatus());
        response.setUploadStatusDesc(result.getUploadStatusDesc());
        response.setIsDuplicate(result.getIsDuplicate());
        response.setDuplicateFileId(result.getDuplicateFileId());
        response.setUploadTime(result.getUploadTime());
        response.setProcessingTime(result.getProcessingTime());
        response.setMessage(result.getMessage());
        // 扩展属性
        response.setSelectedStorage(result.getSelectedStorage());
        response.setStrategy(result.getStrategy());
        response.setSelectionReason(result.getSelectionReason());
        response.setCandidates(toStorageCandidateResponseList(result.getCandidates()));
        response.setDecisionMetadata(result.getDecisionMetadata());
        return response;
    }

    /**
     * 存储候选结果列表 → 存储候选响应列表
     */
    public List<StorageCandidateResponse> toStorageCandidateResponseList(
            List<StorageCandidateResult> results) {
        if (CollectionUtils.isEmpty(results)) {
            return Collections.emptyList();
        }
        return results.stream()
                .map(this::toStorageCandidateResponse)
                .collect(Collectors.toList());
    }

    /**
     * 存储候选结果 → 存储候选响应
     */
    public StorageCandidateResponse toStorageCandidateResponse(StorageCandidateResult result) {
        if (result == null) {
            return null;
        }
        return StorageCandidateResponse.builder()
                .storageType(result.getStorageType())
                .storageTypeDesc(result.getStorageTypeDesc())
                .score(result.getScore())
                .cost(result.getCost())
                .performance(result.getPerformance())
                .reliability(result.getReliability())
                .recommendation(result.getRecommendation())
                .build();
    }
}