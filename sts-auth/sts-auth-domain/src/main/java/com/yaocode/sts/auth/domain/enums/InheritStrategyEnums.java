package com.yaocode.sts.auth.domain.enums;

import lombok.Getter;

/**
 * 角色继承策略
 */
@Getter
public enum InheritStrategyEnums {

    NONE(0, "无继承"),
    FULL(1, "完全继承"),
    INCREMENTAL(2, "增量继承"),
    OVERRIDE(3, "覆盖继承");

    private final int code;
    private final String desc;

    InheritStrategyEnums(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static InheritStrategyEnums fromCode(int code) {
        for (InheritStrategyEnums strategy : values()) {
            if (strategy.code == code) {
                return strategy;
            }
        }
        return NONE;
    }

}
