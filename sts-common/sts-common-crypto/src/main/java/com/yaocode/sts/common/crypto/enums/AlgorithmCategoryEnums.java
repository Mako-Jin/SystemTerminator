package com.yaocode.sts.common.crypto.enums;

import com.yaocode.sts.common.crypto.constants.CryptoI18nKeyConstants;
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
    HASH(CryptoI18nKeyConstants.ALGORITHM_CATEGORY_HASH),
    /** 对称加密 */
    SYMMETRIC(CryptoI18nKeyConstants.ALGORITHM_CATEGORY_SYMMETRIC),
    /** 非对称加密 */
    ASYMMETRIC(CryptoI18nKeyConstants.ALGORITHM_CATEGORY_ASYMMETRIC),
    /** 消息认证码 */
    MAC(CryptoI18nKeyConstants.ALGORITHM_CATEGORY_MAC),
    /** 伪随机数生成器 */
    PRNG(CryptoI18nKeyConstants.ALGORITHM_CATEGORY_PRNG),
    /** 密码HASH加密 */
    PASSWORD_HASH(CryptoI18nKeyConstants.ALGORITHM_CATEGORY_PASSWORD_HASH),
    ;

    /**
     * 国际化key
     */
    private final String description;

    AlgorithmCategoryEnums(String description) {
        this.description = description;
    }
}