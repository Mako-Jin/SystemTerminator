package com.yaocode.sts.auth.domain.enums;

import lombok.Getter;

/**
 * 登出原因
 */
@Getter
public enum LogoutReasonEnums {

    SELF(1, "主动退出"),
    TIMEOUT(2, "超时"),
    KICKED(3, "被踢下线"),
    PASSWORD_CHANGED(4, "密码变更");

    private final int code;
    private final String desc;

    LogoutReasonEnums(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static LogoutReasonEnums fromCode(int code) {
        for (LogoutReasonEnums reason : values()) {
            if (reason.code == code) {
                return reason;
            }
        }
        throw new IllegalArgumentException("Unknown logout reason: " + code);
    }

}
