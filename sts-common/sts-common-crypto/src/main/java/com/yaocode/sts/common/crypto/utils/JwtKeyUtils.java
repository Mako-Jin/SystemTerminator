package com.yaocode.sts.common.crypto.utils;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import com.yaocode.sts.common.crypto.algorithm.asymmetric.RSAAlgorithm;
import com.yaocode.sts.common.crypto.algorithm.encode.Base64Algorithm;
import com.yaocode.sts.common.crypto.algorithm.mac.Hmac512Algorithm;
import com.yaocode.sts.common.crypto.enums.AlgorithmTypeEnums;
import com.yaocode.sts.common.crypto.exception.CryptoException;
import com.yaocode.sts.common.tools.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyPair;

/**
 * JWT 密钥生成器
 * <p>
 * 提供默认密钥生成功能，用于开发环境或演示目的
 * 生产环境应使用安全的密钥管理方案
 * </p>
 */
public class JwtKeyUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtKeyUtils.class);

    private static final String CLASSPATH_PREFIX = "classpath:";

    private static final String FILE_PREFIX = "file:";

    private JwtKeyUtils() {
    }

    // ==================== HMAC 密钥生成 ====================

    /**
     * 生成默认 HMAC 密钥（Base64 编码）
     * <p>用于开发环境，生产环境应从安全位置加载密钥</p>
     *
     * @return Base64 编码的 64 字节随机密钥
     */
    public static String generateDefaultHmacSecret() {
        try {
            byte[] key = Hmac512Algorithm.generateRandomKey();
            String base64Key = Base64Algorithm.encryptByBase64(key);
            logger.warn("Generated default HMAC secret for development purposes. " +
                    "In production, configure a secure secret via environment variables.");
            return base64Key;
        } catch (Exception e) {
            throw new CryptoException("Failed to generate default HMAC secret", e);
        }
    }

    /**
     * 生成指定长度的 HMAC 密钥
     *
     * @param algorithm 算法类型
     * @return Base64 编码的密钥
     */
    public static String generateHmacSecret(AlgorithmTypeEnums algorithm) {
        if (algorithm == null) {
            throw new IllegalArgumentException("Algorithm cannot be null");
        }

        int keyLength;
        switch (algorithm) {
            case HMAC_SHA256, HS256 -> keyLength = 32;  // 256 bits
            case HMAC_SHA384, HS384 -> keyLength = 48;  // 384 bits
            case HMAC_SHA512, HS512 -> keyLength = 64;  // 512 bits
            default -> throw new IllegalArgumentException("Unsupported HMAC algorithm: " + algorithm);
        }

        byte[] key = Hmac512Algorithm.generateRandomKey(keyLength);
        return Base64Algorithm.encryptByBase64(key);
    }

    // ==================== RSA 密钥对生成 ====================

    /**
     * 生成默认 RSA 公钥（PEM 格式）
     * <p>用于开发环境，生产环境应从安全位置加载密钥</p>
     *
     * @return PEM 格式的 RSA 公钥
     */
    public static String generateDefaultRsaPublicKey() {
        try {
            KeyPair keyPair = RSAAlgorithm.generateKeyPair();
            String publicKey = RSAAlgorithm.getPublicKeyPEM(keyPair.getPublic());
            logger.warn("Generated default RSA public key for development purposes. " +
                    "In production, configure secure keys via environment variables.");
            return publicKey;
        } catch (Exception e) {
            throw new CryptoException("Failed to generate default RSA public key", e);
        }
    }

    /**
     * 生成默认 RSA 私钥（PEM 格式）
     * <p>用于开发环境，生产环境应从安全位置加载密钥</p>
     *
     * @return PEM 格式的 RSA 私钥
     */
    public static String generateDefaultRsaPrivateKey() {
        try {
            KeyPair keyPair = RSAAlgorithm.generateKeyPair();
            String privateKey = RSAAlgorithm.getPrivateKeyPEM(keyPair.getPrivate());
            logger.warn("Generated default RSA private key for development purposes. " +
                    "In production, configure secure keys via environment variables.");
            return privateKey;
        } catch (Exception e) {
            throw new CryptoException("Failed to generate default RSA private key", e);
        }
    }

    /**
     * 生成 RSA 密钥对
     *
     * @param keySize 密钥长度（2048 或 4096）
     * @return 密钥对信息
     */
    public static RsaKeyPair generateRsaKeyPair(int keySize) {
        try {
            KeyPair keyPair = RSAAlgorithm.generateKeyPair(keySize);
            return new RsaKeyPair(
                    RSAAlgorithm.getPublicKeyPEM(keyPair.getPublic()),
                    RSAAlgorithm.getPrivateKeyPEM(keyPair.getPrivate())
            );
        } catch (Exception e) {
            throw new CryptoException("Failed to generate RSA key pair", e);
        }
    }

    /**
     * RSA 密钥对信息
     */
    public record RsaKeyPair(String publicKey, String privateKey) {}

    /**
     * 加载密钥内容
     *
     * @param keyPath     密钥文件路径（支持 classpath: 或 file: 前缀）
     * @param resourceLoader Spring ResourceLoader（可选）
     * @return 密钥内容，如果路径为空或文件不存在返回 null
     */
    public static String loadKey(String keyPath, ResourceLoader resourceLoader) {
        if (!StringUtils.hasText(keyPath)) {
            return null;
        }

        try {
            String content;

            if (keyPath.startsWith(CLASSPATH_PREFIX)) {
                // 从 classpath 加载
                String path = keyPath.substring(CLASSPATH_PREFIX.length());
                Resource resource = resourceLoader.getResource(keyPath);
                if (!resource.exists()) {
                    logger.warn("Key file not found in classpath: {}", path);
                    return null;
                }
                content = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            } else if (keyPath.startsWith(FILE_PREFIX)) {
                // 从文件系统加载
                Path path = Paths.get(keyPath.substring(FILE_PREFIX.length()));
                if (!Files.exists(path)) {
                    logger.warn("Key file not found: {}", path);
                    return null;
                }
                content = Files.readString(path, StandardCharsets.UTF_8);
            } else {
                // 默认从文件系统加载
                Path path = Paths.get(keyPath);
                if (!Files.exists(path)) {
                    // 尝试从 classpath 加载
                    try {
                        Resource resource = resourceLoader.getResource(CLASSPATH_PREFIX + keyPath);
                        if (resource.exists()) {
                            content = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
                        } else {
                            logger.warn("Key file not found: {}", path);
                            return null;
                        }
                    } catch (Exception e) {
                        logger.warn("Failed to load key from classpath: {}", keyPath);
                        return null;
                    }
                } else {
                    content = Files.readString(path, StandardCharsets.UTF_8);
                }
            }

            // 清理内容（去除 BOM、空白字符等）
            return cleanKeyContent(content);

        } catch (IOException e) {
            logger.error("Failed to load key file: {}", keyPath, e);
            return null;
        }
    }

    /**
     * 清理密钥内容
     * <p>去除首尾空白、注释、BOM 等</p>
     */
    private static String cleanKeyContent(String content) {
        if (content == null) {
            return null;
        }

        // 去除首尾空白
        content = content.trim();

        // 去除 UTF-8 BOM
        if (content.startsWith("\uFEFF")) {
            content = content.substring(1);
        }

        // 去除 PEM 格式的注释行（保留 BEGIN/END 标记）
        StringBuilder cleaned = new StringBuilder();
        for (String line : content.split("\n")) {
            String trimmedLine = line.trim();
            // 跳过空行和注释行
            if (!trimmedLine.isEmpty() && !trimmedLine.startsWith("#")) {
                cleaned.append(trimmedLine).append("\n");
            }
        }

        return cleaned.toString().trim();
    }

    /**
     * 检查密钥是否需要从文件加载
     */
    public static boolean needsFileLoad(String keyValue, String keyPath) {
        // 如果直接配置了密钥值，优先使用
        if (StringUtils.hasText(keyValue)) {
            return false;
        }
        // 如果配置了文件路径，需要从文件加载
        return StringUtils.hasText(keyPath);
    }
}
