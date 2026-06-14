package com.yaocode.sts.components.flow.core.enums;


import lombok.Getter;

/**
 * 变量类型
 */
@Getter
public enum VariableTypeEnums {

    STRING(1, "字符串"),
    INTEGER(2, "整数"),
    LONG(3, "长整数"),
    DOUBLE(4, "浮点数"),
    BOOLEAN(5, "布尔值"),
    DATE(6, "日期"),
    JSON(7, "JSON"),
    BINARY(8, "二进制"),
    SERIALIZABLE(9, "序列化对象");

    private final int code;
    private final String description;

    VariableTypeEnums(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static VariableTypeEnums fromCode(int code) {
        for (VariableTypeEnums type : values()) {
            if (type.code == code) return type;
        }
        return null;
    }

    public boolean isNumber() {
        return this == INTEGER || this == LONG || this == DOUBLE;
    }
}
