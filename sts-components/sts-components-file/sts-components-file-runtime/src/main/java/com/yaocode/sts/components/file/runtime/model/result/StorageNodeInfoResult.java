package com.yaocode.sts.components.file.runtime.model.result;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 存储节点信息结果
 */
@Data
@Builder
public class StorageNodeInfoResult {

    /**
     * 节点ID
     */
    private Long nodeId;

    /**
     * 节点编码
     */
    private String nodeCode;

    /**
     * 节点名称
     */
    private String nodeName;

    /**
     * 存储类型
     */
    private String storageType;

    /**
     * 存储类型描述
     */
    private String storageTypeDesc;

    /**
     * 端点地址
     */
    private String endpoint;

    /**
     * 桶名称
     */
    private String bucketName;

    /**
     * 最大容量（字节）
     */
    private Long maxCapacity;

    /**
     * 已使用容量（字节）
     */
    private Long usedCapacity;

    /**
     * 使用率
     */
    private Double usageRate;

    /**
     * 节点状态
     */
    private Integer nodeStatus;

    /**
     * 节点状态描述
     */
    private String nodeStatusDesc;

    /**
     * 健康状态
     */
    private Integer healthStatus;

    /**
     * 健康状态描述
     */
    private String healthStatusDesc;

    /**
     * 是否启用
     */
    private Boolean enabled;

    /**
     * 权重
     */
    private Integer weight;

    /**
     * 优先级
     */
    private Integer priority;

    /**
     * 最后健康检查时间
     */
    private LocalDateTime lastHealthCheck;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;

    /**
     * 租户ID
     */
    private String tenantId;
}