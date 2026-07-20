package com.yaocode.sts.components.flow.core.exception;

/**
 * 无效的流程定义异常
 */
public class InvalidFlowDefinitionException extends FlowException {
    public InvalidFlowDefinitionException(String s) {
        super(s);
    }

    public InvalidFlowDefinitionException(String s, Throwable e) {
        super(s, e);
    }
}
