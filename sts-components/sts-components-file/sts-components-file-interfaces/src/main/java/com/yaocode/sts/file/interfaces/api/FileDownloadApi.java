package com.yaocode.sts.file.interfaces.api;

import com.yaocode.sts.common.web.model.PageResultModel;
import com.yaocode.sts.common.web.model.ResultModel;

import com.yaocode.sts.file.interfaces.model.request.BatchDownloadRequest;
import com.yaocode.sts.file.interfaces.model.request.FileDownloadItemRequest;
import com.yaocode.sts.file.interfaces.model.response.BatchDownloadTaskResponse;
import com.yaocode.sts.file.interfaces.model.response.CrossOriginDownloadInfoResponse;
import com.yaocode.sts.file.interfaces.model.response.DownloadRecordResponse;
import com.yaocode.sts.file.interfaces.model.response.DownloadTokenResponse;
import com.yaocode.sts.file.interfaces.model.response.FileDownloadRankResponse;
import com.yaocode.sts.file.interfaces.model.response.FileDownloadStatisticsResponse;
import com.yaocode.sts.file.interfaces.model.response.MediaInfoResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.util.List;

/**
 * 文件下载API
 * <p>
 * 提供文件下载相关功能：
 * <ul>
 *   <li>普通下载 - 直接下载文件</li>
 *   <li>流式下载 - 支持大文件的流式下载</li>
 *   <li>断点续传 - 支持Range请求，实现断点续传</li>
 *   <li>预览模式 - 支持在线预览（图片、PDF等）</li>
 *   <li>批量下载 - 批量下载文件（ZIP打包）</li>
 *   <li>缩略图下载 - 下载图片缩略图</li>
 *   <li>访问控制 - 下载权限校验、防盗链</li>
 *   <li>下载统计 - 记录下载次数和日志</li>
 * </ul>
 * </p>
 *
 * @author yaocode
 * @since 1.0.0
 */
@RequestMapping("/files")
public interface FileDownloadApi {

    // ==================== 1. 基础下载 ====================

    /**
     * 下载文件
     * <p>
     * 支持HTTP Range请求，实现断点续传。
     * 支持大文件的流式下载，不占用大量内存。
     * </p>
     *
     * @param fileId 文件ID
     * @param preview 是否预览模式（true: 浏览器内预览，false: 强制下载）
     * @param request HTTP请求（用于获取Range头）
     * @param response HTTP响应
     * @return 流式响应
     */
    @GetMapping("/download/{fileId}")
    ResponseEntity<Resource> downloadFile(
            @PathVariable String fileId,
            @RequestParam(defaultValue = "false") Boolean preview,
            HttpServletRequest request,
            HttpServletResponse response
    );

    /**
     * 下载文件（指定文件名）
     * <p>
     * 下载文件并使用指定的文件名保存
     * </p>
     *
     * @param fileId 文件ID
     * @param fileName 自定义文件名（可选）
     * @param request HTTP请求
     * @param response HTTP响应
     * @return 流式响应
     */
    @GetMapping("/download/{fileId}/as")
    ResponseEntity<Resource> downloadFileAs(
            @PathVariable String fileId,
            @RequestParam(required = false) String fileName,
            HttpServletRequest request,
            HttpServletResponse response
    );

    /**
     * 下载文件的指定版本
     *
     * @param fileId 文件ID
     * @param version 版本号
     * @param request HTTP请求
     * @param response HTTP响应
     * @return 流式响应
     */
    @GetMapping("/download/{fileId}/version/{version}")
    ResponseEntity<Resource> downloadVersion(
            @PathVariable String fileId,
            @PathVariable Integer version,
            HttpServletRequest request,
            HttpServletResponse response
    );

    // ==================== 2. 流式下载 ====================

    /**
     * 流式下载文件（支持超大文件）
     * <p>
     * 使用 StreamingResponseBody 实现流式输出，
     * 适合超大文件下载，内存占用小。
     * </p>
     *
     * @param fileId 文件ID
     * @param response HTTP响应
     * @return 流式响应体
     */
    @GetMapping("/download/{fileId}/stream")
    ResponseEntity<StreamingResponseBody> downloadStream(
            @PathVariable String fileId,
            HttpServletResponse response
    );

    /**
     * 分段流式下载
     * <p>
     * 支持指定下载范围，适用于视频播放、断点续传等场景
     * </p>
     *
     * @param fileId 文件ID
     * @param range 请求范围（如: bytes=0-1023）
     * @param response HTTP响应
     * @return 流式响应体
     */
    @GetMapping("/download/{fileId}/range")
    ResponseEntity<StreamingResponseBody> downloadRange(
            @PathVariable String fileId,
            @RequestHeader(value = "Range", required = false) String range,
            HttpServletResponse response
    );

    // ==================== 3. 在线预览 ====================

    /**
     * 在线预览文件
     * <p>
     * 支持图片、PDF、文档等格式的在线预览。
     * 对于图片，返回可直接在浏览器中显示的格式。
     * </p>
     *
     * @param fileId 文件ID
     * @param response HTTP响应
     */
    @GetMapping("/preview/{fileId}")
    void previewFile(
            @PathVariable String fileId,
            HttpServletResponse response
    ) throws IOException;

    /**
     * 获取图片缩略图
     *
     * @param fileId 文件ID
     * @param width 缩略图宽度（可选）
     * @param height 缩略图高度（可选）
     * @param response HTTP响应
     */
    @GetMapping("/preview/{fileId}/thumbnail")
    void getThumbnail(
            @PathVariable String fileId,
            @RequestParam(required = false) Integer width,
            @RequestParam(required = false) Integer height,
            HttpServletResponse response
    );

    /**
     * 获取媒体文件播放信息
     * <p>
     * 返回视频/音频文件的播放信息，供前端播放器使用
     * </p>
     *
     * @param fileId 文件ID
     * @return 媒体信息
     */
    @GetMapping("/preview/{fileId}/media-info")
    ResultModel<MediaInfoResponse> getMediaInfo(@PathVariable String fileId);

    /**
     * 获取PDF预览信息（分页）
     *
     * @param fileId 文件ID
     * @param page 页码
     * @param response HTTP响应
     */
    @GetMapping("/preview/{fileId}/pdf/page")
    void getPdfPage(
            @PathVariable String fileId,
            @RequestParam Integer page,
            HttpServletResponse response
    );

    // ==================== 4. 批量下载 ====================

    /**
     * 批量下载文件（打包为ZIP）
     *
     * @param files 文件列表
     * @param zipFileName ZIP文件名（可选）
     * @param response HTTP响应
     */
    @PostMapping("/download/batch")
    void batchDownload(
            @RequestBody List<FileDownloadItemRequest> files,
            @RequestParam(required = false) String zipFileName,
            HttpServletResponse response
    );

    /**
     * 批量下载文件（按目录结构打包）
     *
     * @param request 批量下载请求
     * @param response HTTP响应
     */
    @PostMapping("/download/batch/with-structure")
    void batchDownloadWithStructure(
            @RequestBody BatchDownloadRequest request,
            HttpServletResponse response
    );

    /**
     * 异步批量下载 - 提交任务
     *
     * @param request 批量下载请求
     * @return 任务ID
     */
    @PostMapping("/download/batch/async")
    ResultModel<String> asyncBatchDownload(@RequestBody BatchDownloadRequest request);

    /**
     * 获取批量下载任务状态
     *
     * @param taskId 任务ID
     * @return 任务状态
     */
    @GetMapping("/download/batch/task/{taskId}")
    ResultModel<BatchDownloadTaskResponse> getBatchDownloadTask(@PathVariable String taskId);

    /**
     * 下载批量打包好的文件
     *
     * @param taskId 任务ID
     * @param response HTTP响应
     */
    @GetMapping("/download/batch/task/{taskId}/result")
    void downloadBatchResult(
            @PathVariable String taskId,
            HttpServletResponse response
    );

    // ==================== 5. 安全下载 ====================

    /**
     * 获取文件下载链接（限时）
     * <p>
     * 生成一个有时效性的下载链接，用于授权下载
     * </p>
     *
     * @param fileId 文件ID
     * @param expireSeconds 过期时间（秒）
     * @param limitIp 是否限制IP
     * @return 临时下载链接
     */
    @PostMapping("/download/token")
    ResultModel<DownloadTokenResponse> getDownloadToken(
            @RequestParam String fileId,
            @RequestParam(defaultValue = "300") Integer expireSeconds,
            @RequestParam(defaultValue = "true") Boolean limitIp
    );

    /**
     * 使用Token下载文件
     *
     * @param token 下载Token
     * @param response HTTP响应
     */
    @GetMapping("/download/token/{token}")
    void downloadWithToken(
            @PathVariable String token,
            HttpServletResponse response
    );

    /**
     * 加密下载
     * <p>
     * 下载时对文件进行加密，需要客户端用密钥解密
     * </p>
     *
     * @param fileId 文件ID
     * @param encryptionKey 加密密钥（Base64）
     * @param response HTTP响应
     */
    @GetMapping("/download/{fileId}/encrypted")
    void downloadEncrypted(
            @PathVariable String fileId,
            @RequestParam String encryptionKey,
            HttpServletResponse response
    );

    // ==================== 6. 下载管理 ====================

    /**
     * 获取当前用户的下载历史
     *
     * @param page 页码
     * @param size 每页数量
     * @param startTime 开始时间（可选）
     * @param endTime 结束时间（可选）
     * @return 下载历史列表
     */
    @GetMapping("/download/history")
    PageResultModel<DownloadRecordResponse> getDownloadHistory(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime
    );

    /**
     * 获取文件的下载统计信息
     *
     * @param fileId 文件ID
     * @return 下载统计
     */
    @GetMapping("/download/{fileId}/statistics")
    ResultModel<FileDownloadStatisticsResponse> getDownloadStatistics(@PathVariable String fileId);

    /**
     * 获取下载排行榜
     *
     * @param period 统计周期（day/week/month/year）
     * @param limit 获取数量
     * @return 下载排行榜
     */
    @GetMapping("/download/ranking")
    ResultModel<List<FileDownloadRankResponse>> getDownloadRanking(
            @RequestParam(defaultValue = "week") String period,
            @RequestParam(defaultValue = "10") Integer limit
    );

    // ==================== 7. 跨域下载（CORS） ====================

    /**
     * 获取跨域下载信息（用于前端直连OSS等场景）
     *
     * @param fileId 文件ID
     * @param expiry 过期时间（秒）
     * @return 跨域下载信息
     */
    @GetMapping("/download/{fileId}/cors")
    ResultModel<CrossOriginDownloadInfoResponse> getCrossOriginInfo(
            @PathVariable String fileId,
            @RequestParam(defaultValue = "3600") Integer expiry
    );

    /**
     * 生成预签名URL（用于前端直传/下载）
     *
     * @param fileId 文件ID
     * @param expiry 过期时间（秒）
     * @param method HTTP方法（GET/PUT）
     * @return 预签名URL
     */
    @PostMapping("/download/{fileId}/presigned-url")
    ResultModel<String> generatePresignedUrl(
            @PathVariable String fileId,
            @RequestParam(defaultValue = "3600") Integer expiry,
            @RequestParam(defaultValue = "GET") String method
    );

    /**
     * 使用 HttpServletResponse 直接输出
     * <p>
     * 最灵活的方式，可以完全控制输出过程
     * </p>
     */
    @GetMapping("/download/{fileId}/direct")
    void downloadDirect(
            @PathVariable String fileId,
            HttpServletResponse response
    ) throws IOException;

}
