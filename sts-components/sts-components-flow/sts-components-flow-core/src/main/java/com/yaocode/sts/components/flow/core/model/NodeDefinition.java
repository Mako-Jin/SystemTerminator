package com.yaocode.sts.components.flow.core.model;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * 节点定义（Core 领域模型）
 */
@Data
@Builder
public class NodeDefinition {

    private String nodeId;
    private String nodeKey;
    private String nodeName;
    private Integer nodeType;        // 对应 NodeTypeEnums
    private Integer nodeOrder;
    private Map<String, Object> configuration;
    private Integer timeLimit;
    private Integer timeLimitAction;
    private Boolean isMultiInstance;
    private Integer multiInstanceType;
    private Map<String, Object> multiInstanceConfig;
    private Boolean asyncBefore;
    private Boolean asyncAfter;
    private Boolean exclusive;
    private String eventSubType;
    private Map<String, Object> eventConfig;
}
