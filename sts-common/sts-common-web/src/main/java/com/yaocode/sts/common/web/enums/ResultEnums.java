package com.yaocode.sts.common.web.enums;

/**
 * @author: Jin-LiangBo
 * @date: 2025年10月07日 21:04
 */
public enum ResultEnums {

    /**
     * 请求成功
     */
    SUCCESS("000000", "common.web.request.success"),

    /**
     * 系统异常
     */
    SYSTEM_ERROR("005000", "common.web.system.error"),
    ;

    private final String code;

    private final String msg;

    ResultEnums(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
