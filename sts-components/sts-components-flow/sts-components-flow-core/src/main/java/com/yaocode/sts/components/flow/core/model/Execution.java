package com.yaocode.sts.components.flow.core.model;

import lombok.Builder;
import lombok.Data;

/**
 * 执行路径（Core 领域模型）
 */
@Data
@Builder
public class Execution {

    private String executionId;
    private String processInstanceId;
    private String parentExecutionId;
    private String superExecutionId;
    private String nodeKey;
    private Boolean isActive;
    private Boolean isConcurrent;
    private Boolean isScope;
    private Boolean isEventScope;
    private Integer suspensionState;
}
