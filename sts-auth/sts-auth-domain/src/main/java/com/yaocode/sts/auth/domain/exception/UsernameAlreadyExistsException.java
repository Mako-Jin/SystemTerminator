package com.yaocode.sts.auth.domain.exception;

/**
 * 用户名已存在异常
 * @author: Jin-LiangBo
 * @date: 2026年04月20日 10:42
 */
public class UsernameAlreadyExistsException extends AuthException {
    public UsernameAlreadyExistsException(String message) {
        super(message);
    }
}
