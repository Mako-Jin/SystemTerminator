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
 * 流程实例实体
 * 对应表: flow_tbl_process_instance
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "flow_tbl_process_instance", autoResultMap = true)
public class ProcessInstanceEntity extends BaseEntity {

    /**
     * 流程实例ID（主键）
     */
    @TableId
    private String processInstanceId;

    /**
     * 业务键
     */
    private String businessKey;

    /**
     * 流程定义ID
     */
    private String processId;

    /**
     * 流程定义KEY
     */
    private String processKey;

    /**
     * 流程定义版本
     */
    private Integer processVersion;

    /**
     * 流程定义名称（快照）
     */
    private String processName;

    /**
     * 状态（1=运行中，2=挂起，3=已完成，4=已终止，5=异常）
     */
    private Integer status;

    /**
     * 挂起状态（1=激活，2=挂起）
     */
    private Integer suspensionState;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 总耗时（毫秒）
     */
    private Long durationMilliseconds;

    /**
     * 发起人ID
     */
    private String startUserId;

    /**
     * 发起人名称
     */
    private String startUserName;

    /**
     * 开始节点KEY
     */
    private String startNodeKey;

    /**
     * 当前所在节点KEY列表（支持多分支）
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> currentNodeKeys;

    /**
     * 结束节点KEY
     */
    private String endNodeKey;

    /**
     * 终止/删除原因
     */
    private String deleteReason;

    /**
     * 父流程实例ID
     */
    private String superProcessInstanceId;

    /**
     * 调用活动对应的执行路径ID
     */
    private String superExecutionId;

    /**
     * 根流程实例ID
     */
    private String rootProcessInstanceId;

    /**
     * 乐观锁版本号
     */
    @Version
    private Integer rev;
}