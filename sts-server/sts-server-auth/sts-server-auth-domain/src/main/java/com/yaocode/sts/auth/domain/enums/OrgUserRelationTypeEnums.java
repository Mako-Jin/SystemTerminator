package com.yaocode.sts.auth.domain.enums;

import lombok.Getter;

/**
 * 组织-用户关系类型
 */
@Getter
public enum OrgUserRelationTypeEnums {

    MANAGER(1, "负责人"),
    MEMBER(2, "成员"),
    TEMPORARY(3, "临时人员");

    private final int code;
    private final String desc;

    OrgUserRelationTypeEnums(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static OrgUserRelationTypeEnums fromCode(int code) {
        for (OrgUserRelationTypeEnums type : values()) {
            if (type.code == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown org user relation type: " + code);
    }

    public boolean isManager() {
        return this == MANAGER;
    }

}
