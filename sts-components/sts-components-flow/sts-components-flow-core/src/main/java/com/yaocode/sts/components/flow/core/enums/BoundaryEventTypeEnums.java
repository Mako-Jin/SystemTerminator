package com.yaocode.sts.components.flow.core.enums;


import lombok.Getter;

/**
 * 边界事件类型
 */
@Getter
public enum BoundaryEventTypeEnums {

    TIMER(1, "定时器"),
    ERROR(2, "错误"),
    MESSAGE(3, "消息"),
    SIGNAL(4, "信号"),
    COMPENSATION(5, "补偿"),
    CANCEL(6, "取消");

    private final int code;
    private final String description;

    BoundaryEventTypeEnums(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static BoundaryEventTypeEnums fromCode(int code) {
        for (BoundaryEventTypeEnums type : values()) {
            if (type.code == code) return type;
        }
        return null;
    }
}
