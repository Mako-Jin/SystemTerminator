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
 * 运行时异常实体
 * 对应表: flow_tbl_incident
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "flow_tbl_incident", autoResultMap = true)
public class IncidentEntity extends BaseEntity {

    @TableId
    private String incidentId;

    private Integer incidentType;

    private String processInstanceId;

    private String executionId;

    private String processId;

    private String processKey;

    private String taskId;

    private String jobId;

    private String externalTaskId;

    private String jobDefinitionId;

    private String activityId;

    private String activityName;

    private String failedActivityId;

    private String incidentMessage;

    private String incidentStackUrl;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> configuration;

    private String causeIncidentId;

    private String rootCauseIncidentId;

    private LocalDateTime incidentTimestamp;

    private LocalDateTime resolvedTime;

    private Integer status;

    private String annotation;

    private String resolvedBy;

    private String resolvedByName;

    private Integer escalated;

    private LocalDateTime escalatedTime;

    private String escalatedTo;

    @Version
    private Integer rev;
}
