package com.yaocode.sts.components.file.runtime.model.query;

import lombok.Builder;
import lombok.Data;

/**
 * 异步任务列表查询
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class AsyncTaskListQuery {
    /** 任务状态 */
    private Integer taskStatus;

    /** 页码 */
    private Integer page;

    /** 每页数量 */
    private Integer size;

    /** 租户ID */
    private String tenantId;
}
