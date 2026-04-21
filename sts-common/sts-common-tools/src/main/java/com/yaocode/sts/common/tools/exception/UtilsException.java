package com.yaocode.sts.common.tools.exception;

import com.yaocode.sts.common.basic.exception.BusinessException;

/**
 * 规则类基础异常
 * @author: Jin-LiangBo
 * @date: 2026年04月20日 19:26
 */
public class UtilsException extends BusinessException {

    public UtilsException() {
    }

    public UtilsException(String message) {
        super(message);
    }

    public UtilsException(String message, Throwable cause) {
        super(message, cause);
    }

    public UtilsException(Throwable cause) {
        super(cause);
    }

    public UtilsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
