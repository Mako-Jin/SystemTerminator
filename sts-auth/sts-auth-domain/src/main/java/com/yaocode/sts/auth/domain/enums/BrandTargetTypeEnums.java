package com.yaocode.sts.auth.domain.enums;

import lombok.Getter;

/**
 * 品牌配置关联目标类型
 */
@Getter
public enum BrandTargetTypeEnums {

    TENANT("TENANT", "租户"),
    COMPANY("COMPANY", "公司"),
    PLATFORM("PLATFORM", "平台");

    private final String code;
    private final String desc;

    BrandTargetTypeEnums(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static BrandTargetTypeEnums fromCode(String code) {
        for (BrandTargetTypeEnums type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown brand target type: " + code);
    }
}
