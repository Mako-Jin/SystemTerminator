package com.yaocode.sts.components.flow.core.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 作业（Core 领域模型）
 *
 * 对应表: flow_tbl_job
 */
@Data
@Builder
public class Job {

    private String jobId;
    private Integer jobType;          // 1=定时器,2=异步延续,3=异步消息,4=重试作业,5=批量操作
    private String processInstanceId;
    private String executionId;
    private String definitionId;
    private String definitionKey;
    private String nodeKey;
    private String handlerType;
    private Map<String, Object> handlerConfig;
    private Integer retriesRemaining;
    private Integer maxRetries;
    private Integer retryBackoffMillis;
    private String lastFailureMessage;
    private String lastFailureStackFileId;
    private LocalDateTime dueDate;
    private LocalDateTime executedTime;
    private LocalDateTime lockExpireTime;
    private Integer status;           // 1=等待,2=执行中,3=成功,4=失败,5=取消,6=挂起
    private Integer suspensionState;  // 1=激活,2=挂起
    private String lockOwner;
    private String lockOwnerHost;
    private Integer priority;
    private Boolean exclusive;
    private Map<String, Object> repeatConfig;
    private Long repeatOffsetMillis;
    private String lastFailureLogId;
    private String jobDefinitionId;
    private Integer rev;
    private String tenantId;
}

