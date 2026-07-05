package com.yaocode.sts.auth.domain.enums;

import lombok.Getter;

/**
 * 证件类型
 */
@Getter
public enum CredentialTypeEnums {

    ID_CARD(1, "身份证"),
    PASSPORT(2, "护照"),
    STUDENT_CARD(3, "学生证"),
    MILITARY_CARD(4, "军人证"),
    DRIVING_LICENSE(5, "驾驶证"),
    OTHER(99, "其他");

    private final int code;
    private final String desc;

    CredentialTypeEnums(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static CredentialTypeEnums fromCode(int code) {
        for (CredentialTypeEnums type : values()) {
            if (type.code == code) {
                return type;
            }
        }
        return OTHER;
    }

}
