package com.yaocode.sts.components.file.interfaces.model.response;

import lombok.Data;

/**
 * 迁移任务状态
 */
@Data
public class MigrateTaskStatusResponse {

    /**
     * 迁移任务ID
     */
    private String taskId;
    /**
     * 文件ID
     */
    private String fileId;
    /**
     * 源存储类型
     */
    private String sourceStorage;
    /**
     * 目标存储类型
     */
    private String targetStorage;
    /**
     * 迁移进度（0-100）
     */
    private Integer progress;        // 0-100
    /**
     * 迁移任务状态 PENDING, RUNNING, SUCCESS, FAILED, CANCELLED
     */
    private String status;
    /**
     * 已传输大小（字节）
     */
    private Long transferredSize;
    /**
     * 总大小（字节）
     */
    private Long totalSize;
    /**
     * 开始时间（毫秒级时间戳）
     */
    private Long startTime;
    /**
     * 结束时间（毫秒级时间戳）
     */
    private Long endTime;
    /**
     * 错误码（失败时返回）
     */
    private String errorCode;
    /**
     * 错误信息（失败时返回）
     */
    private String errorMessage;

}
