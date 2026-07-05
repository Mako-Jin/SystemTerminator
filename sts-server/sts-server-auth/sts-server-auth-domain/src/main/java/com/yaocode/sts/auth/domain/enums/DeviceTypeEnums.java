package com.yaocode.sts.auth.domain.enums;

import lombok.Getter;

/**
 * 设备类型
 */
@Getter
public enum DeviceTypeEnums {

    IOS(1, "iOS"),
    ANDROID(2, "Android"),
    WEB(3, "Web"),
    WECHAT_MINI(4, "微信小程序"),
    DESKTOP(5, "桌面端"),
    OTHER(99, "其他");

    private final int code;
    private final String desc;

    DeviceTypeEnums(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static DeviceTypeEnums fromCode(int code) {
        for (DeviceTypeEnums type : values()) {
            if (type.code == code) {
                return type;
            }
        }
        return OTHER;
    }

    public boolean isMobile() {
        return this == IOS || this == ANDROID;
    }

}
