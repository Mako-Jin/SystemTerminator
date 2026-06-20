package com.yaocode.sts.auth.domain.enums;

import lombok.Getter;

/**
 * 验证码类型
 */
@Getter
public enum CaptchaTypeEnums {

    MATH(1, "数学计算"),
    TEXT(2, "文字验证"),
    SLIDER(3, "滑块验证");

    private final int code;
    private final String desc;

    CaptchaTypeEnums(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static CaptchaTypeEnums fromCode(int code) {
        for (CaptchaTypeEnums type : values()) {
            if (type.code == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown captcha type: " + code);
    }

}
