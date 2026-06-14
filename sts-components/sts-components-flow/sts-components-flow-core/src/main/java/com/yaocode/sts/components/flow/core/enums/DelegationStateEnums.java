package com.yaocode.sts.components.flow.core.enums;

import lombok.Getter;

/**
 * 委派状态
 */
@Getter
public enum DelegationStateEnums {

    PENDING(1, "待委派"),
    DELEGATED(2, "已委派"),
    RESOLVED(3, "已解决");

    private final int code;
    private final String description;

    DelegationStateEnums(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static DelegationStateEnums fromCode(int code) {
        for (DelegationStateEnums state : values()) {
            if (state.code == code) return state;
        }
        return null;
    }
}
