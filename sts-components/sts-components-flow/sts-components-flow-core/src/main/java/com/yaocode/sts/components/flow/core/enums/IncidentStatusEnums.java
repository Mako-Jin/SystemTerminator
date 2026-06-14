package com.yaocode.sts.components.flow.core.enums;


import lombok.Getter;

/**
 * 异常状态
 */
@Getter
public enum IncidentStatusEnums {

    UNRESOLVED(1, "未解决"),
    RESOLVED(2, "已解决"),
    IGNORED(3, "已忽略"),
    ESCALATED(4, "已升级");

    private final int code;
    private final String description;

    IncidentStatusEnums(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static IncidentStatusEnums fromCode(int code) {
        for (IncidentStatusEnums status : values()) {
            if (status.code == code) return status;
        }
        return null;
    }
}
