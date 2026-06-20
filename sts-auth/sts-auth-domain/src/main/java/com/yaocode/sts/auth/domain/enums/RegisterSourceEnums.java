package com.yaocode.sts.auth.domain.enums;

import lombok.Getter;

/**
 * 用户新增方式
 * @author: Jin-LiangBo
 * @date: 2025年10月22日 22:54
 */
@Getter
public enum RegisterSourceEnums {

    REGISTER(1, "自助注册"),
    ADMIN(2, "管理员创建"),
    SOCIAL(3, "社交登录"),
    IMPORT(4, "同步导入"),
    ;

    private final Integer code;

    private final String type;

    RegisterSourceEnums(Integer code, String type) {
        this.code = code;
        this.type = type;
    }

    public static RegisterSourceEnums fromCode(int code) {
        for (RegisterSourceEnums source : values()) {
            if (source.code == code) {
                return source;
            }
        }
        throw new IllegalArgumentException("Unknown register source: " + code);
    }
}
