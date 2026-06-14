package com.yaocode.sts.components.flow.core.enums;

import lombok.Getter;


/**
 * 计量类型
 */
@Getter
public enum MeterTypeEnums {

    COUNTER(1, "计数器"),
    TIMER(2, "计时器"),
    THROUGHPUT(3, "吞吐量"),
    ERROR_RATE(4, "错误率"),
    SATURATION(5, "饱和度");

    private final int code;
    private final String description;

    MeterTypeEnums(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static MeterTypeEnums fromCode(int code) {
        for (MeterTypeEnums type : values()) {
            if (type.code == code) return type;
        }
        return null;
    }
}
