package com.yaocode.sts.auth.domain.enums;

import lombok.Getter;

/**
 * 权限策略类型
 */
@Getter
public enum StrategyTypeEnums {

    ROLE_BASED(1, "角色型"),
    GROUP_BASED(2, "组型"),
    USER_BASED(3, "用户型");

    private final int code;
    private final String desc;

    StrategyTypeEnums(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static StrategyTypeEnums fromCode(int code) {
        for (StrategyTypeEnums type : values()) {
            if (type.code == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown strategy type: " + code);
    }
}