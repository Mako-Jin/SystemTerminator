package com.yaocode.sts.components.file.runtime.model.query;

import lombok.Builder;
import lombok.Data;

/**
 * 存储节点统计查询
 */
@Data
@Builder
public class StorageNodeStatsQuery {

    /**
     * 租户ID
     */
    private String tenantId;
}