package com.yaocode.sts.file.core.enums;

import lombok.Getter;

/**
 * 文件差异类型枚举
 *
 * @author yaocode
 * @since 1.0.0
 */
@Getter
public enum DiffTypeEnums {

    /** 未知类型（内容为 null 时） */
    UNKNOWN("UNKNOWN", "未知类型"),

    /** 完全相同 */
    IDENTICAL("IDENTICAL", "完全相同"),

    /** 文本差异 */
    TEXT("TEXT_DIFF", "文本差异"),

    /** 二进制差异 */
    BINARY("BINARY_DIFF", "二进制差异"),
    ;

    private final String code;
    private final String description;

    DiffTypeEnums(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 根据 code 获取枚举
     */
    public static DiffTypeEnums fromCode(String code) {
        if (code == null) {
            return null;
        }
        for (DiffTypeEnums e : values()) {
            if (e.code.equals(code)) {
                return e;
            }
        }
        return null;
    }
}
