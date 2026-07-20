package com.yaocode.sts.file.interfaces.model.request;

import lombok.Data;

/**
 * 存储节点请求
 */
@Data
public class StorageNodeInfoRequest {
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
     * 存储节点访问密钥
     */
    private String accessKey;
    /**
     * 存储节点密钥
     */
    private String secretKey;
    /**
     * 存储节点区域
     */
    private String region;
    /**
     * 存储节点最大容量
     */
    private Long maxCapacity;
    /**
     * 存储节点权重
     */
    private Integer weight;
    /**
     * 存储节点优先级
     */
    private Integer priority;
    /**
     * 是否启用
     */
    private Integer enabled;

}
