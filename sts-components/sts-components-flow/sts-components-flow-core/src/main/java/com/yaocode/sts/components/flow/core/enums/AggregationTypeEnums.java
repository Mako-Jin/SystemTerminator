package com.yaocode.sts.components.flow.core.enums;


import lombok.Getter;

/**
 * 聚合类型
 */
@Getter
public enum AggregationTypeEnums {

    RAW(1, "原始值"),
    SUM(2, "总和"),
    AVG(3, "平均值"),
    MAX(4, "最大值"),
    MIN(5, "最小值"),
    PERCENTILE(6, "百分位数");

    private final int code;
    private final String description;

    AggregationTypeEnums(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static AggregationTypeEnums fromCode(int code) {
        for (AggregationTypeEnums type : values()) {
            if (type.code == code) return type;
        }
        return null;
    }
}
