package com.yaocode.sts.auth.domain.enums;

import lombok.Getter;

/**
 * 角色分类
 */
@Getter
public enum RoleCategoryEnums {

    STATIC(1, "静态角色"),
    DYNAMIC(2, "动态角色"),
    APP(3, "应用角色");

    private final int code;
    private final String desc;

    RoleCategoryEnums(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static RoleCategoryEnums fromCode(int code) {
        for (RoleCategoryEnums category : values()) {
            if (category.code == code) {
                return category;
            }
        }
        throw new IllegalArgumentException("Unknown role category: " + code);
    }

    public boolean isDynamic() {
        return this == DYNAMIC;
    }

}
