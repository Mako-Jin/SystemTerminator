package com.yaocode.sts.auth.domain.enums;

import lombok.Getter;

/**
 * 用户新增方式
 * @author: Jin-LiangBo
 * @date: 2025年10月22日 22:54
 */
@Getter
public enum UserAddTypeEnums {
    /**
     * 新增
     */
    ADD(1, "新增"),
    /**
     * 注册
     */
    REGISTER(2, "注册"),
    ;

    private final Integer code;

    private final String type;

    UserAddTypeEnums(Integer code, String type) {
        this.code = code;
        this.type = type;
    }
}
