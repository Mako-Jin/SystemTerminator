package com.yaocode.sts.components.file.web.converter;

import com.yaocode.sts.components.file.interfaces.model.request.BatchDownloadRequest;
import com.yaocode.sts.components.file.interfaces.model.request.FileDownloadItemRequest;
import com.yaocode.sts.components.file.interfaces.model.response.BatchDownloadTaskResponse;
import com.yaocode.sts.components.file.interfaces.model.response.CrossOriginDownloadInfoResponse;
import com.yaocode.sts.components.file.interfaces.model.response.DownloadRecordResponse;
import com.yaocode.sts.components.file.interfaces.model.response.DownloadTokenResponse;
import com.yaocode.sts.components.file.interfaces.model.response.FileDownloadRankResponse;
import com.yaocode.sts.components.file.interfaces.model.response.FileDownloadStatisticsResponse;
import com.yaocode.sts.components.file.interfaces.model.response.MediaInfoResponse;
import com.yaocode.sts.components.file.interfaces.model.response.MediaStreamResponse;
import com.yaocode.sts.components.file.interfaces.model.response.TrendDataResponse;
import com.yaocode.sts.components.file.runtime.model.command.AsyncBatchDownloadCommand;
import com.yaocode.sts.components.file.runtime.model.command.BatchDownloadCommand;
import com.yaocode.sts.components.file.runtime.model.command.DownloadTokenCommand;
import com.yaocode.sts.components.file.runtime.model.command.PresignedUrlCommand;
import com.yaocode.sts.components.file.runtime.model.dto.FileDownloadItemDto;
import com.yaocode.sts.components.file.runtime.model.query.BatchDownloadResultQuery;
import com.yaocode.sts.components.file.runtime.model.query.BatchDownloadTaskQuery;
import com.yaocode.sts.components.file.runtime.model.query.CrossOriginDownloadQuery;
import com.yaocode.sts.components.file.runtime.model.query.DirectDownloadQuery;
import com.yaocode.sts.components.file.runtime.model.query.DownloadHistoryQuery;
import com.yaocode.sts.components.file.runtime.model.query.DownloadRankingQuery;
import com.yaocode.sts.components.file.runtime.model.query.DownloadStatisticsQuery;
import com.yaocode.sts.components.file.runtime.model.query.EncryptedDownloadQuery;
import com.yaocode.sts.components.file.runtime.model.query.FileResourceQuery;
import com.yaocode.sts.components.file.runtime.model.query.FileVersionResourceQuery;
import com.yaocode.sts.components.file.runtime.model.query.MediaInfoQuery;
import com.yaocode.sts.components.file.runtime.model.query.PdfPageQuery;
import com.yaocode.sts.components.file.runtime.model.query.PreviewQuery;
import com.yaocode.sts.components.file.runtime.model.query.RangeDownloadQuery;
import com.yaocode.sts.components.file.runtime.model.query.StreamDownloadQuery;
import com.yaocode.sts.components.file.runtime.model.query.ThumbnailQuery;
import com.yaocode.sts.components.file.runtime.model.query.TokenDownloadQuery;
import com.yaocode.sts.components.file.runtime.model.result.BatchDownloadTaskResult;
import com.yaocode.sts.components.file.runtime.model.result.CrossOriginDownloadInfoResult;
import com.yaocode.sts.components.file.runtime.model.result.DownloadRecordResult;
import com.yaocode.sts.components.file.runtime.model.result.DownloadTokenResult;
import com.yaocode.sts.components.file.runtime.model.result.FileDownloadRankResult;
import com.yaocode.sts.components.file.runtime.model.result.FileDownloadStatisticsResult;
import com.yaocode.sts.components.file.runtime.model.result.MediaInfoResult;
import com.yaocode.sts.components.file.runtime.model.result.MediaStreamResult;
import com.yaocode.sts.components.file.runtime.model.result.TrendDataResult;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文件下载转换器
 *
 * @author yaocode
 * @since 1.0.0
 */
@Component
public class FileDownloadConverter {

    // ==================== 上下文信息获取 ====================

    private String getCurrentTenantId() {
        // TODO: 从 SecurityContext 获取
        return "default";
    }

    private String getCurrentUserId() {
        // TODO: 从 SecurityContext 获取
        return "system";
    }

    private String getCurrentIp() {
        // TODO: 从 RequestContext 获取
        return "127.0.0.1";
    }

    // ==================== Query 转换 ====================

    /**
     * 文件资源查询
     */
    public FileResourceQuery toFileResourceQuery(String fileId, Boolean preview) {
        return FileResourceQuery.builder()
                .fileId(fileId)
                .preview(preview != null ? preview : false)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    /**
     * 文件版本资源查询
     */
    public FileVersionResourceQuery toFileVersionResourceQuery(String fileId, Integer version) {
        return FileVersionResourceQuery.builder()
                .fileId(fileId)
                .version(version)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    /**
     * 流式下载查询
     */
    public StreamDownloadQuery toStreamDownloadQuery(String fileId) {
        return StreamDownloadQuery.builder()
                .fileId(fileId)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    /**
     * 分段下载查询
     */
    public RangeDownloadQuery toRangeDownloadQuery(String fileId, String range) {
        return RangeDownloadQuery.builder()
                .fileId(fileId)
                .range(range)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    /**
     * 预览查询
     */
    public PreviewQuery toPreviewQuery(String fileId) {
        return PreviewQuery.builder()
                .fileId(fileId)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    /**
     * 缩略图查询
     */
    public ThumbnailQuery toThumbnailQuery(String fileId, Integer width, Integer height) {
        return ThumbnailQuery.builder()
                .fileId(fileId)
                .width(width)
                .height(height)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    /**
     * 媒体信息查询
     */
    public MediaInfoQuery toMediaInfoQuery(String fileId) {
        return MediaInfoQuery.builder()
                .fileId(fileId)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    /**
     * PDF页面查询
     */
    public PdfPageQuery toPdfPageQuery(String fileId, Integer page) {
        return PdfPageQuery.builder()
                .fileId(fileId)
                .page(page)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    /**
     * 下载历史查询
     */
    public DownloadHistoryQuery toDownloadHistoryQuery(Integer page, Integer size,
                                                       String startTime, String endTime) {
        return DownloadHistoryQuery.builder()
                .page(page != null ? page : 1)
                .size(size != null ? size : 20)
                .startTime(startTime)
                .endTime(endTime)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    /**
     * 下载统计查询
     */
    public DownloadStatisticsQuery toDownloadStatisticsQuery(String fileId) {
        return DownloadStatisticsQuery.builder()
                .fileId(fileId)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    /**
     * 下载排行榜查询
     */
    public DownloadRankingQuery toDownloadRankingQuery(String period, Integer limit) {
        return DownloadRankingQuery.builder()
                .period(period != null ? period : "week")
                .limit(limit != null ? limit : 10)
                .tenantId(getCurrentTenantId())
                .build();
    }

    /**
     * 跨域下载查询
     */
    public CrossOriginDownloadQuery toCrossOriginDownloadQuery(String fileId, Integer expiry) {
        return CrossOriginDownloadQuery.builder()
                .fileId(fileId)
                .expiry(expiry != null ? expiry : 3600)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    /**
     * Token下载查询
     */
    public TokenDownloadQuery toTokenDownloadQuery(String token) {
        return TokenDownloadQuery.builder()
                .token(token)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    /**
     * 加密下载查询
     */
    public EncryptedDownloadQuery toEncryptedDownloadQuery(String fileId, String encryptionKey) {
        return EncryptedDownloadQuery.builder()
                .fileId(fileId)
                .encryptionKey(encryptionKey)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    /**
     * 直接下载查询
     */
    public DirectDownloadQuery toDirectDownloadQuery(String fileId) {
        return DirectDownloadQuery.builder()
                .fileId(fileId)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    /**
     * 批量下载任务查询
     */
    public BatchDownloadTaskQuery toBatchDownloadTaskQuery(String taskId) {
        return BatchDownloadTaskQuery.builder()
                .taskId(taskId)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    /**
     * 批量下载结果查询
     */
    public BatchDownloadResultQuery toBatchDownloadResultQuery(String taskId) {
        return BatchDownloadResultQuery.builder()
                .taskId(taskId)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    // ==================== Command 转换 ====================

    /**
     * 批量下载命令
     */
    public BatchDownloadCommand toBatchDownloadCommand(List<FileDownloadItemRequest> files, String zipFileName) {
        return BatchDownloadCommand.builder()
                .files(toFileDownloadItems(files))
                .zipFileName(zipFileName)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    public List<FileDownloadItemDto> toFileDownloadItems(List<FileDownloadItemRequest> requests) {
        return requests.stream()
                .map(this::toFileDownloadItem)
                .toList();
    }

    /**
     * 批量下载命令（带结构）
     */
    public BatchDownloadCommand toBatchDownloadCommand(BatchDownloadRequest request) {

        List<FileDownloadItemDto> fileItems = request.getFiles().stream()
                .map(this::toFileDownloadItem)
                .toList();

        return BatchDownloadCommand.builder()
                .files(fileItems)
                .zipFileName(request.getZipFileName())
                .preserveStructure(request.getPreserveStructure() != null ? request.getPreserveStructure() : true)
                .includeMetadata(request.getIncludeMetadata() != null ? request.getIncludeMetadata() : false)
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    /**
     * 异步批量下载命令
     */
    public AsyncBatchDownloadCommand toAsyncBatchDownloadCommand(BatchDownloadRequest request) {

        List<FileDownloadItemDto> fileItems = request.getFiles().stream()
                .map(this::toFileDownloadItem)
                .toList();

        return AsyncBatchDownloadCommand.builder()
                .files(fileItems)
                .zipFileName(request.getZipFileName())
                .preserveStructure(request.getPreserveStructure() != null ? request.getPreserveStructure() : true)
                .includeMetadata(request.getIncludeMetadata() != null ? request.getIncludeMetadata() : false)
                .callbackUrl(request.getCallbackUrl())
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    /**
     * 将 FileDownloadItemRequest 转换为 FileDownloadItemDto
     */
    public FileDownloadItemDto toFileDownloadItem(FileDownloadItemRequest request) {
        if (request == null) return null;
        return FileDownloadItemDto.builder()
                .fileId(request.getFileId())
                .customFileName(request.getCustomFileName())
                .relativePath(request.getRelativePath())
                .downloadVersion(request.getDownloadVersion())
                .versionNumber(request.getVersionNumber())
                .build();
    }

    /**
     * 下载Token命令
     */
    public DownloadTokenCommand toDownloadTokenCommand(String fileId, Integer expireSeconds, Boolean limitIp) {
        return DownloadTokenCommand.builder()
                .fileId(fileId)
                .expireSeconds(expireSeconds != null ? expireSeconds : 300)
                .limitIp(limitIp != null ? limitIp : true)
                .clientIp(getCurrentIp())
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    /**
     * 预签名URL命令
     */
    public PresignedUrlCommand toPresignedUrlCommand(String fileId, Integer expiry, String method) {
        return PresignedUrlCommand.builder()
                .fileId(fileId)
                .expiry(expiry != null ? expiry : 3600)
                .method(method != null ? method : "GET")
                .tenantId(getCurrentTenantId())
                .userId(getCurrentUserId())
                .build();
    }

    // ==================== Result → Response 转换 ====================

    /**
     * 下载Token结果 → 下载Token响应
     */
    public DownloadTokenResponse toDownloadTokenResponse(DownloadTokenResult result) {
        if (result == null) return null;
        return DownloadTokenResponse.builder()
                .token(result.getToken())
                .fileId(result.getFileId())
                .fileName(result.getFileName())
                .fileSize(result.getFileSize())
                .downloadUrl(result.getDownloadUrl())
                .expireSeconds(result.getExpireSeconds())
                .createTime(result.getCreateTime())
                .expireTime(result.getExpireTime())
                .limitIp(result.getLimitIp())
                .allowedIp(result.getAllowedIp())
                .build();
    }

    /**
     * 媒体信息结果 → 媒体信息响应
     */
    public MediaInfoResponse toMediaInfoResponse(MediaInfoResult result) {
        if (result == null) return null;
        return MediaInfoResponse.builder()
                .fileId(result.getFileId())
                .fileName(result.getFileName())
                .mediaType(result.getMediaType())
                .format(result.getFormat())
                .duration(result.getDuration())
                .width(result.getWidth())
                .height(result.getHeight())
                .bitrate(result.getBitrate())
                .codec(result.getCodec())
                .streams(toMediaStreamResponseList(result.getStreams()))
                .metadata(result.getMetadata())
                .build();
    }

    public List<MediaStreamResponse> toMediaStreamResponseList(List<MediaStreamResult> results) {
        if (CollectionUtils.isEmpty(results)) {
            return Collections.emptyList();
        }
        return results.stream()
                .map(this::toMediaStreamResponse)
                .collect(Collectors.toList());
    }

    public MediaStreamResponse toMediaStreamResponse(MediaStreamResult result) {
        if (result == null) return null;
        return MediaStreamResponse.builder()
                .streamType(result.getStreamType())
                .streamCodec(result.getStreamCodec())
                .width(result.getWidth())
                .language(result.getLanguage())
                .bitrate(result.getBitrate())
                .frameRate(result.getFrameRate())
                .build();
    }

    /**
     * 下载记录结果 → 下载记录响应
     */
    public DownloadRecordResponse toDownloadRecordResponse(DownloadRecordResult result) {
        if (result == null) return null;
        return DownloadRecordResponse.builder()
                .fileId(result.getFileId())
                .fileName(result.getFileName())
                .fileSize(result.getFileSize())
                .storageType(result.getStorageType())
                .downloadType(result.getDownloadType())
                .ipAddress(result.getIpAddress())
                .userAgent(result.getUserAgent())
                .downloadTime(result.getDownloadTime())
                .downloadSize(result.getDownloadSize())
                .success(result.getSuccess())
                .createdTime(result.getCreatedTime())
                .build();
    }

    /**
     * 下载记录结果列表 → 下载记录响应列表
     */
    public List<DownloadRecordResponse> toDownloadRecordResponseList(List<DownloadRecordResult> results) {
        if (CollectionUtils.isEmpty(results)) {
            return Collections.emptyList();
        }
        return results.stream()
                .map(this::toDownloadRecordResponse)
                .collect(Collectors.toList());
    }

    /**
     * 文件下载统计结果 → 文件下载统计响应
     */
    public FileDownloadStatisticsResponse toFileDownloadStatisticsResponse(FileDownloadStatisticsResult result) {
        if (result == null) return null;
        return FileDownloadStatisticsResponse.builder()
                .fileId(result.getFileId())
                .fileName(result.getFileName())
                .totalDownloads(result.getTotalDownloads())
                .uniqueUsers(result.getUniqueUsers())
                .todayDownloads(result.getTodayDownloads())
                .weekDownloads(result.getWeekDownloads())
                .monthDownloads(result.getMonthDownloads())
                .totalDownloadSize(result.getTotalDownloadSize())
                .avgDownloadSpeed(result.getAvgDownloadSpeed())
                .downloadTrend(toTrendDataResponseList(result.getDownloadTrend()))
                .downloadByType(result.getDownloadByType())
                .downloadByRegion(result.getDownloadByRegion())
                .build();
    }

    public List<TrendDataResponse> toTrendDataResponseList(List<TrendDataResult> results) {
        if (CollectionUtils.isEmpty(results)) {
            return Collections.emptyList();
        }
        return results.stream()
                .map(this::toTrendDataResponse)
                .collect(Collectors.toList());
    }

    public TrendDataResponse toTrendDataResponse(TrendDataResult result) {
        if (result == null) return null;
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

    /**
     * 文件下载排行榜结果 → 文件下载排行榜响应
     */
    public FileDownloadRankResponse toFileDownloadRankResponse(FileDownloadRankResult result) {
        if (result == null) return null;
        return FileDownloadRankResponse.builder()
                .fileId(result.getFileId())
                .fileName(result.getFileName())
                .fileType(result.getFileType())
                .fileSize(result.getFileSize())
                .downloadCount(result.getDownloadCount())
                .uniqueUsers(result.getUniqueUsers())
                .growthRate(result.getGrowthRate())
                .rank(result.getRank())
                .rankChange(result.getRankChange())
                .build();
    }

    /**
     * 文件下载排行榜结果列表 → 文件下载排行榜响应列表
     */
    public List<FileDownloadRankResponse> toFileDownloadRankResponseList(List<FileDownloadRankResult> results) {
        if (CollectionUtils.isEmpty(results)) {
            return Collections.emptyList();
        }
        return results.stream()
                .map(this::toFileDownloadRankResponse)
                .collect(Collectors.toList());
    }

    /**
     * 跨域下载信息结果 → 跨域下载信息响应
     */
    public CrossOriginDownloadInfoResponse toCrossOriginDownloadInfoResponse(CrossOriginDownloadInfoResult result) {
        if (result == null) return null;
        return CrossOriginDownloadInfoResponse.builder()
                .fileId(result.getFileId())
                .fileName(result.getFileName())
                .fileSize(result.getFileSize())
                .directUrl(result.getDirectUrl())
                .accessKey(result.getAccessKey())
                .signature(result.getSignature())
                .expiry(result.getExpiry())
                .expireTime(result.getExpireTime())
                .headers(result.getHeaders())
                .build();
    }

    /**
     * 批量下载任务结果 → 批量下载任务响应
     */
    public BatchDownloadTaskResponse toBatchDownloadTaskResponse(BatchDownloadTaskResult result) {
        if (result == null) return null;
        return BatchDownloadTaskResponse.builder()
                .taskId(result.getTaskId())
                .status(result.getStatus())
                .totalFiles(result.getTotalFiles())
                .processedFiles(result.getProcessedFiles())
                .progress(result.getProgress())
                .totalSize(result.getTotalSize())
                .processedSize(result.getProcessedSize())
                .zipFileName(result.getZipFileName())
                .zipFileUrl(result.getZipFileUrl())
                .errorMessage(result.getErrorMessage())
                .createTime(result.getCreateTime())
                .completeTime(result.getCompleteTime())
                .build();
    }

}
