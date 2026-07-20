package com.yaocode.sts.file.runtime.model.command;

import lombok.Builder;
import lombok.Data;

/**
 * 取消迁移命令
 */
@Data
@Builder
public class CancelMigrateCommand {

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 取消原因
     */
    private String reason;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 用户ID
     */
    private String userId;
}