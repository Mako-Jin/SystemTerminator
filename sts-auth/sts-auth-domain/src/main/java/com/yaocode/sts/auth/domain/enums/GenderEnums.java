package com.yaocode.sts.auth.domain.enums;

import lombok.Getter;

/**
 * 性别
 */
@Getter
public enum GenderEnums {

    UNKNOWN(0, "未知"),
    MALE(1, "男"),
    FEMALE(2, "女");

    private final int code;
    private final String desc;

    GenderEnums(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static GenderEnums fromCode(int code) {
        for (GenderEnums gender : values()) {
            if (gender.code == code) {
                return gender;
            }
        }
        return UNKNOWN;
    }

}
