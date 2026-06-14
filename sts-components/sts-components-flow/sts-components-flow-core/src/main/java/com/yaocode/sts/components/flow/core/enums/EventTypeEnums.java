package com.yaocode.sts.components.flow.core.enums;


import lombok.Getter;

/**
 * 事件类型
 */
@Getter
public enum EventTypeEnums {

    MESSAGE(1, "消息"),
    SIGNAL(2, "信号"),
    ERROR(3, "错误");

    private final int code;
    private final String description;

    EventTypeEnums(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static EventTypeEnums fromCode(int code) {
        for (EventTypeEnums type : values()) {
            if (type.code == code) return type;
        }
        return null;
    }
}
