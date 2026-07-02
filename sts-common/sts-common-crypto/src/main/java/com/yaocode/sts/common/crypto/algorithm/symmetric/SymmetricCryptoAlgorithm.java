package com.yaocode.sts.common.crypto.algorithm.symmetric;

import com.yaocode.sts.common.crypto.algorithm.encode.Base64Algorithm;
import com.yaocode.sts.common.crypto.algorithm.hash.DigestAlgorithm;
import com.yaocode.sts.common.crypto.constants.CryptoConstants;
import com.yaocode.sts.common.crypto.constants.CryptoI18nKeyConstants;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

/**
 * 对称加密工具类（仅包含安全的 AES 算法）
 * <p>支持：AES-128、AES-192、AES-256（GCM模式，提供认证加密）</p>
 * @author: Jin-LiangBo
 * @date: 2026年06月01日
 */
public final class SymmetricCryptoAlgorithm {

    /**
     * AES 算法名称
     */
    public static final String AES = CryptoConstants.AES;

    /**
     * AES-GCM 算法模式（提供认证加密，防止篡改）
     */
    public static final String AES_GCM = CryptoConstants.AES_GCM;

    /**
     * GCM 认证标签长度（128位）
     */
    private static final int GCM_TAG_LENGTH = CryptoConstants.AES_GCM_TAG_LENGTH;

    /**
     * GCM IV 长度（12字节，推荐值）
     */
    private static final int GCM_IV_LENGTH = CryptoConstants.AES_GCM_IV_LENGTH;

    /**
     * 安全随机数生成器
     */
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    /**
     * 私有构造函数，防止实例化
     */
    private SymmetricCryptoAlgorithm() {
    }

    // ==================== AES 加密/解密方法 ====================

    /**
     * AES-128 加密（密钥长度：16字节）
     * @param plaintext 明文
     * @param key 16字节密钥
     * @return Base64 编码的密文（包含IV）
     */
    public static String aes128Encrypt(String plaintext, byte[] key) {
        validateKeyLength(key, CryptoConstants.AES_128_KEY_SIZE);
        return aesGcmEncrypt(plaintext.getBytes(StandardCharsets.UTF_8), key);
    }

    /**
     * AES-128 解密（密钥长度：16字节）
     * @param ciphertext Base64 编码的密文
     * @param key 16字节密钥
     * @return 明文
     */
    public static String aes128Decrypt(String ciphertext, byte[] key) {
        validateKeyLength(key, CryptoConstants.AES_128_KEY_SIZE);
        byte[] decrypted = aesGcmDecrypt(ciphertext, key);
        return new String(decrypted, StandardCharsets.UTF_8);
    }

    /**
     * AES-256 加密（密钥长度：32字节）
     * @param plaintext 明文
     * @param key 32字节密钥
     * @return Base64 编码的密文（包含IV）
     */
    public static String aes256Encrypt(String plaintext, byte[] key) {
        validateKeyLength(key, CryptoConstants.AES_256_KEY_SIZE);
        return aesGcmEncrypt(plaintext.getBytes(StandardCharsets.UTF_8), key);
    }

    /**
     * AES-256 解密（密钥长度：32字节）
     * @param ciphertext Base64 编码的密文
     * @param key 32字节密钥
     * @return 明文
     */
    public static String aes256Decrypt(String ciphertext, byte[] key) {
        validateKeyLength(key, CryptoConstants.AES_256_KEY_SIZE);
        byte[] decrypted = aesGcmDecrypt(ciphertext, key);
        return new String(decrypted, StandardCharsets.UTF_8);
    }

    /**
     * AES-GCM 加密（通用方法）
     * @param plaintext 明文字节数组
     * @param key 密钥（16/24/32字节对应AES-128/192/256）
     * @return Base64 编码的密文（格式：IV + 密文 + 认证标签）
     */
    public static String aesGcmEncrypt(byte[] plaintext, byte[] key) {
        try {
            // 生成随机IV
            byte[] iv = new byte[GCM_IV_LENGTH];
            SECURE_RANDOM.nextBytes(iv);

            // 初始化加密器
            Cipher cipher = Cipher.getInstance(AES_GCM);
            SecretKeySpec keySpec = new SecretKeySpec(key, AES);
            GCMParameterSpec gcmSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmSpec);

            // 加密
            byte[] encrypted = cipher.doFinal(plaintext);

            // 组合 IV + 密文（GCM模式下，认证标签已包含在encrypted末尾）
            byte[] result = new byte[iv.length + encrypted.length];
            System.arraycopy(iv, 0, result, 0, iv.length);
            System.arraycopy(encrypted, 0, result, iv.length, encrypted.length);

            return Base64Algorithm.encryptByBase64(result);
        } catch (Exception e) {
            throw new IllegalArgumentException(CryptoI18nKeyConstants.ERR_AES_GCM_ENCRYPT_FAILED, e);
        }
    }

    /**
     * AES-GCM 解密（通用方法）
     * @param ciphertext Base64 编码的密文
     * @param key 密钥（16/24/32字节对应AES-128/192/256）
     * @return 明文字节数组
     */
    public static byte[] aesGcmDecrypt(String ciphertext, byte[] key) {
        try {
            // 解码密文
            byte[] data = Base64Algorithm.decryptByBase64(ciphertext);

            // 分离 IV 和密文
            byte[] iv = new byte[GCM_IV_LENGTH];
            byte[] encrypted = new byte[data.length - GCM_IV_LENGTH];
            System.arraycopy(data, 0, iv, 0, GCM_IV_LENGTH);
            System.arraycopy(data, GCM_IV_LENGTH, encrypted, 0, encrypted.length);

            // 初始化解密器
            Cipher cipher = Cipher.getInstance(AES_GCM);
            SecretKeySpec keySpec = new SecretKeySpec(key, AES);
            GCMParameterSpec gcmSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmSpec);

            // 解密（同时验证认证标签）
            return cipher.doFinal(encrypted);
        } catch (Exception e) {
            throw new IllegalArgumentException(CryptoI18nKeyConstants.ERR_AES_GCM_DECRYPT_FAILED, e);
        }
    }

    // ==================== 密钥派生方法 ====================

    /**
     * 从密码派生 AES-256 密钥（使用 SHA-256 哈希）
     * @param password 密码
     * @return 32字节密钥
     */
    public static byte[] deriveAes256Key(String password) {
        return DigestAlgorithm.sha256Hex(password).getBytes(StandardCharsets.UTF_8);
    }

    /**
     * 从密码派生 AES-256 密钥（带盐值）
     * @param password 密码
     * @param salt 盐值（建议至少16字节）
     * @return 32字节密钥
     */
    public static byte[] deriveAes256Key(String password, byte[] salt) {
        byte[] saltedPassword = new byte[password.length() + salt.length];
        System.arraycopy(password.getBytes(StandardCharsets.UTF_8), 0, saltedPassword, 0, password.length());
        System.arraycopy(salt, 0, saltedPassword, password.length(), salt.length);
        return DigestAlgorithm.sha256Hex(new String(saltedPassword, StandardCharsets.UTF_8)).getBytes(StandardCharsets.UTF_8);
    }

    /**
     * 生成随机 AES-256 密钥
     * @return 32字节随机密钥
     */
    public static byte[] generateAes256Key() {
        byte[] key = new byte[CryptoConstants.AES_256_KEY_SIZE];
        SECURE_RANDOM.nextBytes(key);
        return key;
    }

    /**
     * 生成随机盐值
     * @param length 盐值长度（建议至少16字节）
     * @return 随机盐值
     */
    public static byte[] generateSalt(int length) {
        byte[] salt = new byte[length];
        SECURE_RANDOM.nextBytes(salt);
        return salt;
    }

    // ==================== 辅助方法 ====================

    /**
     * 验证密钥长度
     * @param key 密钥
     * @param expectedLength 期望长度
     */
    private static void validateKeyLength(byte[] key, int expectedLength) {
        if (key == null || key.length != expectedLength) {
            throw new IllegalArgumentException(
                String.format(CryptoI18nKeyConstants.ERR_KEY_LENGTH_INVALID));
        }
    }
}
