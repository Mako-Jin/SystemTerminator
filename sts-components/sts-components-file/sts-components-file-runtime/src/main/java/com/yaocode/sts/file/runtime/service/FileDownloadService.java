package com.yaocode.sts.file.runtime.service;

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
import org.springframework.core.io.Resource;

import java.io.OutputStream;
import java.util.List;

/**
 * 文件下载服务接口
 * <p>
 * 使用 CQRS 模式，命令和查询分离
 * </p>
 *
 * @author yaocode
 * @since 1.0.0
 */
public interface FileDownloadService {

    // ==================== 1. 基础下载（查询） ====================

    /**
     * 获取文件资源
     *
     * @param query 文件资源查询
     * @return 文件资源
     */
    Resource getFileResource(FileResourceQuery query);

    /**
     * 获取文件资源（指定版本）
     *
     * @param query 文件资源查询
     * @return 文件资源
     */
    Resource getFileVersionResource(FileVersionResourceQuery query);

    // ==================== 2. 流式下载（查询） ====================

    /**
     * 流式下载文件
     *
     * @param query 流式下载查询
     * @param outputStream 输出流
     */
    void downloadStream(StreamDownloadQuery query, OutputStream outputStream);

    /**
     * 分段流式下载（支持Range）
     *
     * @param query 分段下载查询
     * @param outputStream 输出流
     * @return Range下载结果（包含文件大小、范围等信息）
     */
    RangeDownloadResult downloadRange(RangeDownloadQuery query, OutputStream outputStream);

    // ==================== 3. 在线预览（查询） ====================

    /**
     * 预览文件
     *
     * @param query 预览查询
     * @param outputStream 输出流
     */
    void previewFile(PreviewQuery query, OutputStream outputStream);

    /**
     * 获取缩略图
     *
     * @param query 缩略图查询
     * @param outputStream 输出流
     */
    void getThumbnail(ThumbnailQuery query, OutputStream outputStream);

    /**
     * 获取媒体信息
     *
     * @param query 媒体信息查询
     * @return 媒体信息
     */
    MediaInfoResult getMediaInfo(MediaInfoQuery query);

    /**
     * 获取PDF页面
     *
     * @param query PDF页面查询
     * @param outputStream 输出流
     */
    void getPdfPage(PdfPageQuery query, OutputStream outputStream);

    // ==================== 4. 批量下载（命令 + 查询） ====================

    /**
     * 批量下载（同步）
     *
     * @param command 批量下载命令
     * @param outputStream 输出流
     */
    void batchDownload(BatchDownloadCommand command, OutputStream outputStream);

    /**
     * 批量下载（异步）
     *
     * @param command 批量下载命令
     * @return 任务ID
     */
    String asyncBatchDownload(AsyncBatchDownloadCommand command);

    /**
     * 获取批量下载任务状态（查询）
     *
     * @param query 批量下载任务查询
     * @return 任务状态
     */
    BatchDownloadTaskResult getBatchDownloadTask(BatchDownloadTaskQuery query);

    /**
     * 下载批量打包结果
     *
     * @param query 批量下载结果查询
     * @param outputStream 输出流
     */
    void downloadBatchResult(BatchDownloadResultQuery query, OutputStream outputStream);

    // ==================== 5. 安全下载（命令 + 查询） ====================

    /**
     * 获取下载Token（命令）
     *
     * @param command 下载Token命令
     * @return 下载Token
     */
    DownloadTokenResult getDownloadToken(DownloadTokenCommand command);

    /**
     * 使用Token下载（查询）
     *
     * @param query Token下载查询
     * @param outputStream 输出流
     */
    void downloadWithToken(TokenDownloadQuery query, OutputStream outputStream);

    /**
     * 加密下载（查询）
     *
     * @param query 加密下载查询
     * @param outputStream 输出流
     */
    void downloadEncrypted(EncryptedDownloadQuery query, OutputStream outputStream);

    // ==================== 6. 下载管理（查询） ====================

    /**
     * 获取下载历史
     *
     * @param query 下载历史查询
     * @return 下载历史列表
     */
    PageResult<DownloadRecordResult> getDownloadHistory(DownloadHistoryQuery query);

    /**
     * 获取文件下载统计
     *
     * @param query 下载统计查询
     * @return 下载统计
     */
    FileDownloadStatisticsResult getDownloadStatistics(DownloadStatisticsQuery query);

    /**
     * 获取下载排行榜
     *
     * @param query 下载排行榜查询
     * @return 下载排行榜
     */
    List<FileDownloadRankResult> getDownloadRanking(DownloadRankingQuery query);

    // ==================== 7. 跨域下载（查询） ====================

    /**
     * 获取跨域下载信息
     *
     * @param query 跨域下载查询
     * @return 跨域下载信息
     */
    CrossOriginDownloadInfoResult getCrossOriginInfo(CrossOriginDownloadQuery query);

    /**
     * 生成预签名URL（命令）
     *
     * @param command 预签名URL命令
     * @return 预签名URL
     */
    String generatePresignedUrl(PresignedUrlCommand command);

    /**
     * 直接下载（查询）
     *
     * @param query 直接下载查询
     * @param outputStream 输出流
     */
    void downloadDirect(DirectDownloadQuery query, OutputStream outputStream);
}
