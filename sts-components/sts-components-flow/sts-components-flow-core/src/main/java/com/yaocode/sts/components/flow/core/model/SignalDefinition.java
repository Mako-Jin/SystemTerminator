package com.yaocode.sts.components.flow.core.model;

import lombok.Builder;
import lombok.Data;

/**
 * 信号定义
 *
 * <p>用于信号事件的信号定义
 *
 * @author Process Engine Team
 */
@Data
@Builder
public class SignalDefinition {

    /**
     * 信号ID
     */
    private String id;

    /**
     * 信号名称
     */
    private String name;

    /**
     * 信号结构（XML Schema 或 Java 类）
     */
    private String structureRef;

    /**
     * 信号描述
     */
    private String description;

    /**
     * 信号作用域
     * <p>可选值：global, processInstance
     */
    @Builder.Default
    private String scope = "global";
}