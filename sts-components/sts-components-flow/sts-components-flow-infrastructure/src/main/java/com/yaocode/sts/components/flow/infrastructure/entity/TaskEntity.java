package com.yaocode.sts.components.flow.infrastructure.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 任务实体
 * 对应表: flow_tbl_task
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "flow_tbl_task", autoResultMap = true)
public class TaskEntity extends BaseEntity {

    /**
     * 任务ID（主键）
     */
    @TableId
    private String taskId;

    /**
     * 任务定义KEY
     */
    private String taskKey;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 任务描述
     */
    private String taskDesc;

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
     * 节点KEY
     */
    private String nodeKey;

    /**
     * 节点名称（快照）
     */
    private String nodeName;

    /**
     * 处理人ID
     */
    private String assignee;

    /**
     * 处理人名称
     */
    private String assigneeName;

    /**
     * 拥有者ID
     */
    private String owner;

    /**
     * 拥有者名称
     */
    private String ownerName;

    /**
     * 候选人ID列表
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> candidateUsers;

    /**
     * 候选组ID列表
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> candidateGroups;

    /**
     * 委派状态（1=待委派，2=已委派，3=已解决）
     */
    private Integer delegationState;

    /**
     * 委派人ID
     */
    private String delegatedBy;

    /**
     * 委派人名称
     */
    private String delegatedByName;

    /**
     * 优先级
     */
    private Integer priority;

    /**
     * 任务描述
     */
    private String description;

    /**
     * 表单KEY
     */
    private String formKey;

    /**
     * 最后更新时间
     */
    private LocalDateTime lastUpdatedTime;

    /**
     * 截止日期
     */
    private LocalDateTime dueDate;

    /**
     * 跟踪日期
     */
    private LocalDateTime followUpDate;

    /**
     * 完成时间
     */
    private LocalDateTime finishTime;

    /**
     * 状态（1=待处理，2=处理中，3=已完成，4=已委派，5=已转交，6=已驳回，7=已取消）
     */
    private Integer status;

    /**
     * 父任务ID
     */
    private String parentTaskId;

    /**
     * 挂起状态（1=激活，2=挂起）
     */
    private Integer suspensionState;

    /**
     * 乐观锁版本号
     */
    @Version
    private Integer rev;
}