package com.yaocode.sts.components.file.runtime.model.command;

import lombok.Builder;
import lombok.Data;

/**
 * 取消异步任务命令
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class CancelAsyncTaskCommand {
    /** 任务ID */
    private String taskId;

    /** 租户ID */
    private String tenantId;

    /** 用户ID */
    private String userId;
}
