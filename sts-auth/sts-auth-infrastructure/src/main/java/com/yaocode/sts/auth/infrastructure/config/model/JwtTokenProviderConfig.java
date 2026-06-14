package com.yaocode.sts.auth.infrastructure.config.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.yaocode.sts.common.crypto.enums.AlgorithmTypeEnums;

import java.time.Duration;

/**
 * JWT Provider 配置接口
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "algorithm",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = HmacJwtTokenProviderConfig.class, name = "HS256"),
        @JsonSubTypes.Type(value = HmacJwtTokenProviderConfig.class, name = "HS384"),
        @JsonSubTypes.Type(value = HmacJwtTokenProviderConfig.class, name = "HS512"),
        @JsonSubTypes.Type(value = RsaJwtTokenProviderConfig.class, name = "RS256"),
        @JsonSubTypes.Type(value = RsaJwtTokenProviderConfig.class, name = "RS384"),
        @JsonSubTypes.Type(value = RsaJwtTokenProviderConfig.class, name = "RS512"),
})
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