package com.yaocode.sts.auth.domain.enums;

import lombok.Getter;

/**
 * 认证方式
 */
@Getter
public enum AuthMethodEnums {

    PASSWORD(1, "密码"),
    SMS(2, "短信验证码"),
    TOTP(3, "TOTP双因素"),
    SOCIAL(4, "社交登录"),
    QR_CODE(5, "扫码登录"),
    SSO(6, "单点登录"),
    FINGERPRINT(7, "指纹识别"),
    FACE(8, "人脸识别"),
    CERTIFICATE(9, "证书认证");

    private final int code;
    private final String desc;

    AuthMethodEnums(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static AuthMethodEnums fromCode(int code) {
        for (AuthMethodEnums method : values()) {
            if (method.code == code) {
                return method;
            }
        }
        throw new IllegalArgumentException("Unknown auth method: " + code);
    }
}
