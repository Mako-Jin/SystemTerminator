package com.yaocode.sts.components.file.runtime.service;

import com.yaocode.sts.common.basic.model.PageResult;
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
import com.yaocode.sts.components.file.runtime.model.result.HybridUploadResult;
import com.yaocode.sts.components.file.runtime.model.result.MultipartInitResult;
import com.yaocode.sts.components.file.runtime.model.result.MultipartSessionResult;
import com.yaocode.sts.components.file.runtime.model.result.ResumeInfoResult;
import com.yaocode.sts.components.file.runtime.model.result.UploadFileResult;
import com.yaocode.sts.components.file.runtime.model.result.UploadPartResult;
import com.yaocode.sts.components.file.runtime.model.result.UploadProgressResult;
import com.yaocode.sts.components.file.runtime.model.result.UploadResult;
import com.yaocode.sts.components.file.runtime.model.result.UploadStatisticsResult;
import com.yaocode.sts.components.file.runtime.model.result.UploadStatusResult;
import java.util.List;

/**
 * 文件上传服务接口
 * <p>
 * 使用 CQRS 模式，命令和查询分离
 * </p>
 *
 * @author yaocode
 * @since 1.0.0
 */
public interface FileUploadService {

    // ==================== 1. 普通上传（命令） ====================

    /**
     * 上传单个文件
     */
    UploadResult upload(UploadFileCommand command);

//    /**
//     * 批量上传文件
//     */
//    List<UploadResult> uploadBatch(UploadBatchCommand command);
//
//    // ==================== 2. 分片上传（命令 + 查询） ====================
//
//    /**
//     * 初始化分片上传
//     */
//    MultipartInitResult initMultipartUpload(InitMultipartCommand command);
//
//    /**
//     * 上传分片
//     */
//    UploadPartResult uploadPart(UploadPartCommand command);
//
//    /**
//     * 完成分片上传
//     */
//    UploadResult completeMultipartUpload(CompleteMultipartCommand command);
//
//    /**
//     * 取消分片上传
//     */
//    void cancelMultipartUpload(CancelMultipartCommand command);
//
//    /**
//     * 获取分片上传进度（查询）
//     */
//    UploadProgressResult getMultipartProgress(UploadProgressQuery query);
//
//    /**
//     * 获取分片上传会话列表（查询）
//     */
//    PageResult<MultipartSessionResult> getMultipartSessions(MultipartSessionQuery query);
//
//    // ==================== 3. 断点续传（命令 + 查询） ====================
//
//    /**
//     * 获取断点续传信息（查询）
//     */
//    ResumeInfoResult getResumeInfo(ResumeInfoQuery query);
//
//    /**
//     * 恢复上传（命令）
//     */
//    UploadResult restoreUpload(ResumeUploadCommand command);
//
//    /**
//     * 暂停上传（命令）
//     */
//    void pauseUpload(PauseUploadCommand command);
//
//    // ==================== 4. 流式直传（命令） ====================
//
//    /**
//     * 流式上传
//     */
//    UploadResult streamUpload(StreamUploadCommand command);
//
//    /**
//     * 分块流式上传
//     */
//    UploadResult chunkedStreamUpload(StreamUploadCommand command);

    // ==================== 5. 秒传（命令 + 查询） ====================

    /**
     * 检查文件是否存在（查询）
     */
    FileExistenceResult checkFileExists(FileExistenceQuery query);

//    /**
//     * 秒传（命令）
//     */
//    UploadResult fastUpload(FastUploadCommand command);
//
//    // ==================== 6. 异步上传（命令 + 查询） ====================
//
//    /**
//     * 异步上传（命令）
//     */
//    AsyncUploadResult asyncUpload(AsyncUploadCommand command);
//
//    /**
//     * 获取异步任务状态（查询）
//     */
//    AsyncTaskResult getAsyncTaskStatus(AsyncTaskQuery query);
//
//    /**
//     * 取消异步任务（命令）
//     */
//    void cancelAsyncTask(CancelAsyncTaskCommand command);
//
//    /**
//     * 获取异步任务列表（查询）
//     */
//    PageResult<AsyncTaskResult> getAsyncTasks(AsyncTaskListQuery query);
//
//    /**
//     * 处理上传回调（命令）
//     */
//    void handleUploadCallback(UploadCallbackCommand command);
//
//    // ==================== 7. 上传状态管理（查询） ====================
//
//    /**
//     * 获取上传状态
//     */
//    UploadStatusResult getUploadStatus(UploadStatusQuery query);
//
//    /**
//     * 获取用户文件列表
//     */
//    PageResult<UploadFileResult> getUserFiles(UploadFileListQuery query);
//
//    /**
//     * 获取上传统计
//     */
//    UploadStatisticsResult getUploadStatistics(UploadStatisticsQuery query);
//
//    // ==================== 8. 大文件上传（命令） ====================
//
//    /**
//     * 自动选择策略上传
//     */
//    UploadResult autoUpload(AutoUploadCommand command);
//
//    /**
//     * 可恢复上传
//     */
//    UploadResult resumableUpload(ResumableUploadCommand command);
//
//    // ==================== 9. Base64上传（命令） ====================
//
//    /**
//     * Base64上传
//     */
//    UploadResult base64Upload(Base64UploadCommand command);
//
//    // ==================== 10. URL拉取上传（命令） ====================
//
//    /**
//     * 从URL拉取文件
//     */
//    UploadResult fetchFromUrl(UrlFetchCommand command);
//
//    /**
//     * 异步从URL拉取文件
//     */
//    AsyncUploadResult asyncFetchFromUrl(UrlFetchCommand command);
//
//    // ==================== 11. 混合云上传（命令） ====================
//
//    /**
//     * 混合云上传
//     */
//    HybridUploadResult hybridUpload(HybridUploadCommand command);
}