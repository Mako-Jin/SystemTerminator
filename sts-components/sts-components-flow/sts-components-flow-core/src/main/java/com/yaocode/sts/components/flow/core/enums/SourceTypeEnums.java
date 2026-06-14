package com.yaocode.sts.components.flow.core.enums;

import lombok.Getter;


/**
 * 来源类型
 */
@Getter
public enum SourceTypeEnums {

    BPMN_XML(1, "BPMN XML"),
    JSON(2, "JSON");

    private final int code;
    private final String description;

    SourceTypeEnums(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static SourceTypeEnums fromCode(int code) {
        for (SourceTypeEnums type : values()) {
            if (type.code == code) return type;
        }
        return null;
    }
}
