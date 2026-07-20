package com.yaocode.sts.file.interfaces.model.response;

import lombok.Builder;
import lombok.Data;

/**
 * 上传进度响应
 * <p>
 * 包含分片上传的进度信息，用于客户端查询上传进度
 * </p>
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class UploadProgressResponse {
    /**
     * 上传ID
     */
    private String uploadId;
    /**
     * 文件ID
     */
    private String fileId;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件大小（字节）
     */
    private Long fileSize;
    /**
     * 分片大小（字节）
     */
    private Long chunkSize;
    /**
     * 总分片数
     */
    private Integer totalChunks;
    /**
     * 已上传分片数
     */
    private Integer uploadedChunks;
    /**
     * 进度（0-100）
     */
    private Integer progress;
    /**
     * 已上传大小（字节）
     */
    private Long uploadedSize;
    /**
     * 状态
     */
    private String status;
    /**
     * 最后活跃时间（毫秒级时间戳）
     */
    private Long lastActiveTime;
    /**
     * 预计剩余时间（毫秒）
     */
    private Long estimatedRemainingTime;
    /**
     * 上传速度（字节/秒）
     */
    private Long uploadSpeed;
    /**
     * 消息
     */
    private String message;
}