package com.yaocode.sts.flow.core.exception;

/**
 * 任务未找到异常
 */
public class TaskNotFoundException extends FlowException {
    public TaskNotFoundException(String s) {
        super(s);
    }

    public TaskNotFoundException(String s, Throwable e) {
        super(s, e);
    }
}
