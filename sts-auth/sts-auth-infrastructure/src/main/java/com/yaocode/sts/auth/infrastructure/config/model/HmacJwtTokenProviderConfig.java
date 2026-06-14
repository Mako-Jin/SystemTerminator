package com.yaocode.sts.auth.infrastructure.config.model;

import com.yaocode.sts.common.crypto.enums.AlgorithmTypeEnums;
import com.yaocode.sts.common.crypto.utils.JwtKeyUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Duration;

@Data
@EqualsAndHashCode(callSuper = true)
public class HmacJwtTokenProviderConfig extends AbstractJwtTokenProviderConfig {

    /**
     * HMAC 密钥
     */
    private String secret;

    private String secretPath;

    public HmacJwtTokenProviderConfig(AlgorithmTypeEnums algorithm, String secret, Duration ttl) {
        super(algorithm, ttl);
        this.secret = secret;
    }

    public HmacJwtTokenProviderConfig(AlgorithmTypeEnums algorithm, Duration ttl) {
        super(algorithm, ttl);
        this.secret = JwtKeyUtils.generateDefaultHmacSecret();
    }

    @Override
    public boolean isValid() {
        try {
            validateCommon();
            if (secret == null || secret.isBlank()) {
                // 如果没有直接配置密钥，检查是否有密钥路径
                if (secretPath == null || secretPath.isBlank()) {
                    // 两者都没有，使用默认生成的密钥（开发环境）
                    return true;
                }
                // 有密钥路径，配置有效（密钥将从文件加载）
                return true;
            }
            // 根据算法检查密钥长度
            return switch (algorithm) {
                case HMAC_SHA256 -> secret.getBytes().length >= 32;   // 256位
                case HMAC_SHA384 -> secret.getBytes().length >= 48;   // 384位
                case HMAC_SHA512 -> secret.getBytes().length >= 64;   // 512位
                default -> false;
            };
        } catch (Exception e) {
            return false;
        }
    }
}
