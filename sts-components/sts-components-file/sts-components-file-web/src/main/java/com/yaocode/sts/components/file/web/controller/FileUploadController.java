package com.yaocode.sts.components.file.web.controller;


import com.yaocode.sts.common.basic.model.PageResult;
import com.yaocode.sts.common.web.annotation.SubRequestMapping;
import com.yaocode.sts.common.web.model.PageResultModel;
import com.yaocode.sts.common.web.model.ResultModel;
import com.yaocode.sts.common.web.utils.PageResultUtils;
import com.yaocode.sts.common.web.utils.ResultUtils;
import com.yaocode.sts.components.file.interfaces.api.FileUploadApi;
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
import com.yaocode.sts.components.file.interfaces.model.response.HybridUploadResponse;
import com.yaocode.sts.components.file.interfaces.model.response.MultipartInitResponse;
import com.yaocode.sts.components.file.interfaces.model.response.MultipartSessionResponse;
import com.yaocode.sts.components.file.interfaces.model.response.ResumeInfoResponse;
import com.yaocode.sts.components.file.interfaces.model.response.UploadFileListResponse;
import com.yaocode.sts.components.file.interfaces.model.response.UploadPartResponse;
import com.yaocode.sts.components.file.interfaces.model.response.UploadProgressResponse;
import com.yaocode.sts.components.file.interfaces.model.response.UploadResponse;
import com.yaocode.sts.components.file.interfaces.model.response.UploadStatisticsResponse;
import com.yaocode.sts.components.file.interfaces.model.response.UploadStatusResponse;
import com.yaocode.sts.components.file.runtime.model.result.AsyncTaskResult;
import com.yaocode.sts.components.file.runtime.model.result.MultipartSessionResult;
import com.yaocode.sts.components.file.runtime.model.result.UploadFileResult;
import com.yaocode.sts.components.file.runtime.service.FileUploadService;
import com.yaocode.sts.components.file.web.converter.FileUploadConverter;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 文件上传控制器
 *
 * @author yaocode
 * @since 1.0.0
 */
@Slf4j
@RestController
@SubRequestMapping("/v1")
public class FileUploadController implements FileUploadApi {

    @Resource
    private FileUploadService fileUploadService;

    @Resource
    private FileUploadConverter converter;

    // ==================== 1. 普通上传 ====================

    /**
     * 普通文件上传（单文件）
     */
    @Override
    public ResultModel<UploadResponse> uploadFile(
            MultipartFile file,
            String storageType,
            String businessId,
            String businessType,
            Boolean enableDeduplication,
            String tags,
            String description,
            Boolean isPublic,
            Map<String, String> metadata
    ) {

        log.info("文件上传: {}, 大小: {}, 存储类型: {}",
                file.getOriginalFilename(), file.getSize(), storageType);

        UploadResponse response = converter.toUploadResponse(
                fileUploadService.upload(
                        converter.toUploadFileCommand(
                                file, storageType, businessId, businessType,
                                enableDeduplication, tags, description, isPublic, metadata
                        )
                )
        );
        return ResultUtils.ok(response);
    }

    /**
     * 批量文件上传
     */
    @Override
    public ResultModel<List<UploadResponse>> uploadBatchFiles(
            List<MultipartFile> files,
            String storageType,
            String businessId,
            String businessType,
            String tags,
            String description,
            Boolean isPublic
    ) {

        log.info("批量上传文件: {} 个文件", files.size());

        List<UploadResponse> responses = converter.toUploadResponseList(
                fileUploadService.uploadBatch(
                        converter.toUploadBatchCommand(
                                files, storageType, businessId, businessType,
                                tags, description, isPublic
                        )
                )
        );
        return ResultUtils.ok(responses);
    }

    // ==================== 2. 分片上传 ====================

    /**
     * 初始化分片上传
     */
    @Override
    public ResultModel<MultipartInitResponse> initMultipartUpload(@Valid MultipartInitRequest request) {
        log.info("初始化分片上传: {}, 大小: {}", request.getFileName(), request.getFileSize());

        MultipartInitResponse response = converter.toMultipartInitResponse(
                fileUploadService.initMultipartUpload(
                        converter.toInitMultipartCommand(request)
                )
        );
        return ResultUtils.ok(response);
    }


    /**
     * 上传分片
     */
    @Override
    public ResultModel<UploadPartResponse> uploadPart(
            String uploadId,
            String fileId,
            Integer chunkNumber,
            Integer totalChunks,
            MultipartFile file,
            String chunkMd5
    ) {

        log.info("上传分片: uploadId={}, chunkNumber={}/{}", uploadId, chunkNumber, totalChunks);

        UploadPartResponse response = converter.toUploadPartResponse(
                fileUploadService.uploadPart(
                        converter.toUploadPartCommand(
                                uploadId, fileId, chunkNumber, totalChunks, file, chunkMd5
                        )
                )
        );
        return ResultUtils.ok(response);
    }

    /**
     * 完成分片上传（合并分片）
     */
    @Override
    public ResultModel<UploadResponse> completeMultipartUpload(@Valid CompleteMultipartRequest request) {
        log.info("完成分片上传: uploadId={}", request.getUploadId());

        UploadResponse response = converter.toUploadResponse(
                fileUploadService.completeMultipartUpload(
                        converter.toCompleteMultipartCommand(request)
                )
        );
        return ResultUtils.ok(response);
    }

    /**
     * 取消分片上传
     */
    @Override
    public ResultModel<String> cancelMultipartUpload(@Valid CancelMultipartRequest request) {
        log.info("取消分片上传: uploadId={}", request.getUploadId());

        fileUploadService.cancelMultipartUpload(
                converter.toCancelMultipartCommand(request)
        );
        return ResultUtils.ok("取消成功");
    }

    /**
     * 获取分片上传进度
     */
    @Override
    public ResultModel<UploadProgressResponse> getMultipartProgress(String uploadId) {
        log.info("获取分片上传进度: {}", uploadId);

        UploadProgressResponse response = converter.toUploadProgressResponse(
                fileUploadService.getMultipartProgress(
                        converter.toUploadProgressQuery(uploadId)
                )
        );
        return ResultUtils.ok(response);
    }

    @Override
    public PageResultModel<MultipartSessionResponse> getMultipartSessions(Integer page, Integer size) {
        log.info("获取分片上传会话列表: page={}, size={}", page, size);

        PageResult<MultipartSessionResult> result = fileUploadService.getMultipartSessions(
                converter.toMultipartSessionQuery(page, size)
        );
        return PageResultUtils.ok(result.getTotal(), converter.toMultipartSessionResponseList(result.getRecords()));
    }

    // ==================== 3. 断点续传 ====================

    /**
     * 断点续传 - 获取已上传的分片信息
     */
    @Override
    public ResultModel<ResumeInfoResponse> getResumeInfo(String fileId) {
        log.info("获取断点续传信息: {}", fileId);

        ResumeInfoResponse response = converter.toResumeInfoResponse(
                fileUploadService.getResumeInfo(
                        converter.toResumeInfoQuery(fileId)
                )
        );
        return ResultUtils.ok(response);
    }

    /**
     * 断点续传 - 恢复上传
     */
    @Override
    public ResultModel<UploadResponse> restoreUpload(@Valid ResumeUploadRequest request) {
        log.info("恢复上传: fileId={}", request.getFileId());

        UploadResponse response = converter.toUploadResponse(
                fileUploadService.restoreUpload(
                        converter.toResumeUploadCommand(request)
                )
        );
        return ResultUtils.ok(response);
    }

    /**
     * 暂停上传
     */
    @Override
    public ResultModel<String> pauseUpload(String fileId) {
        log.info("暂停上传: {}", fileId);

        fileUploadService.pauseUpload(
                converter.toPauseUploadCommand(fileId)
        );
        return ResultUtils.ok();
    }

    // ==================== 4. 流式直传 ====================

    /**
     * 流式直传 - 从InputStream直接上传
     * 适用于: 实时生成的数据流、大文件流式处理、不产生临时文件
     */
    @Override
    public ResultModel<UploadResponse> streamUpload(
            String fileName,
            Long fileSize,
            String storageType,
            String businessId,
            String businessType,
            String tags,
            String description,
            Boolean isPublic,
            InputStream inputStream,
            HttpServletRequest request
    ) throws java.io.IOException {

        log.info("流式上传: {}, 大小: {}", fileName, fileSize);

        // 如果未指定文件大小，从Content-Length获取
        if (fileSize == null || fileSize <= 0) {
            String contentLength = request.getHeader("Content-Length");
            if (contentLength != null) {
                fileSize = Long.parseLong(contentLength);
            } else {
                fileSize = -1L;
            }
        }

        UploadResponse response = converter.toUploadResponse(
                fileUploadService.streamUpload(
                        converter.toStreamUploadCommand(
                                fileName, fileSize, storageType, businessId, businessType,
                                tags, description, isPublic, inputStream, false
                        )
                )
        );
        return ResultUtils.ok(response);
    }

    /**
     * 流式直传 - 支持分块流式上传
     */
    @Override
    public ResultModel<UploadResponse> chunkedStreamUpload(
            String fileName,
            Long fileSize,
            String storageType,
            String businessId,
            String businessType,
            String tags,
            String description,
            Boolean isPublic,
            InputStream inputStream
    ) {

        log.info("分块流式上传: {}", fileName);

        UploadResponse response = converter.toUploadResponse(
                fileUploadService.chunkedStreamUpload(
                        converter.toStreamUploadCommand(
                                fileName, fileSize, storageType, businessId, businessType,
                                tags, description, isPublic, inputStream, true
                        )
                )
        );
        return ResultUtils.ok(response);
    }

    // ==================== 5. 秒传 ====================

    /**
     * 秒传 - 检查文件是否已存在
     */
    @Override
    public ResultModel<FileExistenceResponse> checkFileExists(String fileMd5, Long fileSize, String storageType) {
        log.info("检查文件是否存在: md5={}, size={}", fileMd5, fileSize);

        FileExistenceResponse response = converter.toFileExistenceResponse(
                fileUploadService.checkFileExists(
                        converter.toFileExistenceQuery(fileMd5, fileSize, storageType)
                )
        );
        return ResultUtils.ok(response);
    }

    /**
     * 秒传 - 直接获取已存在文件的信息
     */
    @Override
    public ResultModel<UploadResponse> fastUpload(@Valid FastUploadRequest request) {
        log.info("秒传: {}", request.getFileName());

        UploadResponse response = converter.toUploadResponse(
                fileUploadService.fastUpload(
                        converter.toFastUploadCommand(request)
                )
        );
        return ResultUtils.ok(response);
    }

    // ==================== 6. 异步上传 ====================

    /**
     * 异步上传 - 提交异步上传任务
     */
    @Override
    public ResultModel<AsyncUploadResponse> asyncUpload(@Valid AsyncUploadRequest request) {
        log.info("异步上传: {}", request.getFileName());

        AsyncUploadResponse response = converter.toAsyncUploadResponse(
                fileUploadService.asyncUpload(
                        converter.toAsyncUploadCommand(request)
                )
        );
        return ResultUtils.ok(response);
    }

    /**
     * 获取异步任务状态
     */
    @Override
    public ResultModel<AsyncTaskStatusResponse> getAsyncTaskStatus(String taskId) {
        log.info("获取异步任务状态: {}", taskId);

        AsyncTaskStatusResponse response = converter.toAsyncTaskStatusResponse(
                fileUploadService.getAsyncTaskStatus(
                        converter.toAsyncTaskQuery(taskId)
                )
        );
        return ResultUtils.ok(response);
    }

    /**
     * 取消异步任务
     */
    @Override
    public ResultModel<String> cancelAsyncTask(String taskId) {
        log.info("取消异步任务: {}", taskId);

        fileUploadService.cancelAsyncTask(
                converter.toCancelAsyncTaskCommand(taskId)
        );
        return ResultUtils.ok();
    }

    /**
     * 获取异步任务列表
     */
    @Override
    public PageResultModel<AsyncTaskStatusResponse> getAsyncTasks(Integer taskStatus, Integer page, Integer size) {
        log.info("获取异步任务列表: status={}, page={}, size={}", taskStatus, page, size);

        PageResult<AsyncTaskResult> result = fileUploadService.getAsyncTasks(
                converter.toAsyncTaskListQuery(taskStatus, page, size)
        );
        return PageResultUtils.ok(result.getTotal(), converter.toAsyncTaskStatusResponseList(result.getRecords()));
    }

    // ==================== 7. 文件上传回调 ====================

    /**
     * 上传回调接口（由服务端调用）
     */
    @Override
    public ResultModel<String> uploadCallback(@Valid UploadCallbackRequest request) {
        log.info("上传回调: taskId={}, status={}", request.getTaskId(), request.getStatus());

        fileUploadService.handleUploadCallback(
                converter.toUploadCallbackCommand(request)
        );
        return ResultUtils.ok();
    }

    // ==================== 8. 文件上传状态管理 ====================

    /**
     * 查询文件上传状态
     */
    @Override
    public ResultModel<UploadStatusResponse> getUploadStatus(String fileId) {
        log.info("获取上传状态: {}", fileId);

        UploadStatusResponse response = converter.toUploadStatusResponse(
                fileUploadService.getUploadStatus(
                        converter.toUploadStatusQuery(fileId)
                )
        );
        return ResultUtils.ok(response);
    }

    /**
     * 获取用户上传的文件列表
     */
    @Override
    public PageResultModel<UploadFileListResponse> getUserFiles(
            Integer page, Integer size, Integer fileStatus,
            String storageType, String fileName, String startTime, String endTime) {

        log.info("获取用户文件列表: page={}, size={}", page, size);

        PageResult<UploadFileResult> result = fileUploadService.getUserFiles(
                converter.toUploadFileListQuery(
                        page, size, fileStatus, storageType, fileName, startTime, endTime
                )
        );
        return PageResultUtils.ok(result.getTotal(), converter.toUploadFileListResponseList(result.getRecords()));
    }

    /**
     * 获取文件上传统计信息
     */
    @Override
    public ResultModel<UploadStatisticsResponse> getUploadStatistics(String period) {
        log.info("获取上传统计: period={}", period);

        UploadStatisticsResponse response = converter.toUploadStatisticsResponse(
                fileUploadService.getUploadStatistics(
                        converter.toUploadStatisticsQuery(period)
                )
        );
        return ResultUtils.ok(response);
    }

    // ==================== 9. 大文件专用上传 ====================

    /**
     * 大文件上传 - 支持自动切换策略
     * 根据文件大小自动选择: 小文件普通上传，大文件分片上传
     */
    @Override
    public ResultModel<UploadResponse> autoUpload(
            MultipartFile file,
            String storageType,
            String businessId,
            String businessType,
            Long chunkThreshold,
            String tags,
            String description,
            Boolean isPublic
    ) {

        log.info("自动选择策略上传: {}, 大小: {}, 阈值: {}", file.getOriginalFilename(), file.getSize(), chunkThreshold);

        UploadResponse response = converter.toUploadResponse(
                fileUploadService.autoUpload(
                        converter.toAutoUploadCommand(
                                file, storageType, businessId, businessType,
                                chunkThreshold, tags, description, isPublic
                        )
                )
        );
        return ResultUtils.ok(response);
    }

    /**
     * 可恢复的上传 - 支持服务端自动恢复
     */
    @Override
    public ResultModel<UploadResponse> resumableUpload(
            MultipartFile file,
            String fileId,
            String storageType,
            String businessId,
            String businessType,
            String tags,
            String description
    ) {

        log.info("可恢复上传: {}, fileId={}", file.getOriginalFilename(), fileId);

        UploadResponse response = converter.toUploadResponse(
                fileUploadService.resumableUpload(
                        converter.toResumableUploadCommand(
                                file, fileId, storageType, businessId, businessType, tags, description
                        )
                )
        );
        return ResultUtils.ok(response);
    }

    // ==================== 10. Base64上传 ====================

    @Override
    public ResultModel<UploadResponse> base64Upload(@Valid Base64UploadRequest request) {
        log.info("Base64上传: {}", request.getFileName());

        UploadResponse response = converter.toUploadResponse(
                fileUploadService.base64Upload(
                        converter.toBase64UploadCommand(request)
                )
        );
        return ResultUtils.ok(response);
    }

    // ==================== 11. URL拉取上传 ====================

    @Override
    public ResultModel<UploadResponse> fetchFromUrl(@Valid UrlFetchRequest request) {
        log.info("URL拉取上传: {}", request.getFileUrl());

        UploadResponse response = converter.toUploadResponse(
                fileUploadService.fetchFromUrl(
                        converter.toUrlFetchCommand(request)
                )
        );
        return ResultUtils.ok(response);
    }

    @Override
    public ResultModel<AsyncUploadResponse> asyncFetchFromUrl(@Valid UrlFetchRequest request) {
        log.info("异步URL拉取: {}", request.getFileUrl());

        AsyncUploadResponse response = converter.toAsyncUploadResponse(
                fileUploadService.asyncFetchFromUrl(
                        converter.toUrlFetchCommand(request)
                )
        );
        return ResultUtils.ok(response);
    }

    // ==================== 12. 混合云上传 ====================

    @Override
    public ResultModel<HybridUploadResponse> hybridUpload(
            MultipartFile file,
            List<String> preferredStorages,
            String storageType,
            String businessId,
            String businessType,
            String strategy,
            String tags,
            String description
    ) {

        log.info("混合云上传: {}, 策略: {}", file.getOriginalFilename(), strategy);

        HybridUploadResponse response = converter.toHybridUploadResponse(
                fileUploadService.hybridUpload(
                        converter.toHybridUploadCommand(
                                file, preferredStorages, storageType, businessId, businessType,
                                strategy, tags, description
                        )
                )
        );
        return ResultUtils.ok(response);
    }

}