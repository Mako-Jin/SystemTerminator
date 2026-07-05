package com.yaocode.sts.auth.domain.enums;

import lombok.Getter;

/**
 * 黑白名单类型枚举
 * @author: Jin-LiangBo
 * @date: 2026-06-28
 */
@Getter
public enum BlackWhiteListTypeEnums {

    IP("IP", "IP地址/段"),
    DEVICE("DEVICE", "设备ID"),
    CLIENT("CLIENT", "客户端ID"),
    USER("USER", "用户ID"),
    COUNTRY("COUNTRY", "国家/地区"),
    USER_AGENT("USER_AGENT", "User Agent");

    private final String code;
    private final String desc;

    BlackWhiteListTypeEnums(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static BlackWhiteListTypeEnums fromCode(String code) {
        for (BlackWhiteListTypeEnums type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown Black White list type: " + code);
    }

}
