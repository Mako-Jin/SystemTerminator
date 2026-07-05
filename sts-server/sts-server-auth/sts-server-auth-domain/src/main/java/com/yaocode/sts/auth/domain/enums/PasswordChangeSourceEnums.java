package com.yaocode.sts.auth.domain.enums;

import lombok.Getter;

/**
 * 密码变更来源
 */
@Getter
public enum PasswordChangeSourceEnums {

    SELF(1, "自行修改"),
    ADMIN(2, "管理员重置"),
    SYSTEM(3, "系统强制");

    private final int code;
    private final String desc;

    PasswordChangeSourceEnums(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static PasswordChangeSourceEnums fromCode(int code) {
        for (PasswordChangeSourceEnums source : values()) {
            if (source.code == code) {
                return source;
            }
        }
        throw new IllegalArgumentException("Unknown password change source: " + code);
    }

}
