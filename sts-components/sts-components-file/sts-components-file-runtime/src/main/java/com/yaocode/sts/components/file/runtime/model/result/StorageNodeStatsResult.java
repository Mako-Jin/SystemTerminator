package com.yaocode.sts.components.file.runtime.model.result;


import lombok.Builder;
import lombok.Data;

/**
 * 存储节点统计结果
 */
@Data
@Builder
public class StorageNodeStatsResult {

    /**
     * 节点ID
     */
    private Long nodeId;

    /**
     * 节点名称
     */
    private String nodeName;

    /**
     * 存储类型
     */
    private String storageType;

    /**
     * 文件数量
     */
    private Long fileCount;

    /**
     * 已使用空间（字节）
     */
    private Long usedSpace;

    /**
     * 最大容量（字节）
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

    /**
     * 健康状态描述
     */
    private String healthStatusDesc;

    /**
     * 活跃连接数
     */
    private Integer activeConnections;

    /**
     * 总请求数
     */
    private Long totalRequests;

    /**
     * 平均响应时间（毫秒）
     */
    private Double avgResponseTime;
}