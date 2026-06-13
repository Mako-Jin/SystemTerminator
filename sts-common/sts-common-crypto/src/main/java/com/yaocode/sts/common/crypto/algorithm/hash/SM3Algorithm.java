package com.yaocode.sts.common.crypto.algorithm.hash;

import com.yaocode.sts.common.crypto.algorithm.encode.Base64Algorithm;
import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * SM3 国密哈希算法工具类
 * <p>SM3是国家密码管理局发布的哈希算法标准，输出长度为256位（32字节）</p>
 * @author: Jin-LiangBo
 * @date: 2026年06月01日
 */
public final class SM3Algorithm {

    /**
     * SM3 摘要长度（32字节）
     */
    public static final int DIGEST_LENGTH = 32;

    /**
     * 私有构造函数，防止实例化
     */
    private SM3Algorithm() {
    }

    // ==================== SM3 哈希计算 ====================

    /**
     * 计算 SM3 摘要值（字节数组输入）
     * @param data 原始数据
     * @return 摘要值（32字节）
     */
    public static byte[] digest(byte[] data) {
        SM3Digest digest = new SM3Digest();
        digest.update(data, 0, data.length);
        byte[] hash = new byte[digest.getDigestSize()];
        digest.doFinal(hash, 0);
        return hash;
    }

    /**
     * 计算 SM3 摘要值（字符串输入）
     * @param data 原始字符串
     * @return 摘要值（32字节）
     */
    public static byte[] digest(String data) {
        return digest(data.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 计算 SM3 摘要值，返回十六进制字符串
     * @param data 原始字符串
     * @return 十六进制摘要值
     */
    public static String digestHex(String data) {
        byte[] hash = digest(data);
        return bytesToHex(hash);
    }

    /**
     * 计算 SM3 摘要值，返回十六进制字符串
     * @param data 原始数据
     * @return 十六进制摘要值
     */
    public static String digestHex(byte[] data) {
        byte[] hash = digest(data);
        return bytesToHex(hash);
    }

    /**
     * 计算 SM3 摘要值，返回 Base64 编码字符串
     * @param data 原始字符串
     * @return Base64 编码摘要值
     */
    public static String digestBase64(String data) {
        byte[] hash = digest(data);
        return Base64Algorithm.encryptByBase64(hash);
    }

    /**
     * 计算 SM3 摘要值，返回 Base64 编码字符串
     * @param data 原始数据
     * @return Base64 编码摘要值
     */
    public static String digestBase64(byte[] data) {
        byte[] hash = digest(data);
        return Base64Algorithm.encryptByBase64(hash);
    }

    /**
     * 计算 SM3 摘要值，返回 URL 安全的 Base64 编码字符串
     * @param data 原始字符串
     * @return URL 安全的 Base64 编码摘要值
     */
    public static String digestBase64Url(String data) {
        byte[] hash = digest(data);
        return Base64Algorithm.encodeUrlSafe(hash);
    }

    // ==================== SM3 验证 ====================

    /**
     * 验证 SM3 摘要
     * @param data 原始数据
     * @param digest 待验证的摘要值
     * @return 验证结果
     */
    public static boolean verify(byte[] data, byte[] digest) {
        byte[] newHash = digest(data);
        return Arrays.equals(newHash, digest);
    }

    /**
     * 验证 SM3 摘要（字符串输入）
     * @param data 原始字符串
     * @param hexDigest 待验证的十六进制摘要值
     * @return 验证结果
     */
    public static boolean verifyHex(String data, String hexDigest) {
        byte[] expected = hexToBytes(hexDigest);
        byte[] actual = digest(data);
        return Arrays.equals(actual, expected);
    }

    // ==================== HMAC-SM3 ====================

    /**
     * 计算 HMAC-SM3 消息认证码
     * @param key 密钥（任意长度）
     * @param data 原始数据
     * @return HMAC 值（32字节）
     */
    public static byte[] hmac(byte[] key, byte[] data) {
        KeyParameter keyParameter = new KeyParameter(key);
        SM3Digest digest = new SM3Digest();
        HMac mac = new HMac(digest);
        mac.init(keyParameter);
        mac.update(data, 0, data.length);
        byte[] result = new byte[mac.getMacSize()];
        mac.doFinal(result, 0);
        return result;
    }

    /**
     * 计算 HMAC-SM3 消息认证码
     * @param key 密钥
     * @param data 原始字符串
     * @return HMAC 值（32字节）
     */
    public static byte[] hmac(byte[] key, String data) {
        return hmac(key, data.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 计算 HMAC-SM3 消息认证码，返回十六进制字符串
     * @param key 密钥
     * @param data 原始字符串
     * @return 十六进制 HMAC 值
     */
    public static String hmacHex(byte[] key, String data) {
        byte[] hmac = hmac(key, data);
        return bytesToHex(hmac);
    }

    /**
     * 计算 HMAC-SM3 消息认证码，返回十六进制字符串
     * @param key 密钥
     * @param data 原始数据
     * @return 十六进制 HMAC 值
     */
    public static String hmacHex(byte[] key, byte[] data) {
        byte[] hmac = hmac(key, data);
        return bytesToHex(hmac);
    }

    /**
     * 计算 HMAC-SM3 消息认证码，返回 Base64 编码字符串
     * @param key 密钥
     * @param data 原始字符串
     * @return Base64 编码 HMAC 值
     */
    public static String hmacBase64(byte[] key, String data) {
        byte[] hmac = hmac(key, data);
        return Base64Algorithm.encryptByBase64(hmac);
    }

    // ==================== 辅助方法 ====================

    /**
     * 字节数组转十六进制字符串
     * @param bytes 字节数组
     * @return 十六进制字符串
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    /**
     * 十六进制字符串转字节数组
     * @param hex 十六进制字符串
     * @return 字节数组
     */
    private static byte[] hexToBytes(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }
}
