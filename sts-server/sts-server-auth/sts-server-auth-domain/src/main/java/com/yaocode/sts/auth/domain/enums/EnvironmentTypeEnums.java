package com.yaocode.sts.auth.domain.enums;

import lombok.Getter;

/**
 * 环境标识
 */
@Getter
public enum EnvironmentTypeEnums {

    DEVELOPMENT(1, "development"),
    STAGING(2, "staging"),
    PRODUCTION(3, "production");

    private final int code;
    private final String desc;

    EnvironmentTypeEnums(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static EnvironmentTypeEnums fromCode(int code) {
        for (EnvironmentTypeEnums env : values()) {
            if (env.code == code) {
                return env;
            }
        }
        throw new IllegalArgumentException("Unknown environment: " + code);
    }

}
