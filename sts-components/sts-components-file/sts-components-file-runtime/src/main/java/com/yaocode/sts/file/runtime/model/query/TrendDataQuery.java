package com.yaocode.sts.file.runtime.model.query;

import lombok.Builder;
import lombok.Data;

/**
 * 趋势数据查询
 */
@Data
@Builder
public class TrendDataQuery {

    /**
     * 统计周期（day, week, month）
     */
    private String period;

    /**
     * 开始日期
     */
    private String startDate;

    /**
     * 结束日期
     */
    private String endDate;

    /**
     * 存储类型
     */
    private String storageType;

    /**
     * 租户ID
     */
    private String tenantId;
}