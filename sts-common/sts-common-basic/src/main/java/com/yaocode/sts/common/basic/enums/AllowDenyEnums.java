package com.yaocode.sts.common.basic.enums;

import com.yaocode.sts.common.basic.constants.BasicI18nKeyConstants;
import com.yaocode.sts.common.basic.constants.IConstants;
import lombok.Getter;

/**
 * 允许/拒绝枚举
 * @author: Jin-LiangBo
 * @date: 2025年10月17日 22:16
 */
@Getter
public enum AllowDenyEnums {
    ALLOW(IConstants.ALLOW_CODE, BasicI18nKeyConstants.ALLOW_DESC),
    DENY(IConstants.DENY_CODE, BasicI18nKeyConstants.DENY_DESC);

    private final Integer code;
    private final String desc;

    AllowDenyEnums(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据code获取枚举
     */
    public static AllowDenyEnums fromCode(Integer code) {
        for (AllowDenyEnums item : AllowDenyEnums.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }

    /**
     * 根据code获取描述
     */
    public static String getDescByCode(Integer code) {
        AllowDenyEnums item = fromCode(code);
        return item != null ? item.getDesc() : null;
    }

    /**
     * 判断是否为允许
     */
    public static boolean isAllow(Integer code) {
        return ALLOW.getCode().equals(code);
    }

    /**
     * 判断是否为拒绝
     */
    public static boolean isDeny(Integer code) {
        return DENY.getCode().equals(code);
    }

    /**
     * 转换为布尔值 (true表示允许)
     */
    public boolean toBoolean() {
        return this == ALLOW;
    }

    /**
     * 根据布尔值获取枚举
     */
    public static AllowDenyEnums fromBoolean(boolean flag) {
        return flag ? ALLOW : DENY;
    }
}