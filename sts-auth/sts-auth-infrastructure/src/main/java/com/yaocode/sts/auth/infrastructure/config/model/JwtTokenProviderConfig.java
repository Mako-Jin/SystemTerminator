package com.yaocode.sts.auth.infrastructure.config.model;

import com.yaocode.sts.common.crypto.enums.AlgorithmTypeEnums;

import java.time.Duration;

/**
 * JWT Provider 配置接口
 */
public interface JwtTokenProviderConfig {

    /**
     * 获取算法类型
     */
    AlgorithmTypeEnums getAlgorithm();

    /**
     * 获取有效期
     */
    Duration getTtl();

    /**
     * 验证配置是否有效
     */
    boolean isValid();

    /**
     *
     */
    String getIssuer();

    /**
     *
     */
    String getAudience();
}