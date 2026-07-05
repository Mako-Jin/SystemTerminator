package com.yaocode.sts.auth.domain.enums;

import lombok.Getter;

/**
 * 聚合根类型枚举
 * 统一管理所有聚合根类型，避免硬编码字符串
 *
 * @author: Jin-LiangBo
 */
@Getter
public enum AggregateTypeEnums {

    USER("USER", "用户聚合根"),
    TENANT("TENANT", "租户聚合根"),
    ORGANIZATION("ORGANIZATION", "组织聚合根"),
    ROLE("ROLE", "角色聚合根"),
    RESOURCE("RESOURCE", "资源聚合根"),
    USER_GROUP("USER_GROUP", "用户组聚合根"),
    CLIENT("CLIENT", "客户端聚合根");

    private final String code;
    private final String desc;

    AggregateTypeEnums(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public String toString() {
        return code;
    }
}
