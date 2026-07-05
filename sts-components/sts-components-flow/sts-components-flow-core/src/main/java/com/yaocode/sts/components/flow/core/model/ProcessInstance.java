package com.yaocode.sts.components.flow.core.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 流程实例（Core 领域模型）
 */
@Data
@Builder
public class ProcessInstance {

    private String processInstanceId;
    private String businessKey;
    private String processId;
    private String processKey;
    private Integer processVersion;
    private String processName;
    private Integer status;          // 1=运行中,2=挂起,3=已完成,4=已终止,5=异常
    private Integer suspensionState; // 1=激活,2=挂起
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long durationMilliseconds;
    private String startUserId;
    private String startUserName;
    private Map<String, Object> variables;
    private List<String> currentNodeKeys;
}
