package com.yaocode.sts.common.basic.enums;

import com.yaocode.sts.common.basic.constants.BasicI18nKeyConstants;
import com.yaocode.sts.common.basic.constants.IConstants;
import lombok.Getter;

/**
 * 成功/失败枚举
 * @author: Jin-LiangBo
 * @date: 2025年10月17日 22:16
 */
@Getter
public enum SuccessFailedEnums {

    SUCCESS(IConstants.SUCCESS_CODE, BasicI18nKeyConstants.SUCCESS_DESC),
    FAILED(IConstants.FAILED_CODE, BasicI18nKeyConstants.FAILED_DESC);

    private final Integer code;
    private final String desc;

    SuccessFailedEnums(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据code获取枚举
     */
    public static SuccessFailedEnums fromCode(Integer code) {
        for (SuccessFailedEnums item : SuccessFailedEnums.values()) {
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
        SuccessFailedEnums item = fromCode(code);
        return item != null ? item.getDesc() : null;
    }

    /**
     * 判断是否为成功
     */
    public static boolean isSuccess(Integer code) {
        return SUCCESS.getCode().equals(code);
    }

    /**
     * 判断是否为失败
     */
    public static boolean isFailed(Integer code) {
        return FAILED.getCode().equals(code);
    }

    /**
     * 转换为布尔值 (true表示成功)
     */
    public boolean toBoolean() {
        return this == SUCCESS;
    }

    /**
     * 根据布尔值获取枚举
     */
    public static SuccessFailedEnums fromBoolean(boolean flag) {
        return flag ? SUCCESS : FAILED;
    }
}