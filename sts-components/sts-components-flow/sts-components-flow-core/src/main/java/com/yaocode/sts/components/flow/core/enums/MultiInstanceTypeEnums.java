package com.yaocode.sts.components.flow.core.enums;


import lombok.Getter;

/**
 * 多实例类型
 */
@Getter
public enum MultiInstanceTypeEnums {

    SEQUENTIAL(1, "顺序"),
    PARALLEL(2, "并行");

    private final int code;
    private final String description;

    MultiInstanceTypeEnums(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static MultiInstanceTypeEnums fromCode(int code) {
        for (MultiInstanceTypeEnums type : values()) {
            if (type.code == code) return type;
        }
        return null;
    }
}
