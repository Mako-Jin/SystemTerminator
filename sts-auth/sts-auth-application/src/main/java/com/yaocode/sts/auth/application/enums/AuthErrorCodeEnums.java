package com.yaocode.sts.auth.application.enums;

/**
 * 权限服务错误码枚举类
 * @author: Jin-LiangBo
 * @date: 2025年10月08日 17:53
 */
public enum AuthErrorCodeEnums {

    /**
     * 用户不存在
     */
    USER_NOT_FOUND("004000", "auth.user.not.found"),

    /**
     * 认证异常
     */
    AUTHENTICATION_ERROR("004010", "auth.authentication.error"),

    /**
     * 暂无权限
     */
    NO_PERMISSION_ERROR("004030", "auth.no.permission"),
    ;

    private final String code;

    private final String msg;

    AuthErrorCodeEnums(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
