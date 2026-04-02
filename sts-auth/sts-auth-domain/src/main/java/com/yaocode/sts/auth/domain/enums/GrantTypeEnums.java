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
    PASSWORD("password", "用户名密码认证"),
    ;

    /**
     * 授权类型值
     */
    private final String value;

    /**
     * 描述
     */
    private final String description;

    GrantTypeEnums(String value, String description) {
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
