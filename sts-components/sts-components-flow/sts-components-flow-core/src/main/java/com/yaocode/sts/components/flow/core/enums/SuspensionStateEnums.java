package com.yaocode.sts.components.flow.core.enums;

import lombok.Getter;

/**
 * 挂起状态（通用）
 */
@Getter
public enum SuspensionStateEnums {

    ACTIVE(1, "激活"),
    SUSPENDED(2, "挂起");

    private final int code;
    private final String description;

    SuspensionStateEnums(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static SuspensionStateEnums fromCode(int code) {
        for (SuspensionStateEnums state : values()) {
            if (state.code == code) return state;
        }
        return null;
    }

    public boolean isActive() { return this == ACTIVE; }
    public boolean isSuspended() { return this == SUSPENDED; }
}
