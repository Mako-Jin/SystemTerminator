package com.yaocode.sts.components.flow.core.enums;


import lombok.Getter;

/**
 * 异常类型
 */
@Getter
public enum IncidentTypeEnums {

    JOB_FAILED(1, "作业失败"),
    TIMER_FAILED(2, "定时器失败"),
    TASK_TIMEOUT(3, "任务超时"),
    EXTERNAL_TASK_FAILED(4, "外部任务失败"),
    PROCESS_EXECUTION_ERROR(5, "流程执行异常"),
    LOCK_TIMEOUT(6, "锁定超时"),
    CALL_ACTIVITY_FAILED(7, "调用活动失败");

    private final int code;
    private final String description;

    IncidentTypeEnums(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static IncidentTypeEnums fromCode(int code) {
        for (IncidentTypeEnums type : values()) {
            if (type.code == code) return type;
        }
        return null;
    }
}
