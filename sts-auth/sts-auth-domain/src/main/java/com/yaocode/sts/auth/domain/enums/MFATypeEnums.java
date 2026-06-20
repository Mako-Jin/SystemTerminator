package com.yaocode.sts.auth.domain.enums;

import lombok.Getter;

/**
 * 多因素认证类型
 */
@Getter
public enum MFATypeEnums {

    TOTP(1, "TOTP验证器"),
    SMS(2, "短信验证码"),
    EMAIL(3, "邮箱验证码");

    private final int code;
    private final String desc;

    MFATypeEnums(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static MFATypeEnums fromCode(int code) {
        for (MFATypeEnums type : values()) {
            if (type.code == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown MFA type: " + code);
    }

}
