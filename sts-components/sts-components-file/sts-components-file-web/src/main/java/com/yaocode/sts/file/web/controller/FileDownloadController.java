package com.yaocode.sts.file.web.controller;

import com.yaocode.sts.common.basic.model.PageResult;
import com.yaocode.sts.common.web.model.PageResultModel;
import com.yaocode.sts.common.web.model.ResultModel;
import com.yaocode.sts.common.web.utils.PageResultUtils;
import com.yaocode.sts.common.web.utils.ResultUtils;
import com.yaocode.sts.file.interfaces.api.FileDownloadApi;
import com.yaocode.sts.file.interfaces.model.request.BatchDownloadRequest;
import com.yaocode.sts.file.interfaces.model.request.FileDownloadItemRequest;
import com.yaocode.sts.file.interfaces.model.response.BatchDownloadTaskResponse;
import com.yaocode.sts.file.interfaces.model.response.CrossOriginDownloadInfoResponse;
import com.yaocode.sts.file.interfaces.model.response.DownloadRecordResponse;
import com.yaocode.sts.file.interfaces.model.response.DownloadTokenResponse;
import com.yaocode.sts.file.interfaces.model.response.FileDownloadRankResponse;
import com.yaocode.sts.file.interfaces.model.response.FileDownloadStatisticsResponse;
import com.yaocode.sts.file.interfaces.model.response.MediaInfoResponse;
import com.yaocode.sts.file.runtime.model.result.DownloadRecordResult;
import com.yaocode.sts.file.runtime.model.result.RangeDownloadResult;
import com.yaocode.sts.file.runtime.service.FileDownloadService;
import com.yaocode.sts.file.web.converter.FileDownloadConverter;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 文件下载控制器
 *
 * @author yaocode
 * @since 1.0.0
 */
@Slf4j
@RestController
public class FileDownloadController implements FileDownloadApi {

    @Resource
    private FileDownloadService fileDownloadService;

    @Resource
    private FileDownloadConverter converter;

    // ==================== 1. 基础下载 ====================

    @Override
    public ResponseEntity<org.springframework.core.io.Resource> downloadFile(String fileId, Boolean preview,
                                                 HttpServletRequest request,
                                                 HttpServletResponse response) {
        log.info("下载文件: {}, preview: {}", fileId, preview);

        // 1. 获取文件资源
        org.springframework.core.io.Resource resource = fileDownloadService.getFileResource(
                converter.toFileResourceQuery(fileId, preview)
        );

        // 2. 构建响应头
        String fileName = resource.getFilename();
        String contentDisposition = preview != null && preview
                ? "inline; filename=\"" + encodeFileName(fileName) + "\""
                : "attachment; filename=\"" + encodeFileName(fileName) + "\"";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, contentDisposition);
        headers.set(HttpHeaders.ACCEPT_RANGES, "bytes");

        try {
            headers.setContentLength(resource.contentLength());
        } catch (IOException e) {
            log.warn("获取文件大小失败: {}", e.getMessage());
        }

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }

    @Override
    public ResponseEntity<org.springframework.core.io.Resource> downloadFileAs(String fileId, String fileName,
                                                   HttpServletRequest request,
                                                   HttpServletResponse response) {
        log.info("下载文件: {}, 自定义文件名: {}", fileId, fileName);

        org.springframework.core.io.Resource resource = fileDownloadService.getFileResource(
                converter.toFileResourceQuery(fileId, false)
        );

        String finalFileName = fileName != null ? fileName : resource.getFilename();
        String contentDisposition = "attachment; filename=\"" + encodeFileName(finalFileName) + "\"";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, contentDisposition);

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }

    @Override
    public ResponseEntity<org.springframework.core.io.Resource> downloadVersion(String fileId, Integer version,
                                                    HttpServletRequest request,
                                                    HttpServletResponse response) {
        log.info("下载文件版本: {}, version: {}", fileId, version);

        org.springframework.core.io.Resource resource = fileDownloadService.getFileVersionResource(
                converter.toFileVersionResourceQuery(fileId, version)
        );

        String fileName = resource.getFilename();
        String contentDisposition = "attachment; filename=\"" + encodeFileName(fileName) + "\"";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, contentDisposition);

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }

    // ==================== 2. 流式下载 ====================

    @Override
    public ResponseEntity<StreamingResponseBody> downloadStream(String fileId,
                                                                HttpServletResponse response) {
        log.info("流式下载文件: {}", fileId);

        StreamingResponseBody stream = outputStream -> fileDownloadService.downloadStream(
                converter.toStreamDownloadQuery(fileId),
                outputStream
        );

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileId + "\"")
                .body(stream);
    }

    @Override
    public ResponseEntity<StreamingResponseBody> downloadRange(String fileId, String range,
                                                               HttpServletResponse response) {
        log.info("分段下载文件: {}, range: {}", fileId, range);

        StreamingResponseBody stream = outputStream -> {
            RangeDownloadResult result = fileDownloadService.downloadRange(
                    converter.toRangeDownloadQuery(fileId, range),
                    outputStream
            );

            // 设置响应头（在Controller中设置）
            if (result.getContentRange() != null) {
                response.setHeader(HttpHeaders.CONTENT_RANGE, result.getContentRange());
            }
            response.setStatus(result.getHttpStatus());
            if (result.getContentLength() != null) {
                response.setContentLengthLong(result.getContentLength());
            }
        };

        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.ACCEPT_RANGES, "bytes")
                .body(stream);
    }

    // ==================== 3. 在线预览 ====================

    @Override
    public void previewFile(String fileId, HttpServletResponse response) throws IOException {
        log.info("预览文件: {}", fileId);

        response.setContentType("application/octet-stream");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileId + "\"");

        fileDownloadService.previewFile(
                converter.toPreviewQuery(fileId),
                response.getOutputStream()
        );
    }

    @Override
    public void getThumbnail(String fileId, Integer width, Integer height,
                             HttpServletResponse response) {
        log.info("获取缩略图: {}, {}x{}", fileId, width, height);

        try {
            response.setContentType("image/jpeg");
            fileDownloadService.getThumbnail(
                    converter.toThumbnailQuery(fileId, width, height),
                    response.getOutputStream()
            );
        } catch (IOException e) {
            log.error("获取缩略图失败", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResultModel<MediaInfoResponse> getMediaInfo(String fileId) {
        log.info("获取媒体信息: {}", fileId);

        MediaInfoResponse response = converter.toMediaInfoResponse(
                fileDownloadService.getMediaInfo(
                        converter.toMediaInfoQuery(fileId)
                )
        );
        return ResultUtils.ok(response);
    }

    @Override
    public void getPdfPage(String fileId, Integer page, HttpServletResponse response) {
        log.info("获取PDF页面: {}, page: {}", fileId, page);

        try {
            response.setContentType("image/png");
            fileDownloadService.getPdfPage(
                    converter.toPdfPageQuery(fileId, page),
                    response.getOutputStream()
            );
        } catch (IOException e) {
            log.error("获取PDF页面失败", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    // ==================== 4. 批量下载 ====================

    @Override
    public void batchDownload(List<FileDownloadItemRequest> files, String zipFileName,
                              HttpServletResponse response) {
        log.info("批量下载: {} 个文件, zip: {}", files.size(), zipFileName);

        try {
            String finalZipName = zipFileName != null ? zipFileName : "files.zip";
            response.setContentType("application/zip");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + encodeFileName(finalZipName) + "\"");

            fileDownloadService.batchDownload(
                    converter.toBatchDownloadCommand(files, zipFileName),
                    response.getOutputStream()
            );
        } catch (IOException e) {
            log.error("批量下载失败", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void batchDownloadWithStructure(BatchDownloadRequest request,
                                           HttpServletResponse response) {
        log.info("批量下载（带结构）: {} 个文件", request.getFiles().size());

        try {
            String zipFileName = request.getZipFileName() != null
                    ? request.getZipFileName() : "files.zip";
            response.setContentType("application/zip");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + encodeFileName(zipFileName) + "\"");

            fileDownloadService.batchDownload(
                    converter.toBatchDownloadCommand(request),
                    response.getOutputStream()
            );
        } catch (IOException e) {
            log.error("批量下载（带结构）失败", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResultModel<String> asyncBatchDownload(@Valid BatchDownloadRequest request) {
        log.info("异步批量下载: {} 个文件", request.getFiles().size());

        String taskId = fileDownloadService.asyncBatchDownload(
                converter.toAsyncBatchDownloadCommand(request)
        );
        return ResultUtils.ok(taskId);
    }

    @Override
    public ResultModel<BatchDownloadTaskResponse> getBatchDownloadTask(String taskId) {
        log.info("获取批量下载任务状态: {}", taskId);

        BatchDownloadTaskResponse response = converter.toBatchDownloadTaskResponse(
                fileDownloadService.getBatchDownloadTask(
                        converter.toBatchDownloadTaskQuery(taskId)
                )
        );
        return ResultUtils.ok(response);
    }

    @Override
    public void downloadBatchResult(String taskId, HttpServletResponse response) {
        log.info("下载批量打包结果: {}", taskId);

        try {
            fileDownloadService.downloadBatchResult(
                    converter.toBatchDownloadResultQuery(taskId),
                    response.getOutputStream()
            );
        } catch (IOException e) {
            log.error("下载批量打包结果失败", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    // ==================== 5. 安全下载 ====================

    @Override
    public ResultModel<DownloadTokenResponse> getDownloadToken(String fileId,
                                                               Integer expireSeconds,
                                                               Boolean limitIp) {
        log.info("获取下载Token: {}, expire: {}s, limitIp: {}", fileId, expireSeconds, limitIp);

        DownloadTokenResponse response = converter.toDownloadTokenResponse(
                fileDownloadService.getDownloadToken(
                        converter.toDownloadTokenCommand(fileId, expireSeconds, limitIp)
                )
        );
        return ResultUtils.ok(response);
    }

    @Override
    public void downloadWithToken(String token, HttpServletResponse response) {
        log.info("使用Token下载: {}", token);

        try {
            fileDownloadService.downloadWithToken(
                    converter.toTokenDownloadQuery(token),
                    response.getOutputStream()
            );
        } catch (IOException e) {
            log.error("Token下载失败", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void downloadEncrypted(String fileId, String encryptionKey,
                                  HttpServletResponse response) {
        log.info("加密下载: {}", fileId);

        try {
            fileDownloadService.downloadEncrypted(
                    converter.toEncryptedDownloadQuery(fileId, encryptionKey),
                    response.getOutputStream()
            );
        } catch (IOException e) {
            log.error("加密下载失败", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    // ==================== 6. 下载管理 ====================

    @Override
    public PageResultModel<DownloadRecordResponse> getDownloadHistory(Integer page, Integer size,
                                                                      String startTime, String endTime) {
        log.info("获取下载历史: page={}, size={}", page, size);

        PageResult<DownloadRecordResult> result = fileDownloadService.getDownloadHistory(
                converter.toDownloadHistoryQuery(page, size, startTime, endTime)
        );

        return PageResultUtils.ok(result.getTotal(), converter.toDownloadRecordResponseList(result.getRecords()));
    }

    @Override
    public ResultModel<FileDownloadStatisticsResponse> getDownloadStatistics(String fileId) {
        log.info("获取下载统计: {}", fileId);

        FileDownloadStatisticsResponse response = converter.toFileDownloadStatisticsResponse(
                fileDownloadService.getDownloadStatistics(
                        converter.toDownloadStatisticsQuery(fileId)
                )
        );
        return ResultUtils.ok(response);
    }

    @Override
    public ResultModel<List<FileDownloadRankResponse>> getDownloadRanking(String period, Integer limit) {
        log.info("获取下载排行榜: period={}, limit={}", period, limit);

        List<FileDownloadRankResponse> response = converter.toFileDownloadRankResponseList(
                fileDownloadService.getDownloadRanking(
                        converter.toDownloadRankingQuery(period, limit)
                )
        );
        return ResultUtils.ok(response);
    }

    // ==================== 7. 跨域下载 ====================

    @Override
    public ResultModel<CrossOriginDownloadInfoResponse> getCrossOriginInfo(String fileId, Integer expiry) {
        log.info("获取跨域下载信息: {}", fileId);

        CrossOriginDownloadInfoResponse response = converter.toCrossOriginDownloadInfoResponse(
                fileDownloadService.getCrossOriginInfo(
                        converter.toCrossOriginDownloadQuery(fileId, expiry)
                )
        );
        return ResultUtils.ok(response);
    }

    @Override
    public ResultModel<String> generatePresignedUrl(String fileId, Integer expiry, String method) {
        log.info("生成预签名URL: {}, method: {}", fileId, method);

        String url = fileDownloadService.generatePresignedUrl(
                converter.toPresignedUrlCommand(fileId, expiry, method)
        );
        return ResultUtils.ok(url);
    }

    @Override
    public void downloadDirect(String fileId, HttpServletResponse response) throws IOException {
        log.info("直接下载: {}", fileId);

        fileDownloadService.downloadDirect(
                converter.toDirectDownloadQuery(fileId),
                response.getOutputStream()
        );
    }

    // ==================== 辅助方法 ====================

    /**
     * 编码文件名（支持中文）
     */
    private String encodeFileName(String fileName) {
        if (fileName == null) return "file";
        try {
            return URLEncoder.encode(fileName, StandardCharsets.UTF_8)
                    .replace("+", "%20");
        } catch (Exception e) {
            return fileName;
        }
    }
}