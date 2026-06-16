package com.yaocode.sts.components.flow.infrastructure.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 历史活动实体
 * 对应表: flow_tbl_hist_activity
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("flow_tbl_hist_activity")
public class HistActivityEntity extends BaseEntity {

    @TableId
    private String historyId;

    private String rootProcessInstanceId;

    private String processInstanceId;

    private String executionId;

    private String processId;

    private String processKey;

    private String nodeId;

    private String nodeKey;

    private String nodeName;

    private Integer nodeType;

    private String parentActInstId;

    private String assignee;

    private String assigneeName;

    private String assigneeDeptId;

    private String assigneeDeptName;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Long durationMilliseconds;

    private String calledProcessInstanceId;

    private String taskId;

    private Integer status;

    private LocalDateTime removalTime;
}