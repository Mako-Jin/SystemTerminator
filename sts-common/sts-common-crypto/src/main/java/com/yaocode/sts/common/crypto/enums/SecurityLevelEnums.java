package com.yaocode.sts.common.crypto.enums;

import com.yaocode.sts.common.crypto.constants.CryptoI18nKeyConstants;
import lombok.Getter;

@Getter
public enum SecurityLevelEnums {

    /** 高安全 */
    HIGH(CryptoI18nKeyConstants.SECURITY_LEVEL_HIGH, CryptoI18nKeyConstants.SECURITY_LEVEL_HIGH_ADVICE),
    /** 较弱 */
    WEAK(CryptoI18nKeyConstants.SECURITY_LEVEL_WEAK, CryptoI18nKeyConstants.SECURITY_LEVEL_WEAK_ADVICE),
    /** 不安全 */
    INSECURE(CryptoI18nKeyConstants.SECURITY_LEVEL_INSECURE, CryptoI18nKeyConstants.SECURITY_LEVEL_INSECURE_ADVICE);

    private final String level;
    private final String advice;

    SecurityLevelEnums(String level, String advice) {
        this.level = level;
        this.advice = advice;
    }

}