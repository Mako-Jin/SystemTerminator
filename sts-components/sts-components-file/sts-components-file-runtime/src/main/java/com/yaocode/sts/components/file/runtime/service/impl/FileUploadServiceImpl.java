package com.yaocode.sts.components.file.runtime.service.impl;

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
import com.yaocode.sts.components.file.runtime.service.FileUploadService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileUploadServiceImpl implements FileUploadService {
    @Override
    public UploadResult upload(UploadFileCommand command) {
        return null;
    }

    @Override
    public List<UploadResult> uploadBatch(UploadBatchCommand command) {
        return List.of();
    }

    @Override
    public MultipartInitResult initMultipartUpload(InitMultipartCommand command) {
        return null;
    }

    @Override
    public UploadPartResult uploadPart(UploadPartCommand command) {
        return null;
    }

    @Override
    public UploadResult completeMultipartUpload(CompleteMultipartCommand command) {
        return null;
    }

    @Override
    public void cancelMultipartUpload(CancelMultipartCommand command) {

    }

    @Override
    public UploadProgressResult getMultipartProgress(UploadProgressQuery query) {
        return null;
    }

    @Override
    public PageResult<MultipartSessionResult> getMultipartSessions(MultipartSessionQuery query) {
        return null;
    }

    @Override
    public ResumeInfoResult getResumeInfo(ResumeInfoQuery query) {
        return null;
    }

    @Override
    public UploadResult restoreUpload(ResumeUploadCommand command) {
        return null;
    }

    @Override
    public void pauseUpload(PauseUploadCommand command) {

    }

    @Override
    public UploadResult streamUpload(StreamUploadCommand command) {
        return null;
    }

    @Override
    public UploadResult chunkedStreamUpload(StreamUploadCommand command) {
        return null;
    }

    @Override
    public FileExistenceResult checkFileExists(FileExistenceQuery query) {
        return null;
    }

    @Override
    public UploadResult fastUpload(FastUploadCommand command) {
        return null;
    }

    @Override
    public AsyncUploadResult asyncUpload(AsyncUploadCommand command) {
        return null;
    }

    @Override
    public AsyncTaskResult getAsyncTaskStatus(AsyncTaskQuery query) {
        return null;
    }

    @Override
    public void cancelAsyncTask(CancelAsyncTaskCommand command) {

    }

    @Override
    public PageResult<AsyncTaskResult> getAsyncTasks(AsyncTaskListQuery query) {
        return null;
    }

    @Override
    public void handleUploadCallback(UploadCallbackCommand command) {

    }

    @Override
    public UploadStatusResult getUploadStatus(UploadStatusQuery query) {
        return null;
    }

    @Override
    public PageResult<UploadFileResult> getUserFiles(UploadFileListQuery query) {
        return null;
    }

    @Override
    public UploadStatisticsResult getUploadStatistics(UploadStatisticsQuery query) {
        return null;
    }

    @Override
    public UploadResult autoUpload(AutoUploadCommand command) {
        return null;
    }

    @Override
    public UploadResult resumableUpload(ResumableUploadCommand command) {
        return null;
    }

    @Override
    public UploadResult base64Upload(Base64UploadCommand command) {
        return null;
    }

    @Override
    public UploadResult fetchFromUrl(UrlFetchCommand command) {
        return null;
    }

    @Override
    public AsyncUploadResult asyncFetchFromUrl(UrlFetchCommand command) {
        return null;
    }

    @Override
    public HybridUploadResult hybridUpload(HybridUploadCommand command) {
        return null;
    }
}
