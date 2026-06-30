package com.yaocode.sts.auth.infrastructure.config;

import com.yaocode.sts.auth.infrastructure.config.model.HmacJwtTokenProviderConfig;
import com.yaocode.sts.auth.infrastructure.config.model.JwtTokenProviderConfig;
import com.yaocode.sts.auth.infrastructure.config.model.RsaJwtTokenProviderConfig;
import com.yaocode.sts.common.crypto.enums.AlgorithmTypeEnums;
import com.yaocode.sts.common.tools.StringUtils;
import jakarta.annotation.Nonnull;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;

/**
 * JWT Token 配置转换器
 * 根据 algorithm 字段动态决定使用 HMAC 还是 RSA 配置
 */
@Component
@ConfigurationPropertiesBinding
public class JwtTokenProviderConfigConverter implements Converter<Map<String, Object>, JwtTokenProviderConfig> {

    @Override
    public JwtTokenProviderConfig convert(@Nonnull Map<String, Object> source) {
        if (source.isEmpty()) {
            return null;
        }

        String algorithm = (String) source.get("algorithm");
        if (algorithm == null || algorithm.isBlank()) {
            throw new IllegalArgumentException("algorithm is required for JWT configuration");
        }

        // 判断算法类型
        boolean isRsaAlgorithm = isRsaAlgorithm(algorithm);
        boolean isHmacAlgorithm = isHmacAlgorithm(algorithm);

        if (!isRsaAlgorithm && !isHmacAlgorithm) {
            throw new IllegalArgumentException("Unsupported algorithm: " + algorithm);
        }

        AlgorithmTypeEnums algorithmType = AlgorithmTypeEnums.valueOf(algorithm.toUpperCase());
        Duration ttl = parseDuration(source.get("ttl"));
        String issuer = (String) source.get("issuer");
        String audience = (String) source.get("audience");

        if (isRsaAlgorithm) {
            // 创建 RSA 配置
            RsaJwtTokenProviderConfig config = new RsaJwtTokenProviderConfig(algorithmType, ttl);
            config.setIssuer(issuer);
            config.setAudience(audience);

            // RSA 特有配置
            config.setPublicKey((String) source.get("public-key"));
            config.setPrivateKey((String) source.get("private-key"));
            config.setPublicKeyPath((String) source.get("public-key-path"));
            config.setPrivateKeyPath((String) source.get("private-key-path"));

            return config;
        } else {
            // 创建 HMAC 配置
            HmacJwtTokenProviderConfig config = new HmacJwtTokenProviderConfig(algorithmType, ttl);
            config.setIssuer(issuer);
            config.setAudience(audience);

            // HMAC 特有配置
            config.setSecret((String) source.get("secret"));
            config.setSecretPath((String) source.get("secret-path"));

            return config;
        }
    }

    /**
     * 判断是否为 RSA 算法
     */
    private boolean isRsaAlgorithm(String algorithm) {
        String upper = algorithm.toUpperCase();
        return upper.startsWith("RS") || upper.startsWith("PS") ||
                upper.equals("RSA") || upper.equals("RSA_OAEP");
    }

    /**
     * 判断是否为 HMAC 算法
     */
    private boolean isHmacAlgorithm(String algorithm) {
        String upper = algorithm.toUpperCase();
        return upper.startsWith("HS") || upper.startsWith("HMAC");
    }

    /**
     * 解析 Duration
     * 支持：秒数（数字）、ISO-8601 格式（PT1H）、简单格式（1h, 30m, 7d）
     */
    private Duration parseDuration(Object value) {
        if (value == null) {
            return Duration.ofHours(1); // 默认1小时
        }
        if (value instanceof Duration) {
            return (Duration) value;
        }
        if (value instanceof Number) {
            return Duration.ofSeconds(((Number) value).longValue());
        }
        if (value instanceof String str) {
            if (StringUtils.isBlank(str)) {
                return Duration.ofHours(1);
            }

            // 尝试解析为数字（秒）
            if (str.matches("\\d+")) {
                return Duration.ofSeconds(Long.parseLong(str));
            }

            // 尝试解析为 ISO-8601 格式
            if (str.startsWith("P") || str.startsWith("PT")) {
                try {
                    return Duration.parse(str);
                } catch (Exception e) {
                    // 解析失败，继续尝试其他格式
                }
            }

            // 尝试解析简单格式：1h, 30m, 7d, 5s
            try {
                return parseSimpleDuration(str);
            } catch (Exception e) {
                // 解析失败，使用默认值
                return Duration.ofHours(1);
            }
        }
        return Duration.ofHours(1);
    }

    /**
     * 解析简单时间格式
     */
    private Duration parseSimpleDuration(String str) {
        str = str.trim().toLowerCase();
        if (str.endsWith("s")) {
            return Duration.ofSeconds(Long.parseLong(str.substring(0, str.length() - 1)));
        } else if (str.endsWith("m")) {
            return Duration.ofMinutes(Long.parseLong(str.substring(0, str.length() - 1)));
        } else if (str.endsWith("h")) {
            return Duration.ofHours(Long.parseLong(str.substring(0, str.length() - 1)));
        } else if (str.endsWith("d")) {
            return Duration.ofDays(Long.parseLong(str.substring(0, str.length() - 1)));
        } else {
            // 默认按秒处理
            return Duration.ofSeconds(Long.parseLong(str));
        }
    }
}
