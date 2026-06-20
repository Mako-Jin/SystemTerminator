package com.yaocode.sts.auth.domain.enums;

import lombok.Getter;

/**
 * 联系方式类型
 */
@Getter
public enum ContactTypeEnums {

    MOBILE(1, "手机"),
    EMAIL(2, "邮箱"),
    PHONE(3, "座机"),
    ;

    private final int code;
    private final String desc;

    ContactTypeEnums(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ContactTypeEnums fromCode(int code) {
        for (ContactTypeEnums type : values()) {
            if (type.code == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown contact type: " + code);
    }

}
