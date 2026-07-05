package com.yaocode.sts.auth.domain.enums;

import lombok.Getter;

/**
 * 角色约束类型（职责分离）
 */
@Getter
public enum ConstraintTypeEnums {

    MUTEX(1, "互斥约束"),
    CARDINALITY(2, "基数约束");

    private final int code;
    private final String desc;

    ConstraintTypeEnums(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ConstraintTypeEnums fromCode(int code) {
        for (ConstraintTypeEnums type : values()) {
            if (type.code == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown constraint type: " + code);
    }

}
