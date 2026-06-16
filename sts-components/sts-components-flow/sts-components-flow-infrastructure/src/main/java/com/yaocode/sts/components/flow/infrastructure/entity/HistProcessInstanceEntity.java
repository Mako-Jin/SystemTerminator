package com.yaocode.sts.components.flow.infrastructure.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 历史流程实例实体
 * 对应表: flow_tbl_hist_process_instance
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("flow_tbl_hist_process_instance")
public class HistProcessInstanceEntity extends BaseEntity {

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
     * 流程名称（快照）
     */
    private String processName;

    /**
     * 最终状态（3=已完成，4=已终止，5=异常）
     */
    private Integer status;

    /**
     * 删除/终止原因
     */
    private String deleteReason;

    /**
     * 发起人ID
     */
    private String startUserId;

    /**
     * 发起人名称
     */
    private String startUserName;

    /**
     * 发起人部门ID（快照）
     */
    private String startUserDeptId;

    /**
     * 发起人部门名称（快照）
     */
    private String startUserDeptName;

    /**
     * 开始节点ID
     */
    private String startNodeId;

    /**
     * 开始节点KEY
     */
    private String startNodeKey;

    /**
     * 开始节点名称
     */
    private String startNodeName;

    /**
     * 结束节点ID
     */
    private String endNodeId;

    /**
     * 结束节点KEY
     */
    private String endNodeKey;

    /**
     * 结束节点名称
     */
    private String endNodeName;

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
     * 父流程实例ID
     */
    private String superProcessInstanceId;

    /**
     * 根流程实例ID
     */
    private String rootProcessInstanceId;

    /**
     * 清理时间
     */
    private LocalDateTime removalTime;
}
