package com.yaocode.sts.common.basic.enums;

import com.yaocode.sts.common.basic.constants.BasicI18nKeyConstants;
import com.yaocode.sts.common.basic.constants.IConstants;
import lombok.Getter;

/**
 * 启用/禁用枚举
 * @author: Jin-LiangBo
 * @date: 2025年10月17日 22:16
 */
@Getter
public enum EnableEnums {
    ENABLED(IConstants.ENABLED_CODE, BasicI18nKeyConstants.ENABLED_DESC),
    DISABLED(IConstants.DISABLED_CODE, BasicI18nKeyConstants.DISABLED_DESC);

    private final Integer code;
    private final String desc;

    EnableEnums(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据code获取枚举
     */
    public static EnableEnums fromCode(Integer code) {
        for (EnableEnums item : EnableEnums.values()) {
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
        EnableEnums item = fromCode(code);
        return item != null ? item.getDesc() : null;
    }

    /**
     * 判断是否为启用
     */
    public static boolean isEnabled(Integer code) {
        return ENABLED.getCode().equals(code);
    }

    /**
     * 判断是否为禁用
     */
    public static boolean isDisabled(Integer code) {
        return DISABLED.getCode().equals(code);
    }

    /**
     * 转换为布尔值 (true表示启用)
     */
    public boolean toBoolean() {
        return this == ENABLED;
    }

    /**
     * 根据布尔值获取枚举
     */
    public static EnableEnums fromBoolean(boolean flag) {
        return flag ? ENABLED : DISABLED;
    }
}