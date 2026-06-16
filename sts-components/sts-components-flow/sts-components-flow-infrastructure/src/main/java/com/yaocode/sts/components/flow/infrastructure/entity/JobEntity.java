package com.yaocode.sts.components.flow.infrastructure.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 异步作业实体
 * 对应表: flow_tbl_job
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "flow_tbl_job", autoResultMap = true)
public class JobEntity extends BaseEntity {

    /**
     * 作业ID（主键）
     */
    @TableId
    private String jobId;

    /**
     * 作业类型（1=定时器，2=异步延续，3=异步消息，4=重试作业，5=批量操作）
     */
    private Integer jobType;

    /**
     * 根流程实例ID
     */
    private String rootProcessInstanceId;

    /**
     * 部署ID
     */
    private String deploymentId;

    /**
     * 流程实例ID
     */
    private String processInstanceId;

    /**
     * 执行路径ID
     */
    private String executionId;

    /**
     * 流程定义ID
     */
    private String definitionId;

    /**
     * 流程定义KEY
     */
    private String definitionKey;

    /**
     * 活动节点ID
     */
    private String activityId;

    /**
     * 处理器类型
     */
    private String handlerType;

    /**
     * 处理器配置
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> handlerConfig;

    /**
     * 剩余重试次数
     */
    private Integer retriesRemaining;

    /**
     * 最大重试次数
     */
    private Integer maxRetries;

    /**
     * 重试间隔（毫秒）
     */
    private Integer retryBackoffMillis;

    /**
     * 上次失败的错误信息
     */
    private String lastFailureMessage;

    /**
     * 上次失败的堆栈对象存储URL
     */
    private String lastFailureStackFileId;

    /**
     * 计划执行时间
     */
    private LocalDateTime dueDate;

    /**
     * 实际执行时间
     */
    private LocalDateTime executedTime;

    /**
     * 作业锁过期时间
     */
    private LocalDateTime lockExpireTime;

    /**
     * 状态（1=等待，2=执行中，3=成功，4=失败，5=取消，6=挂起）
     */
    private Integer status;

    /**
     * 挂起状态（1=激活，2=挂起）
     */
    private Integer suspensionState;

    /**
     * 锁定节点ID
     */
    private String lockOwner;

    /**
     * 锁定节点主机名
     */
    private String lockOwnerHost;

    /**
     * 优先级
     */
    private Integer priority;

    /**
     * 是否排他（1=是，0=否）
     */
    private Integer exclusive;

    /**
     * 重复调度配置
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> repeatConfig;

    /**
     * 重复偏移量（毫秒）
     */
    private Long repeatOffsetMillis;

    /**
     * 最后失败的历史日志ID
     */
    private String lastFailureLogId;

    /**
     * 作业定义ID
     */
    private String jobDefinitionId;

    /**
     * 乐观锁版本号
     */
    @Version
    private Integer rev;
}
