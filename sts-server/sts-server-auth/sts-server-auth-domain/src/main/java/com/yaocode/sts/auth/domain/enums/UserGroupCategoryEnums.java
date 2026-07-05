package com.yaocode.sts.auth.domain.enums;

import lombok.Getter;

/**
 * 用户组分类
 */
@Getter
public enum UserGroupCategoryEnums {

    STATIC(1, "静态组"),
    DYNAMIC(2, "动态组"),
    APP(3, "应用组");

    private final int code;
    private final String desc;

    UserGroupCategoryEnums(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static UserGroupCategoryEnums fromCode(int code) {
        for (UserGroupCategoryEnums category : values()) {
            if (category.code == code) {
                return category;
            }
        }
        throw new IllegalArgumentException("Unknown user group category: " + code);
    }

    public boolean isDynamic() {
        return this == DYNAMIC;
    }

}
