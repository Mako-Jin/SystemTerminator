package com.yaocode.sts.auth.application.exception;

import com.yaocode.sts.common.basic.exception.BusinessException;

/**
 * 认证异常
 * @author: Jin-LiangBo
 * @date: 2026年04月21日 15:10
 */
public class AuthException extends BusinessException {

    public AuthException() {
    }

    public AuthException(String message) {
        super(message);
    }

    public AuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthException(Throwable cause) {
        super(cause);
    }

    public AuthException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
