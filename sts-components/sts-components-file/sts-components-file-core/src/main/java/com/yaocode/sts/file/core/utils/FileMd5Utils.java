package com.yaocode.sts.file.core.utils;

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
 * 文件MD5工具类
 *
 * @author yaocode
 * @since 1.0.0
 */
@Slf4j
public final class FileMd5Utils {

    private FileMd5Utils() {
        // 工具类私有构造
    }

    /**
     * 计算文件的MD5值
     *
     * @param file 文件
     * @return MD5字符串（32位小写）
     */
    public static String calculateMd5(File file) {
        if (file == null || !file.exists() || !file.isFile()) {
            throw new FileHashException(FileErrorCodeEnums.HASH_FILE_NOT_VALID);
        }
        try (FileInputStream fis = new FileInputStream(file)) {
            return calculateMd5(fis);
        } catch (IOException e) {
            log.error("计算文件MD5失败: {}", file.getPath(), e);
            throw new FileHashException(FileErrorCodeEnums.HASH_CALCULATE_FAILED, e);
        }
    }

    /**
     * 计算文件的MD5值
     *
     * @param path 文件路径
     * @return MD5字符串（32位小写）
     */
    public static String calculateMd5(Path path) {
        if (path == null || !Files.exists(path) || !Files.isRegularFile(path)) {
            throw new FileHashException(FileErrorCodeEnums.HASH_FILE_NOT_VALID);
        }
        try (InputStream is = Files.newInputStream(path)) {
            return calculateMd5(is);
        } catch (IOException e) {
            log.error("计算文件MD5失败: {}", path, e);
            throw new FileHashException(FileErrorCodeEnums.HASH_CALCULATE_FAILED, e);
        }
    }

    /**
     * 计算输入流的MD5值
     * <p>
     * 注意：此方法会读取流中的所有数据，调用后流将被关闭
     * </p>
     *
     * @param inputStream 输入流
     * @return MD5字符串（32位小写）
     */
    public static String calculateMd5(InputStream inputStream) {
        if (inputStream == null) {
            throw new FileHashException(FileErrorCodeEnums.HASH_INPUT_STREAM_NULL);
        }
        try (inputStream) {
            MessageDigest md = MessageDigest.getInstance(AlgorithmTypeEnums.MD5.getDisplayName());
            byte[] buffer = new byte[FileConstants.BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                md.update(buffer, 0, bytesRead);
            }
            return HexUtils.bytesToHex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new FileHashException(FileErrorCodeEnums.HASH_MD5_UNAVAILABLE, e);
        } catch (IOException e) {
            log.error("读取输入流计算MD5失败", e);
            throw new FileHashException(FileErrorCodeEnums.HASH_CALCULATE_FAILED, e);
        }

    }

    /**
     * 计算字节数组的MD5值
     *
     * @param data 字节数组
     * @return MD5字符串（32位小写）
     */
    public static String calculateMd5(byte[] data) {
        if (data == null || data.length == 0) {
            throw new FileHashException(FileErrorCodeEnums.HASH_DATA_EMPTY);
        }
        try {
            MessageDigest md = MessageDigest.getInstance(AlgorithmTypeEnums.MD5.getDisplayName());
            return HexUtils.bytesToHex(md.digest(data));
        } catch (NoSuchAlgorithmException e) {
            throw new FileHashException(FileErrorCodeEnums.HASH_MD5_UNAVAILABLE, e);
        }
    }

    /**
     * 计算字符串的MD5值
     *
     * @param text 字符串
     * @return MD5字符串（32位小写）
     */
    public static String calculateMd5(String text) {
        if (text == null || text.isEmpty()) {
            throw new FileHashException(FileErrorCodeEnums.HASH_TEXT_EMPTY);
        }
        return calculateMd5(text.getBytes());
    }

    /**
     * 验证文件的MD5值是否匹配
     *
     * @param file 文件
     * @param expectedMd5 期望的MD5值
     * @return 是否匹配
     */
    public static boolean verifyMd5(File file, String expectedMd5) {
        if (expectedMd5 == null || expectedMd5.isEmpty()) {
            return true;
        }
        String actualMd5 = calculateMd5(file);
        return actualMd5.equalsIgnoreCase(expectedMd5);
    }

    /**
     * 验证输入流的MD5值是否匹配
     *
     * @param inputStream 输入流
     * @param expectedMd5 期望的MD5值
     * @return 是否匹配
     */
    public static boolean verifyMd5(InputStream inputStream, String expectedMd5) {
        if (expectedMd5 == null || expectedMd5.isEmpty()) {
            return true;
        }
        String actualMd5 = calculateMd5(inputStream);
        return actualMd5.equalsIgnoreCase(expectedMd5);
    }

    /**
     * 获取文件的MD5值（带缓存，适用于大文件）
     *
     * @param filePath 文件路径
     * @param cacheKey 缓存Key（可选）
     * @return MD5字符串
     */
    public static String calculateMd5WithCache(String filePath, String cacheKey) {
        // 这里可以实现缓存逻辑，避免重复计算
        // 例如使用 Guava Cache 或 Caffeine
        return calculateMd5(new File(filePath));
    }
}