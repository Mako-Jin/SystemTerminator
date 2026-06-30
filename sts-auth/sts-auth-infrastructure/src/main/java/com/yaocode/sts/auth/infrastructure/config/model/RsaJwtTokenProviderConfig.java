package com.yaocode.sts.auth.infrastructure.config.model;


import com.yaocode.sts.common.crypto.enums.AlgorithmTypeEnums;
import com.yaocode.sts.common.crypto.utils.JwtKeyUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

/**
 * RSA 算法配置（RS256/RS384/RS512）
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RsaJwtTokenProviderConfig extends AbstractJwtTokenProviderConfig {

    private static final Logger logger = LoggerFactory.getLogger(RsaJwtTokenProviderConfig.class);

    /**
     * RSA 公钥（PEM 格式）
     */
    private String publicKey;

    /**
     * RSA 私钥（PEM 格式）
     */
    private String privateKey;

    /**
     * 公钥文件路径（可选）
     */
    private String publicKeyPath;

    /**
     * 私钥文件路径（可选）
     */
    private String privateKeyPath;

    public RsaJwtTokenProviderConfig(AlgorithmTypeEnums algorithm, String publicKey, String privateKey, Duration ttl) {
        super(algorithm, ttl);
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public RsaJwtTokenProviderConfig(AlgorithmTypeEnums algorithm, Duration ttl) {
        super(algorithm, ttl);
        this.publicKey = JwtKeyUtils.generateDefaultRsaPublicKey();
        this.privateKey = JwtKeyUtils.generateDefaultRsaPrivateKey();
    }

    @Override
    public boolean isValid() {
        try {
            validateCommon();
            // 检查公钥
            boolean hasPublicKey = (publicKey != null && !publicKey.isBlank()) ||
                    (publicKeyPath != null && !publicKeyPath.isBlank());
            if (!hasPublicKey) {
                // 没有配置公钥，使用默认生成的（开发环境）
                return true;
            }

            // 检查私钥（签名需要私钥，但验证只需要公钥）
            // 如果只有公钥路径或公钥，也视为有效（验证模式）
            boolean hasPrivateKey = (privateKey != null && !privateKey.isBlank()) ||
                    (privateKeyPath != null && !privateKeyPath.isBlank());
            if (!hasPrivateKey) {
                logger.warn("RSA private key not configured, signing operations will fail");
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
