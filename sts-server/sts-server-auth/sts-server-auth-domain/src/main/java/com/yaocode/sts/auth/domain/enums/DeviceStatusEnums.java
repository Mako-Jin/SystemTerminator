package com.yaocode.sts.auth.domain.enums;

import lombok.Getter;

/**
 * 设备状态
 */
@Getter
public enum DeviceStatusEnums {

    ACTIVE(1, "活跃"),
    INACTIVE(2, "非活跃"),
    BLOCKED(3, "已封禁");

    private final int code;
    private final String desc;

    DeviceStatusEnums(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static DeviceStatusEnums fromCode(int code) {
        for (DeviceStatusEnums status : values()) {
            if (status.code == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown device status: " + code);
    }

    public boolean isValid() {
        return this == ACTIVE || this == INACTIVE;
    }

    public boolean isBlocked() {
        return this == BLOCKED;
    }

}
