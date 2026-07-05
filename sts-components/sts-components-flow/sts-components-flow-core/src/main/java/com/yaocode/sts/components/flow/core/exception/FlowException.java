package com.yaocode.sts.components.flow.core.exception;

import com.yaocode.sts.common.basic.exception.BusinessException;


/**
 * 工作流异常
 */
public class FlowException extends BusinessException {
    public FlowException(String s) {
        super(s);
    }

    public FlowException(String s, Throwable e) {
        super(s, e);
    }

}
