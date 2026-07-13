package com.yaocode.sts.components.file.runtime.model.query;

import lombok.Builder;
import lombok.Data;

/**
 * 审计日志查询
 */
@Data
@Builder
public class AuditLogQuery {

    /**
     * 文件ID
     */
    private String fileId;

    /**
     * 操作类型
     */
    private Integer operationType;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 页码
     */
    private Integer page;

    /**
     * 每页大小
     */
    private Integer size;

    /**
     * 租户ID
     */
    private String tenantId;
}