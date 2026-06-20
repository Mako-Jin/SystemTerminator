package com.yaocode.sts.auth.domain.enums;

import lombok.Getter;

/**
 * 认证类型枚举
 * 定义系统支持的所有认证方式
 * @author: Jin-LiangBo
 * @date: 2026年03月27日 16:42
 */
@Getter
public enum GrantTypeEnums {
    /**
     * 用户名密码认证
     */
    PASSWORD(1, "password", "资源拥有者密码"),
    AUTHORIZATION_CODE(2, "authorization_code", "授权码"),
    CLIENT_CREDENTIALS(3, "client_credentials", "客户端凭证"),
    REFRESH_TOKEN(4, "refresh_token", "刷新Token"),
    IMPLICIT(5, "implicit", "隐式授权"),          // OAuth2.0
    DEVICE_CODE(6, "device_code", "设备码"),
    ;

    private final Integer code;
    /**
     * 授权类型值
     */
    private final String value;

    /**
     * 描述
     */
    private final String description;

    GrantTypeEnums(Integer code, String value, String description) {
        this.code = code;
        this.value = value;
        this.description = description;
    }

    public static GrantTypeEnums of(String value) {
        for (GrantTypeEnums type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("不支持的认证类型: " + value);
    }

}
