package com.yaocode.sts.components.file.interfaces.model.response;

import lombok.Builder;
import lombok.Data;

/**
 * 存储节点统计
 */
@Data
@Builder
public class StorageNodeStatsResponse {
    /**
     * 存储节点ID
     */
    private Long nodeId;
    /**
     * 存储节点名称
     */
    private String nodeName;
    /**
     * 存储节点类型
     * 1: 主节点
     * 2: 从节点
     */
    private String storageType;
    /**
     * 文件数量
     */
    private Long fileCount;
     /**
     * 已用空间
     */
    private Long usedSpace;
    /**
     * 最大容量
     */
    private Long maxCapacity;
    /**
     * 使用率
     */
    private Double usageRate;
    /**
     * 健康状态
     */
    private Integer healthStatus;

    private String healthStatusDesc;

    private Integer activeConnections;

    private Long totalRequests;

    private Double avgResponseTime;

}
