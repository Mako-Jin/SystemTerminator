package com.yaocode.sts.components.flow.infrastructure.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 历史外部任务日志实体
 * 对应表: flow_tbl_hist_external_task_log
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "flow_tbl_hist_external_task_log", autoResultMap = true)
public class HistExternalTaskLogEntity extends BaseEntity {

    @TableId
    private String logId;

    private String externalTaskId;

    private String processInstanceId;

    private String rootProcessInstanceId;

    private String executionId;

    private String processId;

    private String processKey;

    private String activityId;

    private String activityInstanceId;

    private String topicName;

    private String workerId;

    private Long priority;

    private Integer retriesRemaining;

    private LocalDateTime timestamp;

    private LocalDateTime lockTime;

    private LocalDateTime completeTime;

    private Integer state;

    private String errorMessage;

    private String errorStackFileId;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> payloadSnapshot;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> resultSnapshot;

    private LocalDateTime removalTime;
}