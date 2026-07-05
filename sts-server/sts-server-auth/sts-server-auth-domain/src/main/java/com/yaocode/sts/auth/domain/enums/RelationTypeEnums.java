package com.yaocode.sts.auth.domain.enums;

import lombok.Getter;

/**
 * 组织-用户组关联类型
 */
@Getter
public enum RelationTypeEnums {

    INHERIT(1, "组织成员自动继承"),
    MANUAL(2, "手动分配");

    private final int code;
    private final String desc;

    RelationTypeEnums(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static RelationTypeEnums fromCode(int code) {
        for (RelationTypeEnums type : values()) {
            if (type.code == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown relation type: " + code);
    }

    public boolean isInherit() {
        return this == INHERIT;
    }

}
