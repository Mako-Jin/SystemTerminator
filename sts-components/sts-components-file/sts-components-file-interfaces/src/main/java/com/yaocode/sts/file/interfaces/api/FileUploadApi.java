package com.yaocode.sts.file.interfaces.api;

import com.yaocode.sts.common.web.model.PageResultModel;
import com.yaocode.sts.common.web.model.ResultModel;
import com.yaocode.sts.file.interfaces.model.request.AsyncUploadRequest;
import com.yaocode.sts.file.interfaces.model.request.Base64UploadRequest;
import com.yaocode.sts.file.interfaces.model.request.FastUploadRequest;
import com.yaocode.sts.file.interfaces.model.request.MultipartInitRequest;
import com.yaocode.sts.file.interfaces.model.request.ResumeUploadRequest;
import com.yaocode.sts.file.interfaces.model.request.CancelMultipartRequest;
import com.yaocode.sts.file.interfaces.model.request.CompleteMultipartRequest;
import com.yaocode.sts.file.interfaces.model.request.UploadCallbackRequest;
import com.yaocode.sts.file.interfaces.model.request.UrlFetchRequest;
import com.yaocode.sts.file.interfaces.model.response.AsyncTaskStatusResponse;
import com.yaocode.sts.file.interfaces.model.response.AsyncUploadResponse;
import com.yaocode.sts.file.interfaces.model.response.FileExistenceResponse;
import com.yaocode.sts.file.interfaces.model.response.HybridUploadResponse;
import com.yaocode.sts.file.interfaces.model.response.MultipartInitResponse;
import com.yaocode.sts.file.interfaces.model.response.MultipartSessionResponse;
import com.yaocode.sts.file.interfaces.model.response.ResumeInfoResponse;
import com.yaocode.sts.file.interfaces.model.response.UploadFileListResponse;
import com.yaocode.sts.file.interfaces.model.response.UploadPartResponse;
import com.yaocode.sts.file.interfaces.model.response.UploadProgressResponse;
import com.yaocode.sts.file.interfaces.model.response.UploadResponse;
import com.yaocode.sts.file.interfaces.model.response.UploadStatisticsResponse;
import com.yaocode.sts.file.interfaces.model.response.UploadStatusResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 文件上传API接口
 * <p>
 * 提供多种文件上传方式，满足不同场景需求：
 * <ul>
 *   <li>普通上传 - 适用于小文件的直接上传</li>
 *   <li>批量上传 - 一次上传多个文件</li>
 *   <li>分片上传 - 适用于大文件，将文件分成多个分片上传</li>
 *   <li>断点续传 - 支持暂停后恢复上传</li>
 *   <li>流式直传 - 支持从InputStream直接上传，不产生临时文件</li>
 *   <li>秒传 - 通过MD5校验实现快速上传</li>
 *   <li>异步上传 - 异步处理文件上传任务</li>
 *   <li>大文件自动上传 - 根据文件大小自动选择上传策略</li>
 *   <li>Base64上传 - 适用于小文件和前端预览</li>
 *   <li>URL拉取上传 - 从远程URL拉取文件</li>
 *   <li>混合云上传 - 自动选择最优存储</li>
 * </ul>
 * </p>
 * <p>
 * <b>租户隔离：</b>所有上传操作会自动绑定当前租户ID，无需显式传入
 * </p>
 *
 * @author yaocode
 * @since 1.0.0
 */
@RequestMapping("/files")
public interface FileUploadApi {

    // ==================== 1. 普通上传 ====================

    /**
     * 普通文件上传（单文件）
     * <p>
     * 适用于小于100MB的文件上传
     * </p>
     *
     * @param file               上传的文件（必填）
     * @param storageType        存储类型（可选，默认使用系统配置）
     * @param businessId         业务ID（可选，用于业务关联）
     * @param businessType       业务类型（可选，用于业务分类）
     * @param enableDeduplication 是否启用去重（默认true）
     * @param tags               文件标签（可选，逗号分隔）
     * @param description        文件描述（可选）
     * @param isPublic           是否公开（默认false）
     * @param metadata           元数据（可选，自定义属性）
     * @return 上传响应
     */
    @PostMapping(value = "/upload/single", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResultModel<UploadResponse> uploadFile(
            @RequestPart("file") @NotNull(message = "文件不能为空") MultipartFile file,
            @RequestParam(required = false) Integer storageType,
            @RequestParam(required = false) String businessId,
            @RequestParam(required = false) String businessType,
            @RequestParam(defaultValue = "true") Boolean enableDeduplication,
            @RequestParam(required = false) String tags,
            @RequestParam(required = false) String description,
            @RequestParam(defaultValue = "false") Boolean isPublic,
            @RequestParam(required = false) Map<String, String> metadata
    );

    /**
     * 批量文件上传
     *
     * @param files         上传的文件列表（必填，最多50个）
     * @param storageType   存储类型（可选）
     * @param businessId    业务ID（可选）
     * @param businessType  业务类型（可选）
     * @param tags          文件标签（可选）
     * @param description   文件描述（可选）
     * @param isPublic      是否公开（默认false）
     * @return 上传响应列表
     */
//    @PostMapping(value = "/upload/batch", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    ResultModel<List<UploadResponse>> uploadBatchFiles(
//            @RequestPart("files") @Size(max = 50, message = "单次最多上传50个文件") List<MultipartFile> files,
//            @RequestParam(required = false) String storageType,
//            @RequestParam(required = false) String businessId,
//            @RequestParam(required = false) String businessType,
//            @RequestParam(required = false) String tags,
//            @RequestParam(required = false) String description,
//            @RequestParam(defaultValue = "false") Boolean isPublic
//    );
//
//    // ==================== 2. 分片上传 ====================
//
//    /**
//     * 初始化分片上传
//     * <p>
//     * 大文件上传前调用，获取上传ID和分片信息
//     * </p>
//     *
//     * @param request 分片上传初始化请求
//     * @return 初始化响应（包含uploadId和分片信息）
//     */
//    @PostMapping("/multipart/init")
//    ResultModel<MultipartInitResponse> initMultipartUpload(
//            @RequestBody @Valid MultipartInitRequest request
//    );
//
//    /**
//     * 上传分片
//     *
//     * @param uploadId     上传ID（必填）
//     * @param fileId       文件ID（必填）
//     * @param chunkNumber  分片序号（从1开始）
//     * @param totalChunks  总分片数
//     * @param file         分片文件（必填）
//     * @param chunkMd5     分片MD5（可选，用于校验）
//     * @return 分片上传结果
//     */
//    @PostMapping(value = "/multipart/part", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    ResultModel<UploadPartResponse> uploadPart(
//            @RequestParam @NotBlank(message = "上传ID不能为空") String uploadId,
//            @RequestParam @NotBlank(message = "文件ID不能为空") String fileId,
//            @RequestParam @Positive(message = "分片序号必须大于0") Integer chunkNumber,
//            @RequestParam @Positive(message = "总分片数必须大于0") Integer totalChunks,
//            @RequestPart("file") @NotNull(message = "分片文件不能为空") MultipartFile file,
//            @RequestParam(required = false) String chunkMd5
//    );
//
//    /**
//     * 完成分片上传（合并分片）
//     *
//     * @param request 完成分片请求
//     * @return 上传响应
//     */
//    @PostMapping("/multipart/complete")
//    ResultModel<UploadResponse> completeMultipartUpload(
//            @RequestBody @Valid CompleteMultipartRequest request
//    );
//
//    /**
//     * 取消分片上传
//     *
//     * @param request 取消请求
//     * @return 操作结果
//     */
//    @DeleteMapping("/multipart/cancel")
//    ResultModel<String> cancelMultipartUpload(
//            @RequestBody @Valid CancelMultipartRequest request
//    );
//
//    /**
//     * 获取分片上传进度
//     *
//     * @param uploadId 上传ID
//     * @return 上传进度
//     */
//    @GetMapping("/multipart/progress")
//    ResultModel<UploadProgressResponse> getMultipartProgress(
//            @RequestParam @NotBlank(message = "上传ID不能为空") String uploadId
//    );
//
//    /**
//     * 获取所有未完成的分片上传会话
//     *
//     * @param page 页码
//     * @param size 每页数量
//     * @return 会话列表
//     */
//    @GetMapping("/multipart/sessions")
//    PageResultModel<MultipartSessionResponse> getMultipartSessions(
//            @RequestParam(defaultValue = "1") Integer page,
//            @RequestParam(defaultValue = "20") Integer size
//    );
//
//    // ==================== 3. 断点续传 ====================
//
//    /**
//     * 断点续传 - 获取已上传的分片信息
//     *
//     * @param fileId 文件ID
//     * @return 已上传分片信息
//     */
//    @GetMapping("/resume/info")
//    ResultModel<ResumeInfoResponse> getResumeInfo(
//            @RequestParam @NotBlank(message = "文件ID不能为空") String fileId
//    );
//
//    /**
//     * 断点续传 - 恢复上传
//     *
//     * @param request 恢复上传请求
//     * @return 上传响应
//     */
//    @PostMapping("/resume/restore")
//    ResultModel<UploadResponse> restoreUpload(
//            @RequestBody @Valid ResumeUploadRequest request
//    );
//
//    /**
//     * 暂停上传
//     *
//     * @param fileId 文件ID
//     * @return 操作结果
//     */
//    @PostMapping("/pause")
//    ResultModel<String> pauseUpload(
//            @RequestParam @NotBlank(message = "文件ID不能为空") String fileId
//    );
//
//    // ==================== 4. 流式直传 ====================
//
//    /**
//     * 流式直传 - 从InputStream直接上传
//     * <p>
//     * 适用于：实时生成的数据流、大文件流式处理、不产生临时文件
//     * </p>
//     * <p>
//     * 注意：此接口使用 Octet-Stream 媒体类型，客户端需将文件内容作为请求体直接发送
//     * </p>
//     *
//     * @param fileName      文件名（必填）
//     * @param fileSize      文件大小（可选，未指定时从Content-Length获取）
//     * @param storageType   存储类型（可选）
//     * @param businessId    业务ID（可选）
//     * @param businessType  业务类型（可选）
//     * @param tags          标签（可选）
//     * @param description   描述（可选）
//     * @param isPublic      是否公开（默认false）
//     * @param inputStream   输入流（从请求体获取）
//     * @param request       HTTP请求（用于获取Content-Length）
//     * @return 上传响应
//     * @throws IOException IO异常
//     */
//    @PostMapping(value = "/stream", consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
//    ResultModel<UploadResponse> streamUpload(
//            @RequestParam @NotBlank(message = "文件名不能为空") String fileName,
//            @RequestParam(required = false) Long fileSize,
//            @RequestParam(required = false) String storageType,
//            @RequestParam(required = false) String businessId,
//            @RequestParam(required = false) String businessType,
//            @RequestParam(required = false) String tags,
//            @RequestParam(required = false) String description,
//            @RequestParam(defaultValue = "false") Boolean isPublic,
//            @RequestBody InputStream inputStream,
//            HttpServletRequest request
//    ) throws IOException;
//
//    /**
//     * 流式直传 - 支持分块流式上传
//     * <p>
//     * 适用于：不确定文件大小、分块发送的场景
//     * </p>
//     *
//     * @param fileName      文件名（必填）
//     * @param fileSize      文件大小（可选）
//     * @param storageType   存储类型（可选）
//     * @param businessId    业务ID（可选）
//     * @param businessType  业务类型（可选）
//     * @param tags          标签（可选）
//     * @param description   描述（可选）
//     * @param isPublic      是否公开（默认false）
//     * @param inputStream   输入流
//     * @return 上传响应
//     */
//    @PostMapping(value = "/stream/chunked", consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
//    ResultModel<UploadResponse> chunkedStreamUpload(
//            @RequestParam @NotBlank(message = "文件名不能为空") String fileName,
//            @RequestParam(required = false) Long fileSize,
//            @RequestParam(required = false) String storageType,
//            @RequestParam(required = false) String businessId,
//            @RequestParam(required = false) String businessType,
//            @RequestParam(required = false) String tags,
//            @RequestParam(required = false) String description,
//            @RequestParam(defaultValue = "false") Boolean isPublic,
//            @RequestBody InputStream inputStream
//    );
//
//    // ==================== 5. 秒传 ====================
//
//    /**
//     * 秒传 - 检查文件是否已存在
//     *
//     * @param fileMd5     文件MD5
//     * @param fileSize    文件大小
//     * @param storageType 存储类型（可选）
//     * @return 检查响应
//     */
//    @PostMapping("/check")
//    ResultModel<FileExistenceResponse> checkFileExists(
//            @RequestParam @NotBlank(message = "文件MD5不能为空") String fileMd5,
//            @RequestParam @Positive(message = "文件大小必须大于0") Long fileSize,
//            @RequestParam(required = false) String storageType
//    );
//
//    /**
//     * 秒传 - 直接获取已存在文件的信息
//     *
//     * @param request 秒传请求
//     * @return 上传响应
//     */
//    @PostMapping("/fast-upload")
//    ResultModel<UploadResponse> fastUpload(
//            @RequestBody @Valid FastUploadRequest request
//    );
//
//    // ==================== 6. 异步上传 ====================
//
//    /**
//     * 异步上传 - 提交异步上传任务
//     * <p>
//     * 适用于：需要后台处理、不想等待上传完成的场景
//     * </p>
//     *
//     * @param request 异步上传请求
//     * @return 任务ID
//     */
//    @PostMapping("/async")
//    ResultModel<AsyncUploadResponse> asyncUpload(
//            @RequestBody @Valid AsyncUploadRequest request
//    );
//
//    /**
//     * 获取异步任务状态
//     *
//     * @param taskId 任务ID
//     * @return 任务状态
//     */
//    @GetMapping("/async/task/{taskId}")
//    ResultModel<AsyncTaskStatusResponse> getAsyncTaskStatus(
//            @PathVariable @NotBlank(message = "任务ID不能为空") String taskId
//    );
//
//    /**
//     * 取消异步任务
//     *
//     * @param taskId 任务ID
//     * @return 操作结果
//     */
//    @DeleteMapping("/async/task/{taskId}")
//    ResultModel<String> cancelAsyncTask(
//            @PathVariable @NotBlank(message = "任务ID不能为空") String taskId
//    );
//
//    /**
//     * 获取异步任务列表
//     *
//     * @param taskStatus 任务状态（可选）
//     * @param page       页码（默认1）
//     * @param size       每页数量（默认20）
//     * @return 任务列表
//     */
//    @GetMapping("/async/tasks")
//    PageResultModel<AsyncTaskStatusResponse> getAsyncTasks(
//            @RequestParam(required = false) Integer taskStatus,
//            @RequestParam(defaultValue = "1") Integer page,
//            @RequestParam(defaultValue = "20") Integer size
//    );
//
//    // ==================== 7. 文件上传回调 ====================
//
//    /**
//     * 上传回调接口（由服务端调用）
//     * <p>
//     * 用于异步上传完成后的回调通知
//     * </p>
//     *
//     * @param request 回调请求
//     * @return 操作结果
//     */
//    @PostMapping("/callback")
//    ResultModel<String> uploadCallback(
//            @RequestBody @Valid UploadCallbackRequest request
//    );
//
//    // ==================== 8. 文件上传状态管理 ====================
//
//    /**
//     * 查询文件上传状态
//     *
//     * @param fileId 文件ID
//     * @return 上传状态
//     */
//    @GetMapping("/status/{fileId}")
//    ResultModel<UploadStatusResponse> getUploadStatus(
//            @PathVariable @NotBlank(message = "文件ID不能为空") String fileId
//    );
//
//    /**
//     * 获取用户上传的文件列表
//     *
//     * @param page         页码（默认1）
//     * @param size         每页数量（默认20）
//     * @param fileStatus   文件状态（可选）
//     * @param storageType  存储类型（可选）
//     * @param fileName     文件名（可选，模糊匹配）
//     * @param startTime    开始时间（可选）
//     * @param endTime      结束时间（可选）
//     * @return 文件列表
//     */
//    @GetMapping("/list")
//    PageResultModel<UploadFileListResponse> getUserFiles(
//            @RequestParam(defaultValue = "1") Integer page,
//            @RequestParam(defaultValue = "20") Integer size,
//            @RequestParam(required = false) Integer fileStatus,
//            @RequestParam(required = false) String storageType,
//            @RequestParam(required = false) String fileName,
//            @RequestParam(required = false) String startTime,
//            @RequestParam(required = false) String endTime
//    );
//
//    /**
//     * 获取文件上传统计信息
//     *
//     * @param period 统计周期（day/week/month/year）
//     * @return 统计信息
//     */
//    @GetMapping("/statistics")
//    ResultModel<UploadStatisticsResponse> getUploadStatistics(
//            @RequestParam(defaultValue = "week") String period
//    );
//
//    // ==================== 9. 大文件专用上传 ====================
//
//    /**
//     * 大文件上传 - 支持自动切换策略
//     * <p>
//     * 根据文件大小自动选择：小文件普通上传，大文件分片上传
//     * </p>
//     *
//     * @param file            文件（必填）
//     * @param storageType     存储类型（可选）
//     * @param businessId      业务ID（可选）
//     * @param businessType    业务类型（可选）
//     * @param chunkThreshold  分片阈值（默认10MB）
//     * @param tags            标签（可选）
//     * @param description     描述（可选）
//     * @param isPublic        是否公开（默认false）
//     * @return 上传响应
//     */
//    @PostMapping(value = "/upload/auto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    ResultModel<UploadResponse> autoUpload(
//            @RequestPart("file") @NotNull(message = "文件不能为空") MultipartFile file,
//            @RequestParam(required = false) String storageType,
//            @RequestParam(required = false) String businessId,
//            @RequestParam(required = false) String businessType,
//            @RequestParam(defaultValue = "10485760") Long chunkThreshold,
//            @RequestParam(required = false) String tags,
//            @RequestParam(required = false) String description,
//            @RequestParam(defaultValue = "false") Boolean isPublic
//    );
//
//    /**
//     * 可恢复的上传 - 支持服务端自动恢复
//     * <p>
//     * 支持上传过程中断后自动恢复
//     * </p>
//     *
//     * @param file         文件（必填）
//     * @param fileId       文件ID（可选，用于断点续传）
//     * @param storageType  存储类型（可选）
//     * @param businessId   业务ID（可选）
//     * @param businessType 业务类型（可选）
//     * @param tags         标签（可选）
//     * @param description  描述（可选）
//     * @return 上传响应
//     */
//    @PostMapping(value = "/upload/resumable", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    ResultModel<UploadResponse> resumableUpload(
//            @RequestPart("file") @NotNull(message = "文件不能为空") MultipartFile file,
//            @RequestParam(required = false) String fileId,
//            @RequestParam(required = false) String storageType,
//            @RequestParam(required = false) String businessId,
//            @RequestParam(required = false) String businessType,
//            @RequestParam(required = false) String tags,
//            @RequestParam(required = false) String description
//    );
//
//    // ==================== 10. Base64上传 ====================
//
//    /**
//     * Base64编码上传
//     * <p>
//     * 适用于：前端图片预览、小文件嵌入JSON、移动端上传
//     * </p>
//     *
//     * @param request Base64上传请求
//     * @return 上传响应
//     */
//    @PostMapping("/upload/base64")
//    ResultModel<UploadResponse> base64Upload(
//            @RequestBody @Valid Base64UploadRequest request
//    );
//
//    // ==================== 11. URL拉取上传 ====================
//
//    /**
//     * 从远程URL拉取文件并上传
//     * <p>
//     * 适用于：跨云存储迁移、镜像站同步、远程文件转存
//     * </p>
//     *
//     * @param request URL拉取请求
//     * @return 上传响应
//     */
//    @PostMapping("/fetch")
//    ResultModel<UploadResponse> fetchFromUrl(
//            @RequestBody @Valid UrlFetchRequest request
//    );
//
//    /**
//     * 异步URL拉取（支持超时文件）
//     *
//     * @param request URL拉取请求
//     * @return 任务ID
//     */
//    @PostMapping("/fetch/async")
//    ResultModel<AsyncUploadResponse> asyncFetchFromUrl(
//            @RequestBody @Valid UrlFetchRequest request
//    );
//
//    // ==================== 12. 混合云上传 ====================
//
//    /**
//     * 混合云存储上传（自动选择最优存储）
//     * <p>
//     * 适用于：多云部署、容灾备份、成本优化
//     * 根据文件类型、大小、优先级自动选择最优存储后端
//     * </p>
//     *
//     * @param file               上传的文件（必填）
//     * @param preferredStorages  优先存储列表（可选）
//     * @param storageType        存储类型（可选，指定则使用指定存储）
//     * @param businessId         业务ID（可选）
//     * @param businessType       业务类型（可选）
//     * @param strategy           选择策略（可选：auto/priority/cost/performance）
//     * @param tags               标签（可选）
//     * @param description        描述（可选）
//     * @return 上传响应（包含选择的存储信息）
//     */
//    @PostMapping(value = "/upload/hybrid", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    ResultModel<HybridUploadResponse> hybridUpload(
//            @RequestPart("file") @NotNull(message = "文件不能为空") MultipartFile file,
//            @RequestParam(required = false) List<String> preferredStorages,
//            @RequestParam(required = false) String storageType,
//            @RequestParam(required = false) String businessId,
//            @RequestParam(required = false) String businessType,
//            @RequestParam(defaultValue = "auto") String strategy,
//            @RequestParam(required = false) String tags,
//            @RequestParam(required = false) String description
//    );
}