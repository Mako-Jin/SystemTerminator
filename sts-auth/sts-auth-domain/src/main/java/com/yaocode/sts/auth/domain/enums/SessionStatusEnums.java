package com.yaocode.sts.auth.domain.enums;

import lombok.Getter;

/**
 * 会话状态枚举
 * @author: Jin-LiangBo
 * @date: 2026-06-28
 */
@Getter
public enum SessionStatusEnums {

    ACTIVE(1, "活跃"),
    IDLE(2, "空闲"),
    FORCE_LOGOUT(3, "强制下线"),
    EXPIRED(4, "已过期");

    private final int code;
    private final String desc;

    SessionStatusEnums(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static SessionStatusEnums fromCode(int code) {
        for (SessionStatusEnums status : values()) {
            if (status.code == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown session status: " + code);
    }

    public boolean isActive() {
        return this == ACTIVE || this == IDLE;
    }

    public boolean isTerminated() {
        return this == FORCE_LOGOUT || this == EXPIRED;
    }
}
