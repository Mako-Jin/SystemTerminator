package com.yaocode.sts.components.file.interfaces.model.response;

import lombok.Builder;
import lombok.Data;

/**
 * 异步上传响应
 */
@Data
@Builder
public class AsyncUploadResponse {
    /**
     * 任务ID
     */
    private String taskId;
    /**
     * 文件ID（如果秒传成功，直接返回）
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
     * 任务状态 QUEUED, PROCESSING, COMPLETED, FAILED, CANCELLED
     */
    private String status;
    /**
     * 进度（0-100）
     */
    private Integer progress;
    /**
     * 消息
     */
    private String message;
    /**
     * 预计完成时间（秒）
     */
    private Long estimatedTime;
    /**
     * 状态查询URL
     */
    private String statusUrl;
}