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
 * 外部任务实体
 * 对应表: flow_tbl_external_task
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "flow_tbl_external_task", autoResultMap = true)
public class ExternalTaskEntity extends BaseEntity {

    @TableId
    private String externalTaskId;

    private String topicName;

    private String processInstanceId;

    private String executionId;

    private String processId;

    private String processKey;

    private String nodeKey;

    private String nodeName;

    private String workerId;

    private String workerHost;

    private Integer retriesRemaining;

    private Integer maxRetries;

    private Integer retryBackoffMillis;

    private LocalDateTime lockExpireTime;

    private String lockOwner;

    private Integer status;

    private Integer suspensionState;

    private Integer priority;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> payload;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> result;

    private String errorMessage;

    private String errorStackFileId;

    private String lastFailureLogId;

    private LocalDateTime lockTime;

    private LocalDateTime completeTime;

    private Integer timeoutMillis;

    @Version
    private Integer rev;
}
