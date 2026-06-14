package com.yaocode.sts.components.flow.core.enums;

import lombok.Getter;

/**
 * 实体类型
 */
@Getter
public enum EntityTypeEnums {

    PROCESS_INSTANCE(1, "流程实例"),
    TASK(2, "任务"),
    VARIABLE(3, "变量"),
    IDENTITY_LINK(4, "身份关联"),
    JOB(5, "作业"),
    PROCESS_DEFINITION(6, "流程定义"),
    DEPLOYMENT(7, "部署"),
    FILTER(8, "过滤器"),
    EXTERNAL_TASK(9, "外部任务"),
    BATCH(10, "批次");

    private final int code;
    private final String description;

    EntityTypeEnums(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static EntityTypeEnums fromCode(int code) {
        for (EntityTypeEnums type : values()) {
            if (type.code == code) return type;
        }
        return null;
    }
}
