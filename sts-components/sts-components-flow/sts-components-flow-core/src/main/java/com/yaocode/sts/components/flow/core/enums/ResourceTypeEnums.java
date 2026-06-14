package com.yaocode.sts.components.flow.core.enums;

import lombok.Getter;

/**
 * 资源类型（过滤器）
 */
@Getter
public enum ResourceTypeEnums {

    TASK(1, "任务"),
    PROCESS_INSTANCE(2, "流程实例"),
    EXTERNAL_TASK(3, "外部任务"),
    JOB(4, "作业"),
    DECISION_INSTANCE(5, "决策实例");

    private final int code;
    private final String description;

    ResourceTypeEnums(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static ResourceTypeEnums fromCode(int code) {
        for (ResourceTypeEnums type : values()) {
            if (type.code == code) return type;
        }
        return null;
    }
}
