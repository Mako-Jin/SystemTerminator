package com.yaocode.sts.components.flow.core.enums;


import lombok.Getter;

/**
 * 超时动作
 */
@Getter
public enum TimeLimitActionEnums {

    NOTIFY(1, "提醒"),
    ESCALATE(2, "升级"),
    AUTO_COMPLETE(3, "自动通过"),
    AUTO_REJECT(4, "自动驳回");

    private final int code;
    private final String description;

    TimeLimitActionEnums(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static TimeLimitActionEnums fromCode(int code) {
        for (TimeLimitActionEnums action : values()) {
            if (action.code == code) return action;
        }
        return null;
    }
}
