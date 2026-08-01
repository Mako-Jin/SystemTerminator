package com.yaocode.sts.common.basic.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 业务异常
 * @author: Jin-LiangBo
 * @date: 2026年04月20日 19:20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BusinessException extends RuntimeException {

    private String code;

    /**
     * 消息参数
     */
    private Object[] args;

    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(String message, Object... args) {
        super(message);
        this.args = args;
    }

    public BusinessException(String code, String message, Object... args) {
        super(message);
        this.code = code;
        this.args = args;
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
