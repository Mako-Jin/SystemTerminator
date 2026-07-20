package com.yaocode.sts.components.flow.core.model;

import lombok.Builder;
import lombok.Data;

/**
 * 消息定义
 *
 * <p>用于消息事件的消息定义
 *
 * @author Process Engine Team
 */
@Data
@Builder
public class MessageDefinition {

    /**
     * 消息ID
     */
    private String id;

    /**
     * 消息名称
     */
    private String name;

    /**
     * 消息引用（关联的ItemDefinition）
     */
    private String itemRef;

    /**
     * 消息结构（XML Schema 或 Java 类）
     */
    private String structureRef;

    /**
     * 消息描述
     */
    private String description;
}
