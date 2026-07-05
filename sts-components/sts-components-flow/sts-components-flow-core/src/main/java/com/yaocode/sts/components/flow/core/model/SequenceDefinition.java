package com.yaocode.sts.components.flow.core.model;

import lombok.Builder;
import lombok.Data;

/**
 * 连线定义（Core 领域模型）
 */
@Data
@Builder
public class SequenceDefinition {

    private String sequenceId;
    private String sequenceKey;
    private String sequenceName;
    private String sourceNodeKey;
    private String targetNodeKey;
    private String conditionExpression;
    private Boolean isDefault;
}
