package com.yaocode.sts.file.runtime.model.query;

import lombok.Builder;
import lombok.Data;

/**
 * 批量下载任务查询
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class BatchDownloadTaskQuery {
    /** 任务ID */
    private String taskId;

    /** 租户ID */
    private String tenantId;

    /** 用户ID */
    private String userId;
}
