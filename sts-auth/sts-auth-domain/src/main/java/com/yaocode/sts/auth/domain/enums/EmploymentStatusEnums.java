package com.yaocode.sts.auth.domain.enums;

import lombok.Getter;

/**
 * 雇佣状态
 */
@Getter
public enum EmploymentStatusEnums {

    PROBATION(1, "试用期"),
    ACTIVE(2, "在职"),
    RESIGNED(3, "已离职"),
    SUSPENDED(4, "停薪留职");

    private final int code;
    private final String desc;

    EmploymentStatusEnums(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static EmploymentStatusEnums fromCode(int code) {
        for (EmploymentStatusEnums status : values()) {
            if (status.code == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown employment status: " + code);
    }

    public boolean isActive() {
        return this == PROBATION || this == ACTIVE;
    }

}
