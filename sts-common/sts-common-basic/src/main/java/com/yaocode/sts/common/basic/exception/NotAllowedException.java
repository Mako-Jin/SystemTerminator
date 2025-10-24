package com.yaocode.sts.common.basic.exception;

/**
 * 不允许的操作异常
 * @author: Jin-LiangBo
 * @date: 2025年10月22日 23:06
 */
public class NotAllowedException extends RuntimeException {
    public NotAllowedException(String message) {
        super(message);
    }
}
