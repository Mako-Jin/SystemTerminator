package com.yaocode.sts.file.core.enums;

import lombok.Getter;

/**
 * 文件状态枚举
 */
@Getter
public enum FileStatusEnums {

    NORMAL(1, "正常"),
    DELETED(2, "已删除"),
    ARCHIVED(3, "已归档"),
    MIGRATING(4, "迁移中");

    private final Integer code;
    private final String desc;

    FileStatusEnums(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static FileStatusEnums fromCode(Integer code) {
        for (FileStatusEnums e : values()) {
            if (e.code.equals(code)) return e;
        }
        return NORMAL;
    }
}
