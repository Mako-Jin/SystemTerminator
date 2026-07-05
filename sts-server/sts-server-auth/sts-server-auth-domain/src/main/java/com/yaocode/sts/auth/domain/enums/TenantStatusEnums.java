package com.yaocode.sts.auth.domain.enums;

import lombok.Getter;

/**
 * 租户状态
 * @author: Jin-LiangBo
 * @date: 2025年10月17日 20:55
 */
@Getter
public enum TenantStatusEnums {
    INACTIVE(0, "未激活"),
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
    SUSPENDED(4, "已停用"),
    /**
     * 删除状态
     */
    DELETE(5, "删除状态"),
    ;

    private final Integer code;

    private final String desc;

    TenantStatusEnums(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static TenantStatusEnums fromCode(int code) {
        for (TenantStatusEnums status : values()) {
            if (status.code == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown tenant status: " + code);
    }

}
