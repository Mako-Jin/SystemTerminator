package com.yaocode.sts.file.interfaces.model.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 分片会话VO
 */
@Data
@Builder
public class MultipartSessionResponse {
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
     * 状态 UPLOADING, COMPLETED, CANCELLED, TIMEOUT
     */
    private String status;
    /**
     * 过期时间（毫秒级时间戳）
     */
    private Long expireTime;
    /**
     * 最后活跃时间（毫秒级时间戳）
     */
    private Long lastActiveTime;
    /**
     * 创建时间
     */
    private LocalDateTime createdTime;
}