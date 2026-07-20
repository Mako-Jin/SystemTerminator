package com.yaocode.sts.components.flow.core.model;

import lombok.Builder;
import lombok.Data;

/**
 * 错误定义
 *
 * <p>用于错误事件的错误定义
 *
 * @author Process Engine Team
 */
@Data
@Builder
public class ErrorDefinition {

    /**
     * 错误ID
     */
    private String id;

    /**
     * 错误名称
     */
    private String name;

    /**
     * 错误代码
     */
    private String errorCode;

    /**
     * 错误结构（关联的ItemDefinition）
     */
    private String structureRef;

    /**
     * 错误描述
     */
    private String description;
}
