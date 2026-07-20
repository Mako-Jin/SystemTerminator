package com.yaocode.sts.components.flow.core.engine.parser.error;

import com.yaocode.sts.components.flow.core.engine.parser.enums.ErrorSeverityEnums;
import lombok.Builder;
import lombok.Data;
import org.w3c.dom.Element;

/**
 * 解析错误
 *
 * @author Process Engine Team
 */
@Data
@Builder
public class ParseError {

    /**
     * 错误ID
     */
    private String id;

    /**
     * 错误名称
     */
    private String name;

    /**
     * 错误编码
     */
    private String errorCode;

    /**
     * 严重程度
     */
    @Builder.Default
    private ErrorSeverityEnums severity = ErrorSeverityEnums.ERROR;

    /**
     * 错误消息
     */
    private String message;

    /**
     * 关联的XML元素
     */
    private Element element;

    /**
     * 异常原因
     */
    private Throwable cause;

    /**
     * 行号
     */
    private int lineNumber;

    /**
     * 列号
     */
    private int columnNumber;

    /**
     * 快速创建错误
     */
    public static ParseError of(String message) {
        return ParseError.builder()
                .message(message)
                .severity(ErrorSeverityEnums.ERROR)
                .build();
    }

    /**
     * 快速创建错误
     */
    public static ParseError of(String message, Element element) {
        return ParseError.builder()
                .message(message)
                .severity(ErrorSeverityEnums.ERROR)
                .element(element)
                .build();
    }

    /**
     * 快速创建致命错误
     */
    public static ParseError fatal(String message, Throwable cause) {
        return ParseError.builder()
                .message(message)
                .severity(ErrorSeverityEnums.FATAL)
                .cause(cause)
                .build();
    }
}
