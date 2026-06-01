package com.yaocode.sts.common.crypto.enums;

import lombok.Getter;

/**
 * 加密算法类型枚举
 * 包含哈希算法、对称加密、非对称加密、消息认证码等
 * @author: Jin-LiangBo
 * @date: 2026年06月01日
 */
@Getter
public enum AlgorithmCategoryEnums {
    /** 哈希算法 */
    HASH("哈希算法"),
    /** 对称加密 */
    SYMMETRIC("对称加密"),
    /** 非对称加密 */
    ASYMMETRIC("非对称加密"),
    /** 消息认证码 */
    MAC("消息认证码"),
    /** 伪随机数生成器 */
    PRNG("伪随机数生成器"),
    /** 密码HASH加密 */
    PASSWORD_HASH("密码哈希算法"),
    ;

    private final String description;

    AlgorithmCategoryEnums(String description) {
        this.description = description;
    }
}
