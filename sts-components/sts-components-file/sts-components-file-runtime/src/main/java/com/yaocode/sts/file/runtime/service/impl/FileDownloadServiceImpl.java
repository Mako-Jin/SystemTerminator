package com.yaocode.sts.file.runtime.service.impl;

import com.yaocode.sts.common.basic.model.PageResult;
import com.yaocode.sts.file.runtime.model.command.AsyncBatchDownloadCommand;
import com.yaocode.sts.file.runtime.model.command.BatchDownloadCommand;
import com.yaocode.sts.file.runtime.model.command.DownloadTokenCommand;
import com.yaocode.sts.file.runtime.model.command.PresignedUrlCommand;
import com.yaocode.sts.file.runtime.model.query.BatchDownloadResultQuery;
import com.yaocode.sts.file.runtime.model.query.BatchDownloadTaskQuery;
import com.yaocode.sts.file.runtime.model.query.CrossOriginDownloadQuery;
import com.yaocode.sts.file.runtime.model.query.DirectDownloadQuery;
import com.yaocode.sts.file.runtime.model.query.DownloadHistoryQuery;
import com.yaocode.sts.file.runtime.model.query.DownloadRankingQuery;
import com.yaocode.sts.file.runtime.model.query.DownloadStatisticsQuery;
import com.yaocode.sts.file.runtime.model.query.EncryptedDownloadQuery;
import com.yaocode.sts.file.runtime.model.query.FileResourceQuery;
import com.yaocode.sts.file.runtime.model.query.FileVersionResourceQuery;
import com.yaocode.sts.file.runtime.model.query.MediaInfoQuery;
import com.yaocode.sts.file.runtime.model.query.PdfPageQuery;
import com.yaocode.sts.file.runtime.model.query.PreviewQuery;
import com.yaocode.sts.file.runtime.model.query.RangeDownloadQuery;
import com.yaocode.sts.file.runtime.model.query.StreamDownloadQuery;
import com.yaocode.sts.file.runtime.model.query.ThumbnailQuery;
import com.yaocode.sts.file.runtime.model.query.TokenDownloadQuery;
import com.yaocode.sts.file.runtime.model.result.BatchDownloadTaskResult;
import com.yaocode.sts.file.runtime.model.result.CrossOriginDownloadInfoResult;
import com.yaocode.sts.file.runtime.model.result.DownloadRecordResult;
import com.yaocode.sts.file.runtime.model.result.DownloadTokenResult;
import com.yaocode.sts.file.runtime.model.result.FileDownloadRankResult;
import com.yaocode.sts.file.runtime.model.result.FileDownloadStatisticsResult;
import com.yaocode.sts.file.runtime.model.result.MediaInfoResult;
import com.yaocode.sts.file.runtime.model.result.RangeDownloadResult;
import com.yaocode.sts.file.runtime.service.FileDownloadService;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.util.List;

@Service
public class FileDownloadServiceImpl implements FileDownloadService {
    @Override
    public Resource getFileResource(FileResourceQuery query) {
        return null;
    }

    @Override
    public Resource getFileVersionResource(FileVersionResourceQuery query) {
        return null;
    }

    @Override
    public void downloadStream(StreamDownloadQuery query, OutputStream outputStream) {

    }

    @Override
    public RangeDownloadResult downloadRange(RangeDownloadQuery query, OutputStream outputStream) {
        return null;
    }

    @Override
    public void previewFile(PreviewQuery query, OutputStream outputStream) {

    }

    @Override
    public void getThumbnail(ThumbnailQuery query, OutputStream outputStream) {

    }

    @Override
    public MediaInfoResult getMediaInfo(MediaInfoQuery query) {
        return null;
    }

    @Override
    public void getPdfPage(PdfPageQuery query, OutputStream outputStream) {

    }

    @Override
    public void batchDownload(BatchDownloadCommand command, OutputStream outputStream) {

    }

    @Override
    public String asyncBatchDownload(AsyncBatchDownloadCommand command) {
        return "";
    }

    @Override
    public BatchDownloadTaskResult getBatchDownloadTask(BatchDownloadTaskQuery query) {
        return null;
    }

    @Override
    public void downloadBatchResult(BatchDownloadResultQuery query, OutputStream outputStream) {

    }

    @Override
    public DownloadTokenResult getDownloadToken(DownloadTokenCommand command) {
        return null;
    }

    @Override
    public void downloadWithToken(TokenDownloadQuery query, OutputStream outputStream) {

    }

    @Override
    public void downloadEncrypted(EncryptedDownloadQuery query, OutputStream outputStream) {

    }

    @Override
    public PageResult<DownloadRecordResult> getDownloadHistory(DownloadHistoryQuery query) {
        return null;
    }

    @Override
    public FileDownloadStatisticsResult getDownloadStatistics(DownloadStatisticsQuery query) {
        return null;
    }

    @Override
    public List<FileDownloadRankResult> getDownloadRanking(DownloadRankingQuery query) {
        return List.of();
    }

    @Override
    public CrossOriginDownloadInfoResult getCrossOriginInfo(CrossOriginDownloadQuery query) {
        return null;
    }

    @Override
    public String generatePresignedUrl(PresignedUrlCommand command) {
        return "";
    }

    @Override
    public void downloadDirect(DirectDownloadQuery query, OutputStream outputStream) {

    }
}
