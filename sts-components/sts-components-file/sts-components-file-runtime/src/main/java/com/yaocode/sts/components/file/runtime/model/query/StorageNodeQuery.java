package com.yaocode.sts.components.file.runtime.model.query;

import lombok.Builder;
import lombok.Data;

/**
 * 存储节点查询
 */
@Data
@Builder
public class StorageNodeQuery {

    /**
     * 是否只查询启用的节点
     */
    private Boolean enabledOnly;

    /**
     * 存储类型
     */
    private String storageType;

    /**
     * 租户ID
     */
    private String tenantId;
}