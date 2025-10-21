package com.yaocode.sts.auth.domain.enums;

import lombok.Getter;

/**
 * 租户状态
 * @author: Jin-LiangBo
 * @date: 2025年10月17日 20:55
 */
@Getter
public enum TenantStatusEnums {
    /**
     * 激活状态
     */
    ACTIVATE(1, "激活状态"),
    /**
     * 冻结状态
     */
    FREEZE(2, "冻结状态"),
    /**
     * 异常状态
     */
    ABNORMAL(3, "异常状态"),
    /**
     * 删除状态
     */
    DELETE(4, "删除状态"),
    ;

    private final Integer code;

    private final String desc;

    TenantStatusEnums(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
