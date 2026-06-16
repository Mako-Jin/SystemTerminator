package com.yaocode.sts.components.flow.infrastructure.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 任务计量日志实体
 * 对应表: flow_tbl_task_meter_log
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("flow_tbl_task_meter_log")
public class TaskMeterLogEntity extends BaseEntity {

    @TableId
    private String taskMeterLogId;

    private String assignee;

    private String assigneeName;

    private Long assigneeHash;

    private Integer taskType;

    private String nodeKey;

    private String nodeName;

    private String processKey;

    private String processInstanceId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Long durationMilliseconds;

    private Long waitMilliseconds;

    private Integer isOverdue;

    private Long overdueMilliseconds;

    private LocalDateTime recordTime;

    private Long recordMilliseconds;
}
