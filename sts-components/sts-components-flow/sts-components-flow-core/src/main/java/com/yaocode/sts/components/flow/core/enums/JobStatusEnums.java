package com.yaocode.sts.components.flow.core.enums;


import lombok.Getter;

/**
 * 作业状态
 */
@Getter
public enum JobStatusEnums {

    WAITING(1, "等待"),
    RUNNING(2, "执行中"),
    SUCCESS(3, "成功"),
    FAILED(4, "失败"),
    CANCELLED(5, "取消"),
    SUSPENDED(6, "挂起");

    private final int code;
    private final String description;

    JobStatusEnums(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static JobStatusEnums fromCode(int code) {
        for (JobStatusEnums status : values()) {
            if (status.code == code) return status;
        }
        return null;
    }

    public boolean isFinished() {
        return this == SUCCESS || this == FAILED || this == CANCELLED;
    }
}