package com.yaocode.sts.common.web.enums;

import com.yaocode.sts.common.web.constants.WebI18nConstants;
import lombok.Getter;

/**
 * @author: Jin-LiangBo
 * @date: 2025年10月07日 21:04
 */
@Getter
public enum ResultEnums {

    /**
     * 请求成功
     */
    SUCCESS("000000", WebI18nConstants.WEB_REQUEST_SUCCESS),

    /**
     * 系统异常
     */
    SYSTEM_ERROR("005000", WebI18nConstants.WEB_SYSTEM_ERROR),

    /**
     * 系统异常
     */
    PARAM_ERROR("004000", WebI18nConstants.WEB_PARAM_ERROR),
    ;

    private final String code;

    private final String msg;

    ResultEnums(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
