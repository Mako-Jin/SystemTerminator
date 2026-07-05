package com.yaocode.sts.auth.domain.enums;

import lombok.Getter;

/**
 * 婚姻状态
 */
@Getter
public enum MaritalStatusEnums {

    UNKNOWN(0, "未知"),
    SINGLE(1, "未婚"),
    MARRIED(2, "已婚"),
    DIVORCED(3, "离异"),
    WIDOWED(4, "丧偶");

    private final int code;
    private final String desc;

    MaritalStatusEnums(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static MaritalStatusEnums fromCode(int code) {
        for (MaritalStatusEnums status : values()) {
            if (status.code == code) {
                return status;
            }
        }
        return UNKNOWN;
    }

}
