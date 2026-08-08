package com.yaocode.sts.file.core.utils;

import com.yaocode.sts.common.crypto.algorithm.hash.SM3Algorithm;
import com.yaocode.sts.common.crypto.enums.AlgorithmTypeEnums;
import com.yaocode.sts.file.core.enums.FileErrorCodeEnums;
import com.yaocode.sts.file.core.exception.FileHashException;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * 文件 SM3 国密哈希工具类
 * <p>
 * 基于 {@code sts-common-crypto} 的 {@link SM3Algorithm} 进行二次封装，
 * 提供文件场景的 SM3 国密哈希计算能力。
 * </p>
 * <p>
 * 支持输入：File、Path、InputStream、byte[]、String
 * 对于大文件采用全量读取（SM3 基于 Bouncy Castle，不支持增量更新）。
 * </p>
 *
 * @author yaocode
 * @since 1.0.0
 * @see SM3Algorithm
 */
@Slf4j
public final class FileSM3Utils {

    private FileSM3Utils() {
    }

    // ==================== SM3 国密 ====================

    /**
     * 计算文件的 SM3 国密哈希值
     *
     * @param file 文件
     * @return SM3 十六进制哈希值
     */
    public static String calculateSm3(File file) {
        validateFile(file);
        try (FileInputStream fis = new FileInputStream(file)) {
            return calculateSm3(fis);
        } catch (IOException e) {
            log.error("计算文件SM3失败: {}", file.getPath(), e);
            throw new FileHashException(FileErrorCodeEnums.HASH_SM3_UNAVAILABLE, e);
        }
    }

    /**
     * 计算文件的 SM3 国密哈希值
     *
     * @param path 文件路径
     * @return SM3 十六进制哈希值
     */
    public static String calculateSm3(Path path) {
        validatePath(path);
        try (InputStream is = Files.newInputStream(path)) {
            return calculateSm3(is);
        } catch (IOException e) {
            log.error("计算文件SM3失败: {}", path, e);
            throw new FileHashException(FileErrorCodeEnums.HASH_SM3_UNAVAILABLE, e);
        }
    }

    /**
     * 计算输入流的 SM3 国密哈希值
     * <p>注意：SM3 基于 Bouncy Castle 实现，需读取全部数据后计算</p>
     *
     * @param inputStream 输入流
     * @return SM3 十六进制哈希值
     */
    public static String calculateSm3(InputStream inputStream) {
        validateInputStream(inputStream);
        try (inputStream) {
            byte[] data = inputStream.readAllBytes();
            return SM3Algorithm.digestHex(data);
        } catch (IOException e) {
            log.error("读取输入流计算SM3失败", e);
            throw new FileHashException(FileErrorCodeEnums.HASH_SM3_UNAVAILABLE, e);
        }
    }

    /**
     * 计算字节数组的 SM3 国密哈希值
     *
     * @param data 字节数组
     * @return SM3 十六进制哈希值
     */
    public static String calculateSm3(byte[] data) {
        validateData(data);
        return SM3Algorithm.digestHex(data);
    }

    /**
     * 计算字符串的 SM3 国密哈希值
     *
     * @param text 字符串
     * @return SM3 十六进制哈希值
     */
    public static String calculateSm3(String text) {
        validateText(text);
        return SM3Algorithm.digestHex(text);
    }

    /**
     * 计算文件的 SM3 国密哈希值（Base64 输出）
     *
     * @param file 文件
     * @return SM3 Base64 编码哈希值
     */
    public static String calculateSm3Base64(File file) {
        validateFile(file);
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] data = fis.readAllBytes();
            return SM3Algorithm.digestBase64(data);
        } catch (IOException e) {
            log.error("计算文件SM3(Base64)失败: {}", file.getPath(), e);
            throw new FileHashException(FileErrorCodeEnums.HASH_SM3_UNAVAILABLE, e);
        }
    }

    /**
     * 验证 SM3 哈希是否匹配
     *
     * @param file       文件
     * @param expectedHex 期望的 SM3 十六进制值
     * @return 是否匹配
     */
    public static boolean verifySm3(File file, String expectedHex) {
        if (expectedHex == null || expectedHex.isEmpty()) {
            return true;
        }
        String actual = calculateSm3(file);
        return actual.equalsIgnoreCase(expectedHex);
    }

    /**
     * 验证 SM3 哈希是否匹配
     *
     * @param data       字节数组
     * @param expectedHex 期望的 SM3 十六进制值
     * @return 是否匹配
     */
    public static boolean verifySm3(byte[] data, String expectedHex) {
        if (expectedHex == null || expectedHex.isEmpty()) {
            return true;
        }
        String actual = calculateSm3(data);
        return actual.equalsIgnoreCase(expectedHex);
    }

    /**
     * 获取 SM3 算法名
     */
    public static String getAlgorithmName() {
        return AlgorithmTypeEnums.SM3.getDisplayName();
    }

    /**
     * 获取 SM3 摘要长度（字节）
     */
    public static int getDigestLength() {
        return SM3Algorithm.DIGEST_LENGTH;
    }

    // ==================== 私有方法 ====================

    private static void validateFile(File file) {
        if (file == null || !file.exists() || !file.isFile()) {
            throw new FileHashException(FileErrorCodeEnums.HASH_FILE_NOT_VALID);
        }
    }

    private static void validatePath(Path path) {
        if (path == null || !Files.exists(path) || !Files.isRegularFile(path)) {
            throw new FileHashException(FileErrorCodeEnums.HASH_FILE_NOT_VALID);
        }
    }

    private static void validateInputStream(InputStream inputStream) {
        if (inputStream == null) {
            throw new FileHashException(FileErrorCodeEnums.HASH_INPUT_STREAM_NULL);
        }
    }

    private static void validateData(byte[] data) {
        if (data == null || data.length == 0) {
            throw new FileHashException(FileErrorCodeEnums.HASH_DATA_EMPTY);
        }
    }

    private static void validateText(String text) {
        if (text == null || text.isEmpty()) {
            throw new FileHashException(FileErrorCodeEnums.HASH_TEXT_EMPTY);
        }
    }
}