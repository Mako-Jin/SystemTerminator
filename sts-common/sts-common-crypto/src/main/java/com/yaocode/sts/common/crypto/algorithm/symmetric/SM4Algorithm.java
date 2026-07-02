package com.yaocode.sts.common.crypto.algorithm.symmetric;

import com.yaocode.sts.common.crypto.algorithm.encode.Base64Algorithm;
import com.yaocode.sts.common.crypto.algorithm.hash.SM3Algorithm;
import com.yaocode.sts.common.crypto.constants.CryptoConstants;
import com.yaocode.sts.common.crypto.constants.CryptoI18nKeyConstants;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.SM4Engine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.modes.CBCModeCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

/**
 * SM4 国密对称加密算法工具类
 * <p>SM4是国家密码管理局发布的对称加密标准，密钥长度128位</p>
 * @author: Jin-LiangBo
 * @date: 2026年06月02日
 */
public final class SM4Algorithm {

    /**
     * SM4 算法名称
     */
    public static final String ALGORITHM_NAME = CryptoConstants.SM4;

    /**
     * SM4 密钥长度（16字节）
     */
    public static final int KEY_LENGTH = CryptoConstants.SM4_KEY_SIZE;

    /**
     * SM4 块大小（16字节）
     */
    public static final int BLOCK_SIZE = CryptoConstants.SM4_BLOCK_SIZE;

    /**
     * SM4 IV 长度（16字节）
     */
    public static final int IV_LENGTH = CryptoConstants.AES_GCM_IV_LENGTH;

    /**
     * 安全随机数生成器
     */
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    /**
     * 私有构造函数，防止实例化
     */
    private SM4Algorithm() {
    }

    // ==================== SM4 加密/解密 ====================

    /**
     * SM4-CBC 加密（返回 Base64）
     * @param plaintext 明文
     * @param key 16字节密钥
     * @return Base64 编码的密文（包含IV）
     */
    public static String encrypt(String plaintext, byte[] key) {
        validateKey(key);
        // 生成随机IV
        byte[] iv = generateIV();
        byte[] encrypted = encrypt(plaintext.getBytes(StandardCharsets.UTF_8), key, iv);

        // 组合 IV + 密文
        byte[] result = new byte[iv.length + encrypted.length];
        System.arraycopy(iv, 0, result, 0, iv.length);
        System.arraycopy(encrypted, 0, result, iv.length, encrypted.length);

        return Base64Algorithm.encryptByBase64(result);
    }

    /**
     * SM4-CBC 解密
     * @param ciphertext Base64 编码的密文（包含IV）
     * @param key 16字节密钥
     * @return 明文
     */
    public static String decrypt(String ciphertext, byte[] key) {
        validateKey(key);
        byte[] data = Base64Algorithm.decryptByBase64(ciphertext);

        // 分离 IV 和密文
        byte[] iv = new byte[IV_LENGTH];
        byte[] encrypted = new byte[data.length - IV_LENGTH];
        System.arraycopy(data, 0, iv, 0, IV_LENGTH);
        System.arraycopy(data, IV_LENGTH, encrypted, 0, encrypted.length);

        byte[] decrypted = decrypt(encrypted, key, iv);
        return new String(decrypted, StandardCharsets.UTF_8);
    }

    /**
     * SM4-CBC 加密（字节数组）
     * @param plaintext 明文字节数组
     * @param key 16字节密钥
     * @param iv 16字节初始化向量
     * @return 密文字节数组
     */
    public static byte[] encrypt(byte[] plaintext, byte[] key, byte[] iv) {
        try {
            PaddedBufferedBlockCipher cipher = createCipher(true, key, iv);
            return process(cipher, plaintext);
        } catch (Exception e) {
            throw new IllegalArgumentException(CryptoI18nKeyConstants.ERR_SM4_ENCRYPT_FAILED, e);
        }
    }

    /**
     * SM4-CBC 解密（字节数组）
     * @param ciphertext 密文字节数组
     * @param key 16字节密钥
     * @param iv 16字节初始化向量
     * @return 明文字节数组
     */
    public static byte[] decrypt(byte[] ciphertext, byte[] key, byte[] iv) {
        try {
            PaddedBufferedBlockCipher cipher = createCipher(false, key, iv);
            return process(cipher, ciphertext);
        } catch (Exception e) {
            throw new IllegalArgumentException(CryptoI18nKeyConstants.ERR_SM4_DECRYPT_FAILED, e);
        }
    }

    // ==================== 密钥和IV生成 ====================

    /**
     * 生成随机 SM4 密钥（16字节）
     * @return 16字节密钥
     */
    public static byte[] generateKey() {
        byte[] key = new byte[KEY_LENGTH];
        SECURE_RANDOM.nextBytes(key);
        return key;
    }

    /**
     * 生成随机 IV（16字节）
     * @return 16字节初始化向量
     */
    public static byte[] generateIV() {
        byte[] iv = new byte[IV_LENGTH];
        SECURE_RANDOM.nextBytes(iv);
        return iv;
    }

    /**
     * 从密码派生 SM4 密钥
     * @param password 密码
     * @return 16字节密钥
     */
    public static byte[] deriveKey(String password) {
        byte[] hash = SM3Algorithm.digest(password);
        byte[] key = new byte[KEY_LENGTH];
        System.arraycopy(hash, 0, key, 0, KEY_LENGTH);
        return key;
    }

    // ==================== 辅助方法 ====================

    /**
     * 创建 SM4-CBC 加解密器
     */
    private static PaddedBufferedBlockCipher createCipher(boolean encryptMode, byte[] key, byte[] iv) {
        BlockCipher engine = new SM4Engine();
        CBCModeCipher cbcEngine = CBCBlockCipher.newInstance(engine);

        KeyParameter keyParam = new KeyParameter(key);
        CipherParameters params = new ParametersWithIV(keyParam, iv);

        PaddedBufferedBlockCipher cipher = new PaddedBufferedBlockCipher(cbcEngine);
        cipher.init(encryptMode, params);

        return cipher;
    }

    /**
     * 执行加解密操作
     */
    private static byte[] process(PaddedBufferedBlockCipher cipher, byte[] input) throws InvalidCipherTextException {
        int outputSize = cipher.getOutputSize(input.length);
        byte[] output = new byte[outputSize];
        int length = cipher.processBytes(input, 0, input.length, output, 0);
        length += cipher.doFinal(output, length);

        // 移除填充
        if (length < outputSize) {
            byte[] result = new byte[length];
            System.arraycopy(output, 0, result, 0, length);
            return result;
        }
        return output;
    }

    /**
     * 验证密钥长度
     */
    private static void validateKey(byte[] key) {
        if (key == null || key.length != KEY_LENGTH) {
            throw new IllegalArgumentException(
                String.format(CryptoI18nKeyConstants.ERR_SM4_KEY_LENGTH_INVALID));
        }
    }
}
