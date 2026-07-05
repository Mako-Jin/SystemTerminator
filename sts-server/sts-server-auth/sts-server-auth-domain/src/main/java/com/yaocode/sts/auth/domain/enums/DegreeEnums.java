package com.yaocode.sts.auth.domain.enums;

import lombok.Getter;

/**
 * 学历
 */
@Getter
public enum DegreeEnums {

    HIGH_SCHOOL(1, "高中"),
    JUNIOR_COLLEGE(2, "大专"),
    BACHELOR(3, "学士"),
    MASTER(4, "硕士"),
    DOCTOR(5, "博士"),
    OTHER(99, "其他");

    private final int code;
    private final String desc;

    DegreeEnums(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static DegreeEnums fromCode(int code) {
        for (DegreeEnums degree : values()) {
            if (degree.code == code) {
                return degree;
            }
        }
        return OTHER;
    }

}
