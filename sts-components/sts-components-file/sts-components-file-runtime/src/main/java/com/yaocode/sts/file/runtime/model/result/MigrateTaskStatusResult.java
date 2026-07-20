package com.yaocode.sts.file.runtime.model.result;

import lombok.Builder;
import lombok.Data;

/**
 * 迁移任务状态结果
 */
@Data
@Builder
public class MigrateTaskStatusResult {

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 文件ID
     */
    private String fileId;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 源存储类型
     */
    private String sourceStorage;

    /**
     * 目标存储类型
     */
    private String targetStorage;

    /**
     * 进度（0-100）
     */
    private Integer progress;

    /**
     * 状态
     */
    private String status;

    /**
     * 状态描述
     */
    private String statusDesc;

    /**
     * 已传输大小（字节）
     */
    private Long transferredSize;

    /**
     * 总大小（字节）
     */
    private Long totalSize;

    /**
     * 开始时间（时间戳）
     */
    private Long startTime;

    /**
     * 结束时间（时间戳）
     */
    private Long endTime;

    /**
     * 耗时（毫秒）
     */
    private Long elapsedTime;

    /**
     * 错误信息
     */
    private String errorMessage;

    /**
     * 租户ID
     */
    private String tenantId;
}