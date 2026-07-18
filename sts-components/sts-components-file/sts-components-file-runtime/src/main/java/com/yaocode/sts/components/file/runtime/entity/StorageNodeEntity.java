package com.yaocode.sts.components.file.runtime.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 存储节点管理表
 */
@Data
@TableName("storage_node")
public class StorageNodeEntity {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    @TableField("node_id")
    private Long nodeId;

    /**
     * 节点代码(唯一标识)
     */
    @TableField("node_code")
    private String nodeCode;

    /**
     * 节点名称(显示名称)
     */
    @TableField("node_name")
    private String nodeName;

    /**
     * 存储类型: local/minio/oss/rustfs/s3/azure/gcs
     */
    @TableField("storage_type")
    private String storageType;

    /**
     * 服务端点URL
     */
    @TableField("endpoint")
    private String endpoint;

    /**
     * 桶名称/根目录
     */
    @TableField("bucket_name")
    private String bucketName;

    /**
     * 访问密钥(加密存储)
     */
    @TableField("access_key")
    private String accessKey;

    /**
     * 秘密密钥(加密存储)
     */
    @TableField("secret_key")
    private String secretKey;

    /**
     * 区域
     */
    @TableField("region")
    private String region;

    /**
     * 最大容量(字节)
     */
    @TableField("max_capacity")
    private Long maxCapacity;

    /**
     * 已用容量(字节)
     */
    @TableField("used_capacity")
    private Long usedCapacity;

    /**
     * 节点状态: 1-正常 2-维护中 3-异常 4-已停用
     */
    @TableField("node_status")
    private Integer nodeStatus;

    /**
     * 负载均衡权重
     */
    @TableField("weight")
    private Integer weight;

    /**
     * 优先级(数字越小优先级越高)
     */
    @TableField("priority")
    private Integer priority;

    /**
     * 扩展配置(JSON)
     */
    @TableField("config_json")
    private String configJson;

    /**
     * 是否启用: 1-启用 0-禁用
     */
    @TableField("enabled")
    private Integer enabled;

    /**
     * 最后健康检查时间
     */
    @TableField("last_health_check")
    private LocalDateTime lastHealthCheck;

    /**
     * 健康状态: 1-健康 0-不健康
     */
    @TableField("health_status")
    private Integer healthStatus;

    // ========== 审计信息 ==========

    /**
     * 创建人ID
     */
    @TableField("created_user_id")
    private String createdUserId;

    /**
     * 创建人名称
     */
    @TableField("update_user_name")
    private String createdUserName;

    /**
     * 更新人ID
     */
    @TableField("updated_user_id")
    private String updatedUserId;

    /**
     * 更新人名称
     */
    @TableField("updated_user_name")
    private String updatedUserName;

    /**
     * 创建时间
     */
    @TableField(value = "created_time", fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    @TableField(value = "updated_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedTime;
}
