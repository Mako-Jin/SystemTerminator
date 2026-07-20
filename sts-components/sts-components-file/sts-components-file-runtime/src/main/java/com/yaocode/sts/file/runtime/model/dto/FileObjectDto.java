package com.yaocode.sts.file.runtime.model.dto;

import lombok.Builder;
import lombok.Data;

import java.io.InputStream;

/**
 * 文件对象（Runtime层使用）
 * <p>
 * 不依赖任何 Web 层类，保持 Runtime 层的独立性
 * </p>
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class FileObjectDto {

    /** 文件名 */
    private String fileName;

    /** 文件大小（字节） */
    private Long fileSize;

    /** 文件输入流 */
    private InputStream inputStream;

    /** 文件内容类型（MIME） */
    private String contentType;

    /** 文件MD5（可选） */
    private String md5;

    /** 原始文件名（包含扩展名） */
    private String originalFilename;

    /** 是否已关闭 */
    @Builder.Default
    private boolean closed = false;

    /**
     * 获取文件扩展名
     */
    public String getFileExtension() {
        if (fileName == null) return null;
        int lastDot = fileName.lastIndexOf(".");
        return lastDot > 0 ? fileName.substring(lastDot + 1).toLowerCase() : null;
    }

    /**
     * 获取文件MIME类型（如果未设置则根据扩展名推测）
     */
    public String getContentTypeOrDefault() {
        if (contentType != null) return contentType;
        // 根据扩展名推测MIME类型
        String ext = getFileExtension();
        if (ext == null) return "application/octet-stream";
        return switch (ext) {
            case "jpg", "jpeg" -> "image/jpeg";
            case "png" -> "image/png";
            case "gif" -> "image/gif";
            case "pdf" -> "application/pdf";
            case "doc" -> "application/msword";
            case "docx" -> "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            case "xls" -> "application/vnd.ms-excel";
            case "xlsx" -> "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            case "ppt" -> "application/vnd.ms-powerpoint";
            case "pptx" -> "application/vnd.openxmlformats-officedocument.presentationml.presentation";
            case "zip" -> "application/zip";
            case "mp4" -> "video/mp4";
            case "mp3" -> "audio/mpeg";
            case "txt" -> "text/plain";
            case "json" -> "application/json";
            case "xml" -> "application/xml";
            default -> "application/octet-stream";
        };
    }

}
