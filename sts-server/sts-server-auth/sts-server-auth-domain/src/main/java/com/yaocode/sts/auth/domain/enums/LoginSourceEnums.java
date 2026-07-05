package com.yaocode.sts.auth.domain.enums;

import lombok.Getter;

/**
 * 登录来源
 */
@Getter
public enum LoginSourceEnums {

    WEB(1, "Web"),
    MOBILE(2, "移动端"),
    DESKTOP(3, "桌面端"),
    MINI_PROGRAM(4, "小程序"),
    THIRD_PARTY(5, "第三方"),
    OTHER(99, "其他");

    private final int code;
    private final String desc;

    LoginSourceEnums(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static LoginSourceEnums fromCode(int code) {
        for (LoginSourceEnums source : values()) {
            if (source.code == code) {
                return source;
            }
        }
        return OTHER;
    }

}
