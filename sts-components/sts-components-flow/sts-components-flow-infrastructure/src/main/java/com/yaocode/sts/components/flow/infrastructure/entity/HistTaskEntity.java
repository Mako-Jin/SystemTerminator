package com.yaocode.sts.components.flow.infrastructure.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 历史任务实体
 * 对应表: flow_tbl_hist_task
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("flow_tbl_hist_task")
public class HistTaskEntity extends BaseEntity {

    @TableId
    private String taskId;

    private String processInstanceId;

    private String rootProcessInstanceId;

    private String executionId;

    private String processId;

    private String processKey;

    private String activityInstanceId;

    private String parentTaskId;

    private String nodeId;

    private String nodeKey;

    private String nodeName;

    private String taskName;

    private String taskDesc;

    private String assignee;

    private String assigneeName;

    private String assigneeDeptId;

    private String assigneeDeptName;

    private String owner;

    private String ownerName;

    private Integer priority;

    private Integer status;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Long durationMilliseconds;

    private LocalDateTime dueDate;

    private LocalDateTime followUpDate;

    private String deleteReason;

    private LocalDateTime removalTime;
}
