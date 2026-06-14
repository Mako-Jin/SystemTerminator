package com.yaocode.sts.components.flow.core.enums;

import lombok.Getter;


/**
 * 外部任务状态
 */
@Getter
public enum ExternalTaskStatusEnums {

    WAITING(1, "等待"),
    LOCKED(2, "已锁定"),
    COMPLETED(3, "已完成"),
    FAILED(4, "失败"),
    CANCELLED(5, "取消");

    private final int code;
    private final String description;

    ExternalTaskStatusEnums(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static ExternalTaskStatusEnums fromCode(int code) {
        for (ExternalTaskStatusEnums status : values()) {
            if (status.code == code) return status;
        }
        return null;
    }
}
