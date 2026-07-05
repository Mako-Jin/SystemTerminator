package com.yaocode.sts.auth.domain.exception;

/**
 * 角色不存在异常
 * @author: Jin-LiangBo
 * @date: 2026年04月20日 10:42
 */
public class RoleNotFoundException extends AuthException {

    public RoleNotFoundException(String message) {
        super(message);
    }

}
