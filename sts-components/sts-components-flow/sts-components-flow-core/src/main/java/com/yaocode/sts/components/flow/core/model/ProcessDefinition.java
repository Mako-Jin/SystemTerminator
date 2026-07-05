package com.yaocode.sts.components.flow.core.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 流程定义（Core 领域模型）
 * 从 Entity 转换而来，供引擎内部使用
 */
@Data
@Builder
public class ProcessDefinition {

    private String processId;
    private String processKey;
    private Integer version;
    private Boolean isLatest;
    private String name;
    private String description;
    private String category;
    private Integer status;          // 1=启用,0=禁用,2=挂起
    private Boolean startable;
    private List<NodeDefinition> nodes;
    private List<SequenceDefinition> sequences;
}
