package com.yaocode.sts.auth.domain.enums;

import lombok.Getter;

/**
 * 三员管理角色（管理职能，每人可以有多个）
 */
@Getter
public enum ManagementRoleEnums {

    SYSTEM_ADMIN(1, "系统管理员", "负责系统配置、用户管理"),
    SECURITY_ADMIN(2, "安全管理员", "负责安全策略、审计日志"),
    AUDITOR(3, "审计员", "负责审计日志、合规检查"),
    OPERATOR(4, "运维员", "负责系统运维、监控"),
    DATA_ADMIN(5, "数据管理员", "负责数据管理、备份");

    private final int code;
    private final String desc;
    private final String description;

    ManagementRoleEnums(int code, String desc, String description) {
        this.code = code;
        this.desc = desc;
        this.description = description;
    }

    public static ManagementRoleEnums fromCode(int code) {
        for (ManagementRoleEnums role : values()) {
            if (role.code == code) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unknown management role: " + code);
    }

    /**
     * 检查该角色是否需要安全审计
     */
    public boolean requireAudit() {
        return this == SYSTEM_ADMIN || this == SECURITY_ADMIN || this == AUDITOR;
    }

}
