package com.yaocode.sts.auth.domain.enums;

import lombok.Getter;

/**
 * 客户端类型
 */
@Getter
public enum ClientTypeEnums {

    ADMIN(0, "管理后台"),
    WEB(1, "Web端"),
    MOBILE(2, "移动端"),
    DESKTOP(3, "桌面端"),
    MINI_PROGRAM(4, "小程序");

    private final int code;
    private final String desc;

    ClientTypeEnums(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ClientTypeEnums fromCode(int code) {
        for (ClientTypeEnums type : values()) {
            if (type.code == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown client type: " + code);
    }

}
