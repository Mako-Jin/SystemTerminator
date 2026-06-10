package com.yaocode.sts.auth.domain.enums;

import lombok.Getter;

/**
 * 令牌类型枚举
 */
@Getter
public enum TokenTypeEnums {

    /**
     * 访问令牌
     */
    ACCESS_TOKEN("ACCESS_TOKEN"),

    /**
     * 刷新令牌
     */
    REFRESH_TOKEN("REFRESH_TOKEN"),

    /**
     * 记住我令牌
     */
    REMEMBER_ME("REMEMBER_ME"),

    /**
     * 状态令牌
     */
    STATE("STATE"),
    ;

    private final String tokenType;

    TokenTypeEnums(String tokenType) {
        this.tokenType = tokenType;
    }
}
