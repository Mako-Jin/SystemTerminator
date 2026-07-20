package com.yaocode.sts.components.file.runtime.model.command;

import lombok.Builder;
import lombok.Data;

/**
 * 添加存储节点命令
 */
@Data
@Builder
public class AddStorageNodeCommand {

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
     * 端点地址
     */
    private String endpoint;

    /**
     * 桶名称
     */
    private String bucketName;

    /**
     * 访问密钥
     */
    private String accessKey;

    /**
     * 秘密密钥
     */
    private String secretKey;

    /**
     * 区域
     */
    private String region;

    /**
     * 最大容量（字节）
     */
    private Long maxCapacity;

    /**
     * 权重
     */
    private Integer weight;

    /**
     * 优先级
     */
    private Integer priority;

    /**
     * 是否启用
     */
    private Integer enabled;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 用户ID
     */
    private String userId;
}