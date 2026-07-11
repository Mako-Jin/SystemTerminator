package com.yaocode.sts.components.file.interfaces.model.response;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 存储节点信息
 */
@Data
public class StorageNodeInfoResponse {
    /**
     * 存储节点ID
     */
    private Long nodeId;
    /**
     * 存储节点编码
     */
    private String nodeCode;
    /**
     * 存储节点名称
     */
    private String nodeName;
    /**
     * 存储节点类型
     */
    private String storageType;
    /**
     * 存储节点端点
     */
    private String endpoint;
    /**
     * 存储节点桶名称
     */
    private String bucketName;
    /**
     * 存储节点最大容量
     */
    private Long maxCapacity;
    /**
     * 存储节点已用容量
     */
    private Long usedCapacity;
    /**
     * 存储节点状态
     */
    private Integer nodeStatus;
    /**
     * 存储节点健康状态
     */
    private Integer healthStatus;
    /**
     * 是否启用
     */
    private Boolean enabled;
    /**
     * 最后一次健康检查时间
     */
    private LocalDateTime lastHealthCheck;
    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

}
