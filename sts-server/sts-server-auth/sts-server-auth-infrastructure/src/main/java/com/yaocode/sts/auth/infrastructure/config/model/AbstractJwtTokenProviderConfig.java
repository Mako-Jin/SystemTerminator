package com.yaocode.sts.auth.infrastructure.config.model;

import com.yaocode.sts.auth.infrastructure.constants.AuthInfrastructureConstants;
import com.yaocode.sts.common.crypto.enums.AlgorithmTypeEnums;
import lombok.Data;

import java.time.Duration;

@Data
public abstract class AbstractJwtTokenProviderConfig implements JwtTokenProviderConfig {

    /**
     * 算法类型
     */
    protected AlgorithmTypeEnums algorithm;
    /**
     * 有效期
     */
    protected Duration ttl;

    protected String issuer;

    protected String audience;

    public AbstractJwtTokenProviderConfig(AlgorithmTypeEnums algorithm, Duration ttl) {
        this.algorithm = algorithm;
        this.ttl = ttl;
    }

    @Override
    public AlgorithmTypeEnums getAlgorithm() {
        return algorithm;
    }

    @Override
    public Duration getTtl() {
        return ttl;
    }

    /**
     * 通用验证逻辑
     */
    protected void validateCommon() {
        if (algorithm == null) {
            throw new IllegalStateException(
                    AuthInfrastructureConstants.ERROR_JWT_ALGORITHM_NOT_NULL);
        }
        if (ttl == null || ttl.isNegative()) {
            throw new IllegalStateException(
                    AuthInfrastructureConstants.ERROR_JWT_TTL_POSITIVE);
        }
    }

}