package com.yaocode.sts.auth.domain.enums;

import lombok.Getter;

/**
 * 验证码触发条件
 */
@Getter
public enum CaptchaTriggerEnums {

    ALWAYS(1, "始终显示"),
    FAILED(2, "登录失败后显示"),
    SENSITIVE(3, "敏感操作时显示");

    private final int code;
    private final String desc;

    CaptchaTriggerEnums(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static CaptchaTriggerEnums fromCode(int code) {
        for (CaptchaTriggerEnums trigger : values()) {
            if (trigger.code == code) {
                return trigger;
            }
        }
        throw new IllegalArgumentException("Unknown captcha trigger: " + code);
    }

}
