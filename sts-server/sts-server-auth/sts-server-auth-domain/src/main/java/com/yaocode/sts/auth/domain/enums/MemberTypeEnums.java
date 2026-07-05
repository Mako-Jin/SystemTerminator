package com.yaocode.sts.auth.domain.enums;

import lombok.Getter;

/**
 * 成员类型（角色/用户组的成员）
 */
@Getter
public enum MemberTypeEnums {

    USER(1, "用户"),
    GROUP(2, "用户组");

    private final int code;
    private final String desc;

    MemberTypeEnums(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static MemberTypeEnums fromCode(int code) {
        for (MemberTypeEnums type : values()) {
            if (type.code == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown member type: " + code);
    }

    public boolean isUser() {
        return this == USER;
    }

    public boolean isGroup() {
        return this == GROUP;
    }

}
