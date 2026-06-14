package com.yaocode.sts.auth.infrastructure.config;

import com.yaocode.sts.auth.domain.enums.TokenTypeEnums;
import com.yaocode.sts.auth.domain.port.JwtTokenPort;
import com.yaocode.sts.auth.infrastructure.config.model.HmacJwtTokenProviderConfig;
import com.yaocode.sts.auth.infrastructure.config.model.JwtTokenProviderConfig;
import com.yaocode.sts.auth.infrastructure.config.model.RsaJwtTokenProviderConfig;
import com.yaocode.sts.auth.infrastructure.config.properties.JwtTokenProperties;
import com.yaocode.sts.auth.infrastructure.port.Hmac512JwtTokenAdapter;
import com.yaocode.sts.auth.infrastructure.port.Rsa384JwtTokenAdapter;
import com.yaocode.sts.common.crypto.utils.JwtKeyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

@Configuration
@EnableConfigurationProperties(JwtTokenProperties.class)
public class JwtTokenConfig {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenConfig.class);

    private final JwtTokenProperties properties;

    private final ResourceLoader resourceLoader;

    public JwtTokenConfig(JwtTokenProperties properties, ResourceLoader resourceLoader) {
        this.properties = properties;
        this.resourceLoader = resourceLoader;
    }

    // ==================== Access Token ====================

    /**
     * Access Token Port
     * 用于 API 访问认证，短期有效
     */
    @Bean
    @Qualifier("accessTokenPort")
    public JwtTokenPort accessTokenPort() {
        JwtTokenProviderConfig config = properties.getAccess();
        logger.info("Creating AccessTokenPort with algorithm: {}", config.getAlgorithm());
        return createTokenPort(config, TokenTypeEnums.ACCESS_TOKEN);
    }


    // ==================== Refresh Token ====================

    /**
     * Refresh Token Port
     * 用于刷新 Access Token，长期有效
     */
    @Bean
    @Qualifier("refreshTokenPort")
    public JwtTokenPort refreshTokenPort() {
        JwtTokenProviderConfig config = properties.getRefresh();
        logger.info("Creating RefreshTokenPort with algorithm: {}", config.getAlgorithm());
        return createTokenPort(config, TokenTypeEnums.REFRESH_TOKEN);
    }


    // ==================== Remember-Me Token ====================

    /**
     * Remember-Me Token Port
     * 用于自动登录，超长期有效
     */
    @Bean
    @Qualifier("rememberMeTokenPort")
    public JwtTokenPort rememberMeTokenPort() {
        JwtTokenProviderConfig config = properties.getRememberMe();
        logger.info("Creating RememberMeTokenPort with algorithm: {}", config.getAlgorithm());
        return createTokenPort(config, TokenTypeEnums.REMEMBER_ME);
    }


    // ==================== State Token ====================

    /**
     * State Token Port
     * 用于 OAuth2 授权流程，一次性短期有效
     */
    @Bean
    @Qualifier("stateTokenPort")
    public JwtTokenPort stateTokenPort() {
        JwtTokenProviderConfig config = properties.getState();
        logger.info("Creating StateTokenPort with algorithm: {}", config.getAlgorithm());
        return createTokenPort(config, TokenTypeEnums.STATE_TOKEN);
    }


    // ==================== 通用创建方法 ====================

    /**
     * 根据配置创建对应的 JwtTokenPort 实现
     *
     * @param config    配置对象
     * @param tokenType Token 类型
     * @return JwtTokenPort 实现
     */
    private JwtTokenPort createTokenPort(JwtTokenProviderConfig config, TokenTypeEnums tokenType) {
        // 根据算法类型创建对应的适配器
        if (config instanceof HmacJwtTokenProviderConfig hmacConfig) {

            // 尝试从文件加载密钥
            String secret = loadHmacSecret(hmacConfig);

            return new Hmac512JwtTokenAdapter(
                    hmacConfig.getAlgorithm(),
                    secret,
                    hmacConfig.getTtl().getSeconds(),
                    hmacConfig.getIssuer(),
                    hmacConfig.getAudience(),
                    tokenType
            );
        }

        if (config instanceof RsaJwtTokenProviderConfig rsaConfig) {

            String publicKey = loadRsaPublicKey(rsaConfig);
            String privateKey = loadRsaPrivateKey(rsaConfig);

            return new Rsa384JwtTokenAdapter(
                    rsaConfig.getAlgorithm(),
                    rsaConfig.getTtl().getSeconds(),
                    rsaConfig.getIssuer(),
                    rsaConfig.getAudience(),
                    privateKey,
                    publicKey,
                    tokenType
            );
        }

        throw new IllegalArgumentException("Unsupported config type: " + config.getClass().getName());
    }

    /**
     * 加载 HMAC 密钥
     * <p>优先级：直接配置 > 文件路径 > 默认生成</p>
     */
    private String loadHmacSecret(HmacJwtTokenProviderConfig config) {
        // 1. 如果直接配置了密钥，优先使用
        if (config.getSecret() != null && !config.getSecret().isBlank()) {
            return config.getSecret();
        }

        // 2. 尝试从文件加载
        if (config.getSecretPath() != null && !config.getSecretPath().isBlank()) {
            String secret = JwtKeyUtils.loadKey(config.getSecretPath(), resourceLoader);
            if (secret != null) {
                logger.info("Loaded HMAC secret from file: {}", config.getSecretPath());
                return secret;
            }
            logger.warn("Failed to load HMAC secret from file: {}, using default", config.getSecretPath());
        }

        // 3. 返回当前配置（可能是默认生成的）
        return config.getSecret();
    }

    /**
     * 加载 RSA 公钥
     */
    private String loadRsaPublicKey(RsaJwtTokenProviderConfig config) {
        if (config.getPublicKey() != null && !config.getPublicKey().isBlank()) {
            return config.getPublicKey();
        }

        if (config.getPublicKeyPath() != null && !config.getPublicKeyPath().isBlank()) {
            String publicKey = JwtKeyUtils.loadKey(config.getPublicKeyPath(), resourceLoader);
            if (publicKey != null) {
                logger.info("Loaded RSA public key from file: {}", config.getPublicKeyPath());
                return publicKey;
            }
            logger.warn("Failed to load RSA public key from file: {}, using default", config.getPublicKeyPath());
        }

        return config.getPublicKey();
    }

    /**
     * 加载 RSA 私钥
     */
    private String loadRsaPrivateKey(RsaJwtTokenProviderConfig config) {
        if (config.getPrivateKey() != null && !config.getPrivateKey().isBlank()) {
            return config.getPrivateKey();
        }

        if (config.getPrivateKeyPath() != null && !config.getPrivateKeyPath().isBlank()) {
            String privateKey = JwtKeyUtils.loadKey(config.getPrivateKeyPath(), resourceLoader);
            if (privateKey != null) {
                logger.info("Loaded RSA private key from file: {}", config.getPrivateKeyPath());
                return privateKey;
            }
            logger.warn("Failed to load RSA private key from file: {}, using default", config.getPrivateKeyPath());
        }

        return config.getPrivateKey();
    }

}
