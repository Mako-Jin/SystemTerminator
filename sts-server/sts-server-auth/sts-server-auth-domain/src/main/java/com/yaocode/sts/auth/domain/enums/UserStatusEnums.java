package com.yaocode.sts.auth.domain.enums;

import lombok.Getter;

/**
 * 用户状态
 */
@Getter
public enum UserStatusEnums {

    INACTIVE(0, "未激活"),
    ACTIVE(1, "已激活"),
    LOCKED(2, "已锁定");

    private final int code;
    private final String desc;

    UserStatusEnums(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static UserStatusEnums fromCode(int code) {
        for (UserStatusEnums status : values()) {
            if (status.code == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown user status: " + code);
    }

    public boolean canLogin() {
        return this == ACTIVE;
    }

    public boolean isLocked() {
        return this == LOCKED;
    }

    public boolean isActive() {
        return this == ACTIVE;
    }
}
