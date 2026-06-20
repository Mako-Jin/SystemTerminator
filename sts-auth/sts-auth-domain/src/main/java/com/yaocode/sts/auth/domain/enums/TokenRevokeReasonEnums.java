package com.yaocode.sts.auth.domain.enums;

import lombok.Getter;

/**
 * Token 撤销原因
 */
@Getter
public enum TokenRevokeReasonEnums {

    LOGOUT("LOGOUT", "用户退出"),
    PASSWORD_CHANGE("PASSWORD_CHANGE", "密码变更"),
    REPLACED("REPLACED", "被新Token替换"),
    SECURITY_BREACH("SECURITY_BREACH", "安全事件"),
    ADMIN_REVOKE("ADMIN_REVOKE", "管理员撤销"),
    USER_LOCKED("USER_LOCKED", "用户被锁定");

    private final String code;
    private final String desc;

    TokenRevokeReasonEnums(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static TokenRevokeReasonEnums fromCode(String code) {
        for (TokenRevokeReasonEnums reason : values()) {
            if (reason.code.equals(code)) {
                return reason;
            }
        }
        throw new IllegalArgumentException("Unknown token revoke reason: " + code);
    }

}
