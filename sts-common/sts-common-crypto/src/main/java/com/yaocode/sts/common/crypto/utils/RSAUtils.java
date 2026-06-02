package com.yaocode.sts.common.crypto.utils;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * RSA 非对称加密工具类（安全版本）
 * <p>支持：RSA-2048/4096、SHA-256/SHA-512 签名、OAEP 填充</p>
 * @author: Jin-LiangBo
 * @date: 2026年06月01日
 */
public final class RSAUtils {

    /**
     * RSA 算法名称
     */
    public static final String RSA = "RSA";

    /**
     * 默认密钥长度（2048位，最小安全要求）
     */
    public static final int DEFAULT_KEY_SIZE = 2048;

    /**
     * 推荐密钥长度（4096位，更高安全性）
     */
    public static final int RECOMMENDED_KEY_SIZE = 4096;

    /**
     * RSA OAEP 加密模式（更安全的填充方式）
     */
    public static final String RSA_OAEP = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";

    /**
     * RSA PKCS#1 v1.5 加密模式（兼容旧系统）
     */
    public static final String RSA_PKCS1 = "RSA/ECB/PKCS1Padding";

    /**
     * SHA-256 with RSA 签名算法
     */
    public static final String SIGNATURE_SHA256_RSA = "SHA256withRSA";

    /**
     * SHA-512 with RSA 签名算法
     */
    public static final String SIGNATURE_SHA512_RSA = "SHA512withRSA";

    /**
     * PEM 格式常量
     */
    private static final String LINE_SEPARATOR = "\n";
    private static final int PEM_LINE_LENGTH = 64;

    /**
     * 私有构造函数，防止实例化
     */
    private RSAUtils() {
    }

    // ==================== 密钥对生成 ====================

    /**
     * 生成 RSA 密钥对（默认2048位）
     * @return 密钥对
     * @throws Exception 异常
     */
    public static KeyPair generateKeyPair() throws Exception {
        return generateKeyPair(DEFAULT_KEY_SIZE);
    }

    /**
     * 生成 RSA 密钥对
     * @param keySize 密钥长度（推荐2048或4096）
     * @return 密钥对
     * @throws Exception 异常
     */
    public static KeyPair generateKeyPair(int keySize) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA);
        keyPairGenerator.initialize(keySize, new SecureRandom());
        return keyPairGenerator.generateKeyPair();
    }

    // ==================== 密钥转换 ====================

    /**
     * 从字节数组构建公钥
     * @param keyBytes 公钥字节数组
     * @return 公钥对象
     * @throws Exception 异常
     */
    public static PublicKey getPublicKey(byte[] keyBytes) throws Exception {
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        return keyFactory.generatePublic(spec);
    }

    /**
     * 从字节数组构建私钥
     * @param keyBytes 私钥字节数组
     * @return 私钥对象
     * @throws Exception 异常
     */
    public static PrivateKey getPrivateKey(byte[] keyBytes) throws Exception {
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        return keyFactory.generatePrivate(spec);
    }

    /**
     * 从 Base64 字符串构建公钥
     * @param base64Key Base64 编码的公钥
     * @return 公钥对象
     * @throws Exception 异常
     */
    public static PublicKey getPublicKeyFromBase64(String base64Key) throws Exception {
        byte[] keyBytes = Base64Utils.decryptByBase64(base64Key);
        return getPublicKey(keyBytes);
    }

    /**
     * 从 Base64 字符串构建私钥
     * @param base64Key Base64 编码的私钥
     * @return 私钥对象
     * @throws Exception 异常
     */
    public static PrivateKey getPrivateKeyFromBase64(String base64Key) throws Exception {
        byte[] keyBytes = Base64Utils.decryptByBase64(base64Key);
        return getPrivateKey(keyBytes);
    }

    // ==================== 加密/解密（OAEP模式，推荐） ====================

    /**
     * 公钥加密（OAEP模式，更安全）
     * @param plaintext 明文
     * @param publicKey 公钥
     * @return Base64 编码的密文
     * @throws Exception 异常
     */
    public static String encryptByPublicKeyOAEP(String plaintext, PublicKey publicKey) throws Exception {
        byte[] encrypted = encryptByPublicKeyOAEP(plaintext.getBytes(StandardCharsets.UTF_8), publicKey);
        return Base64Utils.encryptByBase64(encrypted);
    }

    /**
     * 公钥加密（OAEP模式）
     * @param plaintext 明文字节数组
     * @param publicKey 公钥
     * @return 密文字节数组
     * @throws Exception 异常
     */
    public static byte[] encryptByPublicKeyOAEP(byte[] plaintext, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA_OAEP);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(plaintext);
    }

    /**
     * 私钥解密（OAEP模式）
     * @param ciphertext Base64 编码的密文
     * @param privateKey 私钥
     * @return 明文
     * @throws Exception 异常
     */
    public static String decryptByPrivateKeyOAEP(String ciphertext, PrivateKey privateKey) throws Exception {
        byte[] decrypted = decryptByPrivateKeyOAEP(Base64Utils.decryptByBase64(ciphertext), privateKey);
        return new String(decrypted, StandardCharsets.UTF_8);
    }

    /**
     * 私钥解密（OAEP模式）
     * @param ciphertext 密文字节数组
     * @param privateKey 私钥
     * @return 明文字节数组
     * @throws Exception 异常
     */
    public static byte[] decryptByPrivateKeyOAEP(byte[] ciphertext, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA_OAEP);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(ciphertext);
    }

    // ==================== 加密/解密（PKCS#1模式，兼容旧系统） ====================

    /**
     * 公钥加密（PKCS#1模式，兼容旧系统）
     * @param plaintext 明文
     * @param publicKey 公钥
     * @return Base64 编码的密文
     * @throws Exception 异常
     */
    public static String encryptByPublicKeyPKCS1(String plaintext, PublicKey publicKey) throws Exception {
        byte[] encrypted = encryptByPublicKeyPKCS1(plaintext.getBytes(StandardCharsets.UTF_8), publicKey);
        return Base64Utils.encryptByBase64(encrypted);
    }

    /**
     * 公钥加密（PKCS#1模式）
     * @param plaintext 明文字节数组
     * @param publicKey 公钥
     * @return 密文字节数组
     * @throws Exception 异常
     */
    public static byte[] encryptByPublicKeyPKCS1(byte[] plaintext, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA_PKCS1);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(plaintext);
    }

    /**
     * 私钥解密（PKCS#1模式）
     * @param ciphertext Base64 编码的密文
     * @param privateKey 私钥
     * @return 明文
     * @throws Exception 异常
     */
    public static String decryptByPrivateKeyPKCS1(String ciphertext, PrivateKey privateKey) throws Exception {
        byte[] decrypted = decryptByPrivateKeyPKCS1(Base64Utils.decryptByBase64(ciphertext), privateKey);
        return new String(decrypted, StandardCharsets.UTF_8);
    }

    /**
     * 私钥解密（PKCS#1模式）
     * @param ciphertext 密文字节数组
     * @param privateKey 私钥
     * @return 明文字节数组
     * @throws Exception 异常
     */
    public static byte[] decryptByPrivateKeyPKCS1(byte[] ciphertext, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA_PKCS1);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(ciphertext);
    }

    // ==================== 签名/验签 ====================

    /**
     * 使用私钥签名（SHA-256）
     * @param data 待签名数据
     * @param privateKey 私钥
     * @return Base64 编码的签名
     * @throws Exception 异常
     */
    public static String signSHA256(byte[] data, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance(SIGNATURE_SHA256_RSA);
        signature.initSign(privateKey);
        signature.update(data);
        return Base64Utils.encryptByBase64(signature.sign());
    }

    /**
     * 使用私钥签名（SHA-256）
     * @param data 待签名字符串
     * @param privateKey 私钥
     * @return Base64 编码的签名
     * @throws Exception 异常
     */
    public static String signSHA256(String data, PrivateKey privateKey) throws Exception {
        return signSHA256(data.getBytes(StandardCharsets.UTF_8), privateKey);
    }

    /**
     * 使用私钥签名（SHA-512）
     * @param data 待签名数据
     * @param privateKey 私钥
     * @return Base64 编码的签名
     * @throws Exception 异常
     */
    public static String signSHA512(byte[] data, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance(SIGNATURE_SHA512_RSA);
        signature.initSign(privateKey);
        signature.update(data);
        return Base64Utils.encryptByBase64(signature.sign());
    }

    /**
     * 使用私钥签名（SHA-512）
     * @param data 待签名字符串
     * @param privateKey 私钥
     * @return Base64 编码的签名
     * @throws Exception 异常
     */
    public static String signSHA512(String data, PrivateKey privateKey) throws Exception {
        return signSHA512(data.getBytes(StandardCharsets.UTF_8), privateKey);
    }

    /**
     * 验证签名（SHA-256）
     * @param data 原始数据
     * @param signatureStr Base64 编码的签名
     * @param publicKey 公钥
     * @return 验证结果
     * @throws Exception 异常
     */
    public static boolean verifySHA256(byte[] data, String signatureStr, PublicKey publicKey) throws Exception {
        Signature signature = Signature.getInstance(SIGNATURE_SHA256_RSA);
        signature.initVerify(publicKey);
        signature.update(data);
        byte[] sigBytes = Base64Utils.decryptByBase64(signatureStr);
        return signature.verify(sigBytes);
    }

    /**
     * 验证签名（SHA-256）
     * @param data 原始字符串
     * @param signatureStr Base64 编码的签名
     * @param publicKey 公钥
     * @return 验证结果
     * @throws Exception 异常
     */
    public static boolean verifySHA256(String data, String signatureStr, PublicKey publicKey) throws Exception {
        return verifySHA256(data.getBytes(StandardCharsets.UTF_8), signatureStr, publicKey);
    }

    /**
     * 验证签名（SHA-512）
     * @param data 原始数据
     * @param signatureStr Base64 编码的签名
     * @param publicKey 公钥
     * @return 验证结果
     * @throws Exception 异常
     */
    public static boolean verifySHA512(byte[] data, String signatureStr, PublicKey publicKey) throws Exception {
        Signature signature = Signature.getInstance(SIGNATURE_SHA512_RSA);
        signature.initVerify(publicKey);
        signature.update(data);
        byte[] sigBytes = Base64Utils.decryptByBase64(signatureStr);
        return signature.verify(sigBytes);
    }

    /**
     * 验证签名（SHA-512）
     * @param data 原始字符串
     * @param signatureStr Base64 编码的签名
     * @param publicKey 公钥
     * @return 验证结果
     * @throws Exception 异常
     */
    public static boolean verifySHA512(String data, String signatureStr, PublicKey publicKey) throws Exception {
        return verifySHA512(data.getBytes(StandardCharsets.UTF_8), signatureStr, publicKey);
    }

    // ==================== PEM 格式转换 ====================

    /**
     * 获取公钥的 PEM 格式
     * @param publicKey 公钥
     * @return PEM 格式字符串
     */
    public static String getPublicKeyPEM(PublicKey publicKey) {
        return formatPEM("-----BEGIN PUBLIC KEY-----", "-----END PUBLIC KEY-----",
                publicKey.getEncoded());
    }

    /**
     * 获取私钥的 PEM 格式（PKCS#8）
     * @param privateKey 私钥
     * @return PEM 格式字符串
     */
    public static String getPrivateKeyPEM(PrivateKey privateKey) {
        return formatPEM("-----BEGIN PRIVATE KEY-----", "-----END PRIVATE KEY-----",
                privateKey.getEncoded());
    }

    /**
     * 格式化字节数组为 PEM 格式
     * @param beginMarker 开始标记
     * @param endMarker 结束标记
     * @param encoded 编码后的字节数组
     * @return PEM 格式字符串
     */
    private static String formatPEM(String beginMarker, String endMarker, byte[] encoded) {
        String base64 = Base64.getMimeEncoder(PEM_LINE_LENGTH, LINE_SEPARATOR.getBytes())
                .encodeToString(encoded);
        return beginMarker + LINE_SEPARATOR + base64 + LINE_SEPARATOR + endMarker + LINE_SEPARATOR;
    }
}
