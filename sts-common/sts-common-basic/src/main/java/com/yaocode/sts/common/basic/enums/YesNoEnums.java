package com.yaocode.sts.common.basic.enums;

import com.yaocode.sts.common.basic.constants.BasicI18nKeyConstants;
import com.yaocode.sts.common.basic.constants.IConstants;
import lombok.Getter;

/**
 * 通用的是/否枚举
 * @author: Jin-LiangBo
 * @date: 2025年10月17日 22:16
 */
@Getter
public enum YesNoEnums {
    YES(IConstants.YES, BasicI18nKeyConstants.YES_DESC),
    NO(IConstants.NO, BasicI18nKeyConstants.NO_DESC);

    private final Integer code;
    private final String desc;

    YesNoEnums(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据布尔值获取code
     */
    public static Integer getCode(boolean flag) {
        return flag ? YES.getCode() : NO.getCode();
    }

    /**
     * 根据code获取枚举
     */
    public static YesNoEnums fromCode(Integer code) {
        for (YesNoEnums item : YesNoEnums.values()) {
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
        YesNoEnums item = fromCode(code);
        return item != null ? item.getDesc() : null;
    }

    /**
     * 判断是否为"是"
     */
    public static boolean isYes(Integer code) {
        return YES.getCode().equals(code);
    }

    /**
     * 判断是否为"否"
     */
    public static boolean isNo(Integer code) {
        return NO.getCode().equals(code);
    }

    /**
     * 转换为布尔值
     */
    public boolean toBoolean() {
        return this == YES;
    }

    /**
     * 根据布尔值获取枚举
     */
    public static YesNoEnums fromBoolean(boolean flag) {
        return flag ? YES : NO;
    }
}