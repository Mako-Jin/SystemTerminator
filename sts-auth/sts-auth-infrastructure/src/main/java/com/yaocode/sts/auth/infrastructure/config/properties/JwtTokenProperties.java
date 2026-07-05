package com.yaocode.sts.auth.infrastructure.config.properties;


import com.yaocode.sts.auth.domain.constants.AuthI18nKeyConstants;
import com.yaocode.sts.auth.domain.enums.TokenTypeEnums;
import com.yaocode.sts.auth.infrastructure.config.model.HmacJwtTokenProviderConfig;
import com.yaocode.sts.auth.infrastructure.config.model.JwtTokenProviderConfig;
import com.yaocode.sts.auth.infrastructure.config.model.RsaJwtTokenProviderConfig;
import com.yaocode.sts.common.crypto.enums.AlgorithmTypeEnums;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

/**
 * JWT 配置属性
 */
@Data
@ConfigurationProperties(prefix = "yaocode.jwt.token")
public class JwtTokenProperties {

    /**
     * Access Token 配置
     * 默认：HS512（性能好，足够安全）
     */
    private JwtTokenProviderConfig access;

    /**
     * Refresh Token 配置
     * 默认：RS256（非对称，可让客户端安全存储）
     */
    private JwtTokenProviderConfig refresh;

    /**
     * Remember-Me Token 配置
     * 默认：HS512（简单场景）或 ED25519（高安全场景）
     */
    private JwtTokenProviderConfig rememberMe;

    /**
     * State Token 配置
     * 默认：HS256（轻量级，一次性使用）
     */
    private JwtTokenProviderConfig state;


    /**
     * Access Token 默认配置
     * 推荐：HS512 - 对称加密，性能优秀，安全性高
     */
    private JwtTokenProviderConfig getDefaultAccessConfig() {
        return new HmacJwtTokenProviderConfig(
                AlgorithmTypeEnums.HS512,
                Duration.ofHours(1)
        );
    }

    /**
     * Refresh Token 默认配置
     * 推荐：RS256 - 非对称，可让客户端安全存储，服务端无状态验证
     */
    private JwtTokenProviderConfig getDefaultRefreshConfig() {
        return new RsaJwtTokenProviderConfig(
                AlgorithmTypeEnums.RS256,
                Duration.ofDays(7)
        );
    }

    /**
     * Remember-Me Token 默认配置
     * 推荐：HS512 - 简单场景，或 ED25519 - 高安全场景
     * 默认使用 HS512，因为 ED25519 需要生成密钥对
     */
    private JwtTokenProviderConfig getDefaultRememberMeConfig() {
        return new HmacJwtTokenProviderConfig(
                AlgorithmTypeEnums.HS512,
                Duration.ofDays(30)
        );
    }

    /**
     * State Token 默认配置
     * 推荐：HS256 - 轻量级，一次性使用，足够安全
     */
    private JwtTokenProviderConfig getDefaultStateConfig() {
        return new HmacJwtTokenProviderConfig(
                AlgorithmTypeEnums.HS256,
                Duration.ofMinutes(5)
        );
    }

    public JwtTokenProviderConfig getAccess() {
        if (access == null) {
            access = getDefaultAccessConfig();
        }
        return validateConfig(access, TokenTypeEnums.ACCESS_TOKEN.getTokenType());
    }



    public JwtTokenProviderConfig getRefresh() {
        if (refresh == null) {
            refresh = getDefaultRefreshConfig();
        }
        return validateConfig(refresh, TokenTypeEnums.REFRESH_TOKEN.getTokenType());
    }

    public JwtTokenProviderConfig getRememberMe() {
        if (rememberMe == null) {
            rememberMe = getDefaultRememberMeConfig();
        }
        return validateConfig(rememberMe, TokenTypeEnums.REMEMBER_ME.getTokenType());
    }

    public JwtTokenProviderConfig getState() {
        if (state == null) {
            state = getDefaultStateConfig();
        }
        return validateConfig(state, TokenTypeEnums.STATE_TOKEN.getTokenType());
    }



    private JwtTokenProviderConfig validateConfig(JwtTokenProviderConfig config, String providerName) {
        if (config == null) {
            throw new IllegalStateException(
                    String.format(AuthI18nKeyConstants.ERROR_JWT_CONFIG_NOT_FOUND));
        }
        if (!config.isValid()) {
            throw new IllegalStateException(
                    String.format(AuthI18nKeyConstants.ERROR_JWT_CONFIG_INVALID));
        }
        return config;
    }
}