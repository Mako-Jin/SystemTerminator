package com.yaocode.sts.components.flow.core.engine.parser.error;

import lombok.Builder;
import lombok.Data;
import org.w3c.dom.Element;

/**
 * 解析警告
 *
 * @author Process Engine Team
 */
@Data
@Builder
public class ParseWarning {

    /**
     * 警告消息
     */
    private String message;

    /**
     * 警告编码
     */
    private String warningCode;

    /**
     * 关联的XML元素
     */
    private Element element;

    /**
     * 行号
     */
    private int lineNumber;

    /**
     * 列号
     */
    private int columnNumber;

    /**
     * 快速创建警告
     */
    public static ParseWarning of(String message) {
        return ParseWarning.builder()
                .message(message)
                .build();
    }

    /**
     * 快速创建警告
     */
    public static ParseWarning of(String message, Element element) {
        return ParseWarning.builder()
                .message(message)
                .element(element)
                .build();
    }
}