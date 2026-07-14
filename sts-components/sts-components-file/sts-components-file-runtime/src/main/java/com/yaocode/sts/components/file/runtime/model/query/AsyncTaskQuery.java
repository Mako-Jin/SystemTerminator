package com.yaocode.sts.components.file.runtime.model.query;

import lombok.Builder;
import lombok.Data;

/**
 * 异步任务查询
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class AsyncTaskQuery {
    /** 任务ID */
    private String taskId;

    /** 租户ID */
    private String tenantId;
}
