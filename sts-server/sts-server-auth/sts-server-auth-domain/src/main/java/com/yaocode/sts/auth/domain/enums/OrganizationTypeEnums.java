package com.yaocode.sts.auth.domain.enums;

import lombok.Getter;

/**
 * 组织类型
 */
@Getter
public enum OrganizationTypeEnums {

    ENTITY(1, "实体组织"),
    VIRTUAL(2, "虚拟组织");

    private final int code;
    private final String desc;

    OrganizationTypeEnums(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static OrganizationTypeEnums fromCode(int code) {
        for (OrganizationTypeEnums type : values()) {
            if (type.code == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown organization type: " + code);
    }

}
