package com.yaocode.sts.common.resources.enums;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年11月15日 18:10
 */
public enum MediaTypeEnums {

    /**
     * application/json
     */
    APPLICATION_JSON("application/json"),

    ;

    private final String value;

    MediaTypeEnums(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
