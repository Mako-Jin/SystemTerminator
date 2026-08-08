package com.yaocode.sts.file.core.utils;

import com.yaocode.sts.common.crypto.algorithm.hash.DigestAlgorithm;
import com.yaocode.sts.common.crypto.enums.AlgorithmTypeEnums;
import com.yaocode.sts.common.crypto.utils.HexUtils;
import com.yaocode.sts.file.core.constants.FileConstants;
import com.yaocode.sts.file.core.enums.FileErrorCodeEnums;
import com.yaocode.sts.file.core.exception.FileHashException;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 文件哈希工具类
 * <p>
 * 基于 {@code sts-common-crypto} 的加密算法进行二次封装，
 * 提供文件场景的哈希计算能力。
 * </p>
 * <p>
 * 支持算法：SHA-256、SHA-384、SHA-512
 * 支持输入：File、Path、InputStream、byte[]、String
 * 对于大文件采用增量哈希（分块读取），避免内存溢出。
 * </p>
 *
 * @author yaocode
 * @since 1.0.0
 * @see DigestAlgorithm
 */
@Slf4j
public final class FileSHAUtils {

    private FileSHAUtils() {
    }

    // ==================== SHA-256 ====================

    /**
     * 计算文件的 SHA-256 值
     */
    public static String calculateSha256(File file) {
        validateFile(file);
        try (FileInputStream fis = new FileInputStream(file)) {
            return calculateSha256(fis);
        } catch (IOException e) {
            log.error("计算文件SHA-256失败: {}", file.getPath(), e);
            throw new FileHashException(FileErrorCodeEnums.HASH_CALCULATE_FAILED, e);
        }
    }

    /**
     * 计算文件的 SHA-256 值
     */
    public static String calculateSha256(Path path) {
        validatePath(path);
        try (InputStream is = Files.newInputStream(path)) {
            return calculateSha256(is);
        } catch (IOException e) {
            log.error("计算文件SHA-256失败: {}", path, e);
            throw new FileHashException(FileErrorCodeEnums.HASH_CALCULATE_FAILED, e);
        }
    }

    /**
     * 计算输入流的 SHA-256 值（增量读取，支持大文件）
     */
    public static String calculateSha256(InputStream inputStream) {
        validateInputStream(inputStream);
        try (inputStream) {
            return digestInputStream(inputStream, AlgorithmTypeEnums.SHA_256.getDisplayName());
        } catch (IOException e) {
            log.error("读取输入流计算SHA-256失败", e);
            throw new FileHashException(FileErrorCodeEnums.HASH_CALCULATE_FAILED, e);
        }
    }

    /**
     * 计算字节数组的 SHA-256 值
     */
    public static String calculateSha256(byte[] data) {
        validateData(data);
        return DigestAlgorithm.sha256Hex(data);
    }

    /**
     * 计算字符串的 SHA-256 值
     */
    public static String calculateSha256(String text) {
        validateText(text);
        return DigestAlgorithm.sha256Hex(text);
    }

    /**
     * 验证 SHA-256 是否匹配
     */
    public static boolean verifySha256(File file, String expectedHex) {
        if (expectedHex == null || expectedHex.isEmpty()) {
            return true;
        }
        String actual = calculateSha256(file);
        return actual.equalsIgnoreCase(expectedHex);
    }

    // ==================== SHA-384 ====================

    /**
     * 计算文件的 SHA-384 值
     */
    public static String calculateSha384(File file) {
        validateFile(file);
        try (FileInputStream fis = new FileInputStream(file)) {
            return calculateSha384(fis);
        } catch (IOException e) {
            log.error("计算文件SHA-384失败: {}", file.getPath(), e);
            throw new FileHashException(FileErrorCodeEnums.HASH_CALCULATE_FAILED, e);
        }
    }

    /**
     * 计算文件的 SHA-384 值
     */
    public static String calculateSha384(Path path) {
        validatePath(path);
        try (InputStream is = Files.newInputStream(path)) {
            return calculateSha384(is);
        } catch (IOException e) {
            log.error("计算文件SHA-384失败: {}", path, e);
            throw new FileHashException(FileErrorCodeEnums.HASH_CALCULATE_FAILED, e);
        }
    }

    /**
     * 计算输入流的 SHA-384 值（增量读取，支持大文件）
     */
    public static String calculateSha384(InputStream inputStream) {
        validateInputStream(inputStream);
        try (inputStream) {
            return digestInputStream(inputStream, AlgorithmTypeEnums.SHA_384.getDisplayName());
        } catch (IOException e) {
            log.error("读取输入流计算SHA-384失败", e);
            throw new FileHashException(FileErrorCodeEnums.HASH_CALCULATE_FAILED, e);
        }
    }

    /**
     * 计算字节数组的 SHA-384 值
     */
    public static String calculateSha384(byte[] data) {
        validateData(data);
        return DigestAlgorithm.sha384Hex(data);
    }

    /**
     * 计算字符串的 SHA-384 值
     */
    public static String calculateSha384(String text) {
        validateText(text);
        return DigestAlgorithm.sha384Hex(text);
    }

    /**
     * 验证 SHA-384 是否匹配
     */
    public static boolean verifySha384(File file, String expectedHex) {
        if (expectedHex == null || expectedHex.isEmpty()) {
            return true;
        }
        String actual = calculateSha384(file);
        return actual.equalsIgnoreCase(expectedHex);
    }

    // ==================== SHA-512 ====================

    /**
     * 计算文件的 SHA-512 值
     */
    public static String calculateSha512(File file) {
        validateFile(file);
        try (FileInputStream fis = new FileInputStream(file)) {
            return calculateSha512(fis);
        } catch (IOException e) {
            log.error("计算文件SHA-512失败: {}", file.getPath(), e);
            throw new FileHashException(FileErrorCodeEnums.HASH_CALCULATE_FAILED, e);
        }
    }

    /**
     * 计算文件的 SHA-512 值
     */
    public static String calculateSha512(Path path) {
        validatePath(path);
        try (InputStream is = Files.newInputStream(path)) {
            return calculateSha512(is);
        } catch (IOException e) {
            log.error("计算文件SHA-512失败: {}", path, e);
            throw new FileHashException(FileErrorCodeEnums.HASH_CALCULATE_FAILED, e);
        }
    }

    /**
     * 计算输入流的 SHA-512 值（增量读取，支持大文件）
     */
    public static String calculateSha512(InputStream inputStream) {
        validateInputStream(inputStream);
        try (inputStream) {
            return digestInputStream(inputStream, AlgorithmTypeEnums.SHA_512.getDisplayName());
        } catch (IOException e) {
            log.error("读取输入流计算SHA-512失败", e);
            throw new FileHashException(FileErrorCodeEnums.HASH_CALCULATE_FAILED, e);
        }
    }

    /**
     * 计算字节数组的 SHA-512 值
     */
    public static String calculateSha512(byte[] data) {
        validateData(data);
        return DigestAlgorithm.sha512Hex(data);
    }

    /**
     * 计算字符串的 SHA-512 值
     */
    public static String calculateSha512(String text) {
        validateText(text);
        return DigestAlgorithm.sha512Hex(text);
    }

    /**
     * 验证 SHA-512 是否匹配
     */
    public static boolean verifySha512(File file, String expectedHex) {
        if (expectedHex == null || expectedHex.isEmpty()) {
            return true;
        }
        String actual = calculateSha512(file);
        return actual.equalsIgnoreCase(expectedHex);
    }

    // ==================== 获取算法名 ====================

    /**
     * 获取 SHA-256 算法名
     */
    public static String getSha256AlgorithmName() {
        return AlgorithmTypeEnums.SHA_256.getDisplayName();
    }

    /**
     * 获取 SHA-384 算法名
     */
    public static String getSha384AlgorithmName() {
        return AlgorithmTypeEnums.SHA_384.getDisplayName();
    }

    /**
     * 获取 SHA-512 算法名
     */
    public static String getSha512AlgorithmName() {
        return AlgorithmTypeEnums.SHA_512.getDisplayName();
    }

    // ==================== 私有方法 ====================

    /**
     * 增量读取输入流并计算哈希（SHA 系列通用）
     * <p>采用分块读取，避免大文件导致内存溢出</p>
     */
    private static String digestInputStream(InputStream inputStream, String algorithm) throws IOException {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] buffer = new byte[FileConstants.BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                md.update(buffer, 0, bytesRead);
            }
            return HexUtils.bytesToHex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new FileHashException(FileErrorCodeEnums.HASH_SHA_UNAVAILABLE, e);
        }
    }

    // ==================== 校验方法 ====================

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