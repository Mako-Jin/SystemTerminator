package com.yaocode.sts.components.flow.core.enums;


import lombok.Getter;

/**
 * 作业类型
 */
@Getter
public enum JobTypeEnums {

    TIMER(1, "定时器"),
    ASYNC_CONTINUATION(2, "异步延续"),
    ASYNC_MESSAGE(3, "异步消息"),
    RETRY(4, "重试作业"),
    BATCH(5, "批量操作");

    private final int code;
    private final String description;

    JobTypeEnums(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static JobTypeEnums fromCode(int code) {
        for (JobTypeEnums type : values()) {
            if (type.code == code) return type;
        }
        return null;
    }
}
