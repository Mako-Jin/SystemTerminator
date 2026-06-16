package com.yaocode.sts.components.flow.infrastructure.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 历史作业日志实体
 * 对应表: flow_tbl_hist_job_log
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("flow_tbl_hist_job_log")
public class HistJobLogEntity extends BaseEntity {

    @TableId
    private String logId;

    private String jobId;

    private String jobDefinitionId;

    private String jobDefType;

    private String jobDefConfiguration;

    private String processInstanceId;

    private String rootProcessInstanceId;

    private String executionId;

    private String processId;

    private String processKey;

    private String deploymentId;

    private String activityId;

    private String failedActivityId;

    private Long priority;

    private Integer retriesRemaining;

    private LocalDateTime dueDate;

    private LocalDateTime executedTime;

    private Integer state;

    private String errorMessage;

    private String errorStackFileId;

    private String hostname;

    private LocalDateTime removalTime;
}
