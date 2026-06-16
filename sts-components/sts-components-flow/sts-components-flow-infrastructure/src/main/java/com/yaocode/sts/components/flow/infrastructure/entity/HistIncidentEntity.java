package com.yaocode.sts.components.flow.infrastructure.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 历史异常实体
 * 对应表: flow_tbl_hist_incident
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("flow_tbl_hist_incident")
public class HistIncidentEntity extends BaseEntity {

    @TableId
    private String incidentId;

    private Integer incidentType;

    private String processInstanceId;

    private String rootProcessInstanceId;

    private String executionId;

    private String processId;

    private String processKey;

    private String activityId;

    private String failedActivityId;

    private String jobDefinitionId;

    private String causeIncidentId;

    private String rootCauseIncidentId;

    private String incidentMessage;

    private String configuration;

    private String historyConfiguration;

    private LocalDateTime incidentTimestamp;

    private LocalDateTime endTime;

    private Integer status;

    private String annotation;

    private String resolvedBy;

    private String resolvedByName;

    private LocalDateTime resolvedTime;

    private Long resolutionDurationMs;

    private LocalDateTime removalTime;
}
