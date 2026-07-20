package com.yaocode.sts.components.flow.core.exception;

/**
 * 流程实例未找到异常
 */
public class FlowInstanceNotFoundException extends FlowException {
    public FlowInstanceNotFoundException(String s) {
        super(s);
    }

    public FlowInstanceNotFoundException(String s, Throwable e) {
        super(s, e);
    }
}
