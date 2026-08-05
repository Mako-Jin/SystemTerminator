package com.yaocode.sts.file.infrastructure.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yaocode.sts.common.infrastructure.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 存储节点管理表
 */
@Data
@TableName("file_tbl_storage_node")
@EqualsAndHashCode(callSuper = true)
public class StorageNodeEntity extends BasePo {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
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

}
