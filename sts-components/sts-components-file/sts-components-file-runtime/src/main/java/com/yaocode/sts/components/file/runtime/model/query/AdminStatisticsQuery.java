package com.yaocode.sts.components.file.runtime.model.query;

import lombok.Builder;
import lombok.Data;

/**
 * 管理端统计查询
 */
@Data
@Builder
public class AdminStatisticsQuery {

    /**
     * 存储类型
     */
    private String storageType;

    /**
     * 租户ID
     */
    private String tenantId;
}