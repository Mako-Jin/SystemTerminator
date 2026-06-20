package com.yaocode.sts.auth.domain.enums;

import lombok.Getter;

/**
 * 主题
 */
@Getter
public enum ThemeEnums {

    LIGHT(1, "浅色"),
    DARK(2, "深色"),
    AUTO(3, "跟随系统");

    private final int code;
    private final String desc;

    ThemeEnums(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ThemeEnums fromCode(int code) {
        for (ThemeEnums theme : values()) {
            if (theme.code == code) {
                return theme;
            }
        }
        return AUTO;
    }

}
