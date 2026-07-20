package com.yaocode.sts.components.flow.core.exception;

/**
 * 流程定义未找到异常
 */
public class FlowDefinitionNotFoundException extends FlowException {
    public FlowDefinitionNotFoundException(String s) {
        super(s);
    }

    public FlowDefinitionNotFoundException(String s, Throwable e) {
        super(s, e);
    }
}
