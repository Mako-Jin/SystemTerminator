package com.yaocode.sts.auth.domain.enums;

import lombok.Getter;

/**
 * 实例类型
 */
@Getter
public enum InstanceTypeEnums {

    PROD(1, "生产环境"),
    TEST(2, "测试环境"),
    DEV(3, "开发环境"),
    PRE(4, "预发布环境");

    private final int code;
    private final String desc;

    InstanceTypeEnums(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static InstanceTypeEnums fromCode(int code) {
        for (InstanceTypeEnums type : values()) {
            if (type.code == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown instance type: " + code);
    }

    public boolean isProd() {
        return this == PROD;
    }

}
