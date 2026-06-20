package com.yaocode.sts.auth.domain.enums;

import lombok.Getter;

/**
 * 租户用户类型
 */
@Getter
public enum TenantUserTypeEnums {

    EMPLOYEE(1, "员工"),
    ADMIN(2, "管理员"),
    GUEST(3, "访客"),
    CONTRACTOR(4, "外包人员"),
    INTERN(5, "实习生");

    private final int code;
    private final String desc;

    TenantUserTypeEnums(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static TenantUserTypeEnums fromCode(int code) {
        for (TenantUserTypeEnums type : values()) {
            if (type.code == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown tenant user type: " + code);
    }

    public boolean isAdmin() {
        return this == ADMIN;
    }

}
