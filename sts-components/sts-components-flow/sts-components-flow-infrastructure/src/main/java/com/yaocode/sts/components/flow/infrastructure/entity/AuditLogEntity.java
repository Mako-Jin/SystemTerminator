package com.yaocode.sts.components.flow.infrastructure.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 审计日志实体
 * 对应表: flow_tbl_audit_log
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "flow_tbl_audit_log", autoResultMap = true)
public class AuditLogEntity extends BaseEntity {

    /**
     * 审计日志ID（主键）
     */
    @TableId
    private String auditId;

    /**
     * 操作ID（同一操作的多个变更共享）
     */
    private String operationId;

    /**
     * 流程实例ID
     */
    private String processInstanceId;

    /**
     * 根流程实例ID
     */
    private String rootProcessInstanceId;

    /**
     * 执行路径ID
     */
    private String executionId;

    /**
     * 流程定义ID
     */
    private String processId;

    /**
     * 流程定义KEY
     */
    private String processKey;

    /**
     * 部署ID
     */
    private String deploymentId;

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 作业ID
     */
    private String jobId;

    /**
     * 作业定义ID
     */
    private String jobDefinitionId;

    /**
     * 外部任务ID
     */
    private String externalTaskId;

    /**
     * 操作用户ID
     */
    private String userId;

    /**
     * 操作用户名称
     */
    private String userName;

    /**
     * 操作用户部门ID
     */
    private String userDeptId;

    /**
     * 操作用户部门名称
     */
    private String userDeptName;

    /**
     * 操作时间戳
     */
    private LocalDateTime timestamp;

    /**
     * 操作类型
     */
    private Integer operationType;

    /**
     * 实体类型
     */
    private Integer entityType;

    /**
     * 实体ID
     */
    private String entityId;

    /**
     * 变更属性名
     */
    private String property;

    /**
     * 原值
     */
    private String oldValue;

    /**
     * 新值
     */
    private String newValue;

    /**
     * 操作分类
     */
    private String category;

    /**
     * 备注/审批意见
     */
    private String annotation;

    /**
     * 客户端IP
     */
    private String clientIp;

    /**
     * 用户代理
     */
    private String userAgent;

    /**
     * 清理时间
     */
    private LocalDateTime removalTime;
}
