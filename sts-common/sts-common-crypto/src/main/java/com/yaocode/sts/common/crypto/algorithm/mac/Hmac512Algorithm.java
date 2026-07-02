package com.yaocode.sts.common.crypto.algorithm.mac;

import com.yaocode.sts.common.crypto.algorithm.encode.Base64Algorithm;
import com.yaocode.sts.common.crypto.constants.CryptoConstants;
import com.yaocode.sts.common.crypto.constants.CryptoI18nKeyConstants;
import com.yaocode.sts.common.crypto.exception.CryptoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public class Hmac512Algorithm {

    private static final Logger log = LoggerFactory.getLogger(Hmac512Algorithm.class);

    /**
     * HMAC-SHA512 算法名称
     */
    public static final String HMAC_SHA512 = CryptoConstants.HMAC_SHA512;

    /**
     * HMAC-SHA512 输出长度（字节）
     */
    public static final int OUTPUT_LENGTH = CryptoConstants.HMAC_SHA512_OUTPUT_LENGTH;

    /**
     * 推荐的最小密钥长度（字节）
     * HMAC-SHA512 推荐密钥长度 >= 输出长度
     */
    public static final int RECOMMENDED_KEY_LENGTH = CryptoConstants.HMAC_SHA512_RECOMMENDED_KEY_LENGTH;

    /**
     * 最小的密钥长度（字节）
     */
    public static final int MIN_KEY_LENGTH = CryptoConstants.HMAC_SHA512_MIN_KEY_LENGTH;

    /**
     * ThreadLocal 缓存 Mac 实例（性能优化）
     */
    private static final ThreadLocal<Mac> MAC_CACHE = ThreadLocal.withInitial(() -> {
        try {
            return Mac.getInstance(HMAC_SHA512);
        } catch (NoSuchAlgorithmException e) {
            throw new CryptoException(CryptoI18nKeyConstants.ERR_HMAC_ALGORITHM_UNAVAILABLE, e);
        }
    });

    /**
     * 私有构造函数，防止实例化
     */
    private Hmac512Algorithm() {
    }

    // ==================== 密钥处理 ====================

    /**
     * 验证并处理密钥（长度不足时进行填充）
     * @param key 原始密钥
     * @return 处理后的密钥
     */
    private static byte[] normalizeKey(byte[] key) {
        if (key == null || key.length == 0) {
            throw new CryptoException(CryptoI18nKeyConstants.ERR_KEY_EMPTY);
        }

        // 密钥太短时记录警告
        if (key.length < MIN_KEY_LENGTH) {
            log.warn("密钥长度 {} 字节小于建议的最小长度 {} 字节，安全性可能不足",
                    key.length, MIN_KEY_LENGTH);
        }

        // 如果密钥长度超过推荐长度，直接返回
        if (key.length >= RECOMMENDED_KEY_LENGTH) {
            return key;
        }

        // 密钥长度不足时，使用 SHA-512 扩展密钥（推荐做法）
        // 这样确保密钥至少有 64 字节
        try {
            MessageDigest digest = MessageDigest.getInstance(CryptoConstants.SHA_512);
            byte[] extendedKey = digest.digest(key);
            log.debug("密钥长度从 {} 字节扩展到 {} 字节", key.length, extendedKey.length);
            return extendedKey;
        } catch (NoSuchAlgorithmException e) {
            log.warn("无法扩展密钥，使用原始密钥");
            return key;
        }
    }

    /**
     * 获取 Mac 实例（线程安全）
     */
    private static Mac getMac(byte[] key) {
        try {
            byte[] normalizedKey = normalizeKey(key);
            SecretKeySpec keySpec = new SecretKeySpec(normalizedKey, HMAC_SHA512);
            Mac mac = MAC_CACHE.get();
            // 注意：Mac 实例不能共享，需要重新初始化
            mac.init(keySpec);
            return mac;
        } catch (InvalidKeyException e) {
            throw new CryptoException(CryptoI18nKeyConstants.ERR_KEY_INVALID, e);
        }
    }

    // ==================== 基础方法 ====================

    /**
     * 计算 HMAC-SHA512，返回字节数组
     * @param key 密钥
     * @param data 待认证的数据
     * @return HMAC 值（64字节）
     */
    public static byte[] hmac(byte[] key, byte[] data) {
        if (data == null) {
            throw new CryptoException(CryptoI18nKeyConstants.ERR_HMAC_DATA_EMPTY);
        }
        Mac mac = getMac(key);
        return mac.doFinal(data);
    }

    /**
     * 计算 HMAC-SHA512，返回字节数组
     * @param key 密钥
     * @param data 待认证的字符串
     * @return HMAC 值（64字节）
     */
    public static byte[] hmac(byte[] key, String data) {
        return hmac(key, data != null ? data.getBytes(StandardCharsets.UTF_8) : null);
    }

    /**
     * 计算 HMAC-SHA512，返回字节数组
     * @param key 密钥字符串
     * @param data 待认证的字符串
     * @return HMAC 值（64字节）
     */
    public static byte[] hmac(String key, String data) {
        return hmac(key != null ? key.getBytes(StandardCharsets.UTF_8) : null, data);
    }

    // ==================== 十六进制输出 ====================

    /**
     * 计算 HMAC-SHA512，返回十六进制字符串（小写）
     * @param key 密钥
     * @param data 待认证的数据
     * @return 十六进制 HMAC 值
     */
    public static String hmacHex(byte[] key, byte[] data) {
        byte[] hmac = hmac(key, data);
        return HexFormat.of().formatHex(hmac);
    }

    /**
     * 计算 HMAC-SHA512，返回十六进制字符串（大写）
     * @param key 密钥
     * @param data 待认证的数据
     * @return 大写十六进制 HMAC 值
     */
    public static String hmacHexUpper(byte[] key, byte[] data) {
        byte[] hmac = hmac(key, data);
        return HexFormat.of().withUpperCase().formatHex(hmac);
    }

    /**
     * 计算 HMAC-SHA512，返回十六进制字符串
     * @param key 密钥
     * @param data 待认证的字符串
     * @return 十六进制 HMAC 值
     */
    public static String hmacHex(byte[] key, String data) {
        return hmacHex(key, data.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 计算 HMAC-SHA512，返回十六进制字符串
     * @param key 密钥字符串
     * @param data 待认证的字符串
     * @return 十六进制 HMAC 值
     */
    public static String hmacHex(String key, String data) {
        return hmacHex(key.getBytes(StandardCharsets.UTF_8), data);
    }

    // ==================== Base64 输出 ====================

    /**
     * 计算 HMAC-SHA512，返回 Base64 编码字符串
     * @param key 密钥
     * @param data 待认证的数据
     * @return Base64 编码的 HMAC 值
     */
    public static String hmacBase64(byte[] key, byte[] data) {
        byte[] hmac = hmac(key, data);
        return Base64Algorithm.encryptByBase64(hmac);
    }

    /**
     * 计算 HMAC-SHA512，返回 Base64 编码字符串
     * @param key 密钥
     * @param data 待认证的字符串
     * @return Base64 编码的 HMAC 值
     */
    public static String hmacBase64(byte[] key, String data) {
        return hmacBase64(key, data.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 计算 HMAC-SHA512，返回 Base64 编码字符串
     * @param key 密钥字符串
     * @param data 待认证的字符串
     * @return Base64 编码的 HMAC 值
     */
    public static String hmacBase64(String key, String data) {
        return hmacBase64(key.getBytes(StandardCharsets.UTF_8), data);
    }

    // ==================== URL 安全 Base64 输出 ====================

    /**
     * 计算 HMAC-SHA512，返回 URL 安全的 Base64 编码字符串
     * @param key 密钥
     * @param data 待认证的数据
     * @return URL 安全的 Base64 编码的 HMAC 值
     */
    public static String hmacBase64Url(byte[] key, byte[] data) {
        byte[] hmac = hmac(key, data);
        return Base64Algorithm.encodeUrlSafe(hmac);
    }

    /**
     * 计算 HMAC-SHA512，返回 URL 安全的 Base64 编码字符串
     * @param key 密钥
     * @param data 待认证的字符串
     * @return URL 安全的 Base64 编码的 HMAC 值
     */
    public static String hmacBase64Url(byte[] key, String data) {
        return hmacBase64Url(key, data.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 计算 HMAC-SHA512，返回 URL 安全的 Base64 编码字符串
     * @param key 密钥字符串
     * @param data 待认证的字符串
     * @return URL 安全的 Base64 编码的 HMAC 值
     */
    public static String hmacBase64Url(String key, String data) {
        return hmacBase64Url(key.getBytes(StandardCharsets.UTF_8), data);
    }

    // ==================== 验证方法 ====================

    /**
     * 验证 HMAC-SHA512 签名（常量时间比较）
     * @param key 密钥
     * @param data 原始数据
     * @param expectedHmac 期望的 HMAC 值
     * @return 是否匹配
     */
    public static boolean verify(byte[] key, byte[] data, byte[] expectedHmac) {
        if (expectedHmac == null || expectedHmac.length != OUTPUT_LENGTH) {
            return false;
        }
        byte[] actualHmac = hmac(key, data);
        return MessageDigest.isEqual(actualHmac, expectedHmac);
    }

    /**
     * 验证 HMAC-SHA512 签名（十六进制格式）
     * @param key 密钥
     * @param data 原始字符串
     * @param expectedHmacHex 期望的十六进制 HMAC 值
     * @return 是否匹配
     */
    public static boolean verifyHex(byte[] key, String data, String expectedHmacHex) {
        if (expectedHmacHex == null || expectedHmacHex.length() != OUTPUT_LENGTH * 2) {
            return false;
        }
        try {
            byte[] expectedHmac = HexFormat.of().parseHex(expectedHmacHex);
            return verify(key, data.getBytes(StandardCharsets.UTF_8), expectedHmac);
        } catch (IllegalArgumentException e) {
            log.debug("无效的十六进制字符串: {}", expectedHmacHex);
            return false;
        }
    }

    /**
     * 验证 HMAC-SHA512 签名（Base64 格式）
     * @param key 密钥
     * @param data 原始字符串
     * @param expectedHmacBase64 期望的 Base64 编码 HMAC 值
     * @return 是否匹配
     */
    public static boolean verifyBase64(byte[] key, String data, String expectedHmacBase64) {
        if (expectedHmacBase64 == null || expectedHmacBase64.isEmpty()) {
            return false;
        }
        try {
            byte[] expectedHmac = Base64Algorithm.decryptByBase64(expectedHmacBase64);
            if (expectedHmac.length != OUTPUT_LENGTH) {
                return false;
            }
            return verify(key, data.getBytes(StandardCharsets.UTF_8), expectedHmac);
        } catch (Exception e) {
            log.debug("无效的 Base64 字符串: {}", expectedHmacBase64);
            return false;
        }
    }

    // ==================== 辅助方法 ====================

    /**
     * 生成随机密钥（推荐长度）
     * @return 64字节随机密钥
     */
    public static byte[] generateRandomKey() {
        byte[] key = new byte[RECOMMENDED_KEY_LENGTH];
        // 使用 SecureRandom 填充
        java.security.SecureRandom random = new java.security.SecureRandom();
        random.nextBytes(key);
        return key;
    }

    /**
     * 生成随机密钥（指定长度）
     * @param length 密钥长度（字节）
     * @return 随机密钥
     */
    public static byte[] generateRandomKey(int length) {
        if (length < MIN_KEY_LENGTH) {
            log.warn("请求的密钥长度 {} 小于推荐的最小长度 {}", length, MIN_KEY_LENGTH);
        }
        byte[] key = new byte[length];
        java.security.SecureRandom random = new java.security.SecureRandom();
        random.nextBytes(key);
        return key;
    }
}