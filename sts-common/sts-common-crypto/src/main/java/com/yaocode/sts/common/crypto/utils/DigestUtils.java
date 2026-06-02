package com.yaocode.sts.common.crypto.utils;

import com.yaocode.sts.common.crypto.exception.CryptoException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 消息摘要工具类（仅包含安全的哈希算法）
 * <p>支持：SHA-256、SHA-384、SHA-512</p>
 * @author: Jin-LiangBo
 * @date: 2026年06月01日
 */
public final class DigestUtils {

    /**
     * SHA-256 算法名称
     */
    public static final String SHA_256 = "SHA-256";

    /**
     * SHA-384 算法名称
     */
    public static final String SHA_384 = "SHA-384";

    /**
     * SHA-512 算法名称
     */
    public static final String SHA_512 = "SHA-512";

    /**
     * 私有构造函数，防止实例化
     */
    private DigestUtils() {
    }

    // ==================== SHA-256 ====================

    /**
     * SHA-256 哈希，返回十六进制字符串
     * @param input 输入字符串
     * @return 十六进制哈希值
     */
    public static String sha256Hex(String input) {
        return digestHex(input.getBytes(StandardCharsets.UTF_8), SHA_256);
    }

    /**
     * SHA-256 哈希，返回十六进制字符串
     * @param input 输入字节数组
     * @return 十六进制哈希值
     */
    public static String sha256Hex(byte[] input) {
        return digestHex(input, SHA_256);
    }

    /**
     * SHA-256 哈希，返回 Base64 编码字符串
     * @param input 输入字符串
     * @return Base64 编码哈希值
     */
    public static String sha256B64(String input) {
        return digestBase64(input.getBytes(StandardCharsets.UTF_8), SHA_256);
    }

    /**
     * SHA-256 哈希，返回 Base64 编码字符串
     * @param input 输入字节数组
     * @return Base64 编码哈希值
     */
    public static String sha256B64(byte[] input) {
        return digestBase64(input, SHA_256);
    }

    /**
     * SHA-256 哈希，返回 URL 安全的 Base64 编码字符串
     * @param input 输入字符串
     * @return URL 安全的 Base64 编码哈希值
     */
    public static String sha256B64Url(String input) {
        return digestBase64Url(input.getBytes(StandardCharsets.UTF_8), SHA_256);
    }

    // ==================== SHA-384 ====================

    /**
     * SHA-384 哈希，返回十六进制字符串
     * @param input 输入字符串
     * @return 十六进制哈希值
     */
    public static String sha384Hex(String input) {
        return digestHex(input.getBytes(StandardCharsets.UTF_8), SHA_384);
    }

    /**
     * SHA-384 哈希，返回十六进制字符串
     * @param input 输入字节数组
     * @return 十六进制哈希值
     */
    public static String sha384Hex(byte[] input) {
        return digestHex(input, SHA_384);
    }

    /**
     * SHA-384 哈希，返回 Base64 编码字符串
     * @param input 输入字符串
     * @return Base64 编码哈希值
     */
    public static String sha384B64(String input) {
        return digestBase64(input.getBytes(StandardCharsets.UTF_8), SHA_384);
    }

    /**
     * SHA-384 哈希，返回 Base64 编码字符串
     * @param input 输入字节数组
     * @return Base64 编码哈希值
     */
    public static String sha384B64(byte[] input) {
        return digestBase64(input, SHA_384);
    }

    /**
     * SHA-384 哈希，返回 URL 安全的 Base64 编码字符串
     * @param input 输入字符串
     * @return URL 安全的 Base64 编码哈希值
     */
    public static String sha384B64Url(String input) {
        return digestBase64Url(input.getBytes(StandardCharsets.UTF_8), SHA_384);
    }

    // ==================== SHA-512 ====================

    /**
     * SHA-512 哈希，返回十六进制字符串
     * @param input 输入字符串
     * @return 十六进制哈希值
     */
    public static String sha512Hex(String input) {
        return digestHex(input.getBytes(StandardCharsets.UTF_8), SHA_512);
    }

    /**
     * SHA-512 哈希，返回十六进制字符串
     * @param input 输入字节数组
     * @return 十六进制哈希值
     */
    public static String sha512Hex(byte[] input) {
        return digestHex(input, SHA_512);
    }

    /**
     * SHA-512 哈希，返回 Base64 编码字符串
     * @param input 输入字符串
     * @return Base64 编码哈希值
     */
    public static String sha512B64(String input) {
        return digestBase64(input.getBytes(StandardCharsets.UTF_8), SHA_512);
    }

    /**
     * SHA-512 哈希，返回 Base64 编码字符串
     * @param input 输入字节数组
     * @return Base64 编码哈希值
     */
    public static String sha512B64(byte[] input) {
        return digestBase64(input, SHA_512);
    }

    /**
     * SHA-512 哈希，返回 URL 安全的 Base64 编码字符串
     * @param input 输入字符串
     * @return URL 安全的 Base64 编码哈希值
     */
    public static String sha512B64Url(String input) {
        return digestBase64Url(input.getBytes(StandardCharsets.UTF_8), SHA_512);
    }

    // ==================== 底层方法 ====================

    /**
     * 计算消息摘要，返回十六进制字符串
     * @param input 输入字节数组
     * @param algorithm 算法名称
     * @return 十六进制哈希值
     */
    private static String digestHex(byte[] input, String algorithm) {
        byte[] digest = digest(input, algorithm);
        return bytesToHex(digest);
    }

    /**
     * 计算消息摘要，返回 Base64 编码字符串
     * @param input 输入字节数组
     * @param algorithm 算法名称
     * @return Base64 编码哈希值
     */
    private static String digestBase64(byte[] input, String algorithm) {
        byte[] digest = digest(input, algorithm);
        return Base64Utils.encryptByBase64(digest);
    }

    /**
     * 计算消息摘要，返回 URL 安全的 Base64 编码字符串
     * @param input 输入字节数组
     * @param algorithm 算法名称
     * @return URL 安全的 Base64 编码哈希值
     */
    private static String digestBase64Url(byte[] input, String algorithm) {
        byte[] digest = digest(input, algorithm);
        return Base64Utils.encryptByBase64(digest);
    }

    /**
     * 计算消息摘要
     * @param input 输入字节数组
     * @param algorithm 算法名称
     * @return 哈希字节数组
     */
    private static byte[] digest(byte[] input, String algorithm) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.update(input);
            return messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new CryptoException("不支持的哈希算法: " + algorithm, e);
        }
    }

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
}
