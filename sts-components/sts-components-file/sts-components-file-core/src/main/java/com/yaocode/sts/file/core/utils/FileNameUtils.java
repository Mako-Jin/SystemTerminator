package com.yaocode.sts.file.core.utils;

import com.yaocode.sts.common.basic.constants.SymbolConstants;
import com.yaocode.sts.common.tools.StringUtils;
import com.yaocode.sts.file.core.constants.FileConstants;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 文件名工具类
 * <p>
 * 统一处理文件名的校验、解析、生成等逻辑
 * </p>
 *
 * @author yaocode
 * @since 1.0.0
 */
@Slf4j
public final class FileNameUtils {

    private static final Pattern INVALID_CHARS_PATTERN = Pattern.compile(FileConstants.INVALID_FILE_NAME_REGEX);
    private static final Pattern SPACE_PATTERN = Pattern.compile(FileConstants.WHITESPACE_REGEX);
    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern(FileConstants.TIMESTAMP_FORMATTER_PATTERN);

    private FileNameUtils() {
    }

    /**
     * 文件名各部分
     */
    public record FileNameParts(String baseName, String extension) {

    /**
     * 获取完整文件名（基名 + 扩展名）
     */
    public String getFullName() {
        return extension.isEmpty() ? baseName : baseName + FileConstants.EXTENSION_SEPARATOR + extension;
    }
    }

    /**
     * 解析文件名，返回基名和扩展名
     */
    public static FileNameParts parseFileName(String fileName) {
        if (!StringUtils.hasText(fileName)) {
            return new FileNameParts(SymbolConstants.EMPTY_STR, SymbolConstants.EMPTY_STR);
        }

        int lastDotIndex = fileName.lastIndexOf(FileConstants.EXTENSION_SEPARATOR);
        if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
            String baseName = fileName.substring(0, lastDotIndex);
            String extension = fileName.substring(lastDotIndex + 1);
            return new FileNameParts(baseName, extension);
        }

        return new FileNameParts(fileName, SymbolConstants.EMPTY_STR);
    }

    /**
     * 获取文件扩展名（不含点）
     */
    public static String getFileExtension(String fileName) {
        if (!StringUtils.hasText(fileName)) {
            return SymbolConstants.EMPTY_STR;
        }
        int lastDotIndex = fileName.lastIndexOf(FileConstants.EXTENSION_SEPARATOR);
        if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex + 1);
        }
        return SymbolConstants.EMPTY_STR;
    }

    /**
     * 获取文件基名（不含扩展名）
     */
    public static String getBaseFileName(String fileName) {
        if (!StringUtils.hasText(fileName)) {
            return SymbolConstants.EMPTY_STR;
        }
        int lastDotIndex = fileName.lastIndexOf(FileConstants.EXTENSION_SEPARATOR);
        if (lastDotIndex > 0) {
            return fileName.substring(0, lastDotIndex);
        }
        return fileName;
    }

    /**
     * 安全化文件名（移除非法字符）
     */
    public static String sanitizeFileName(String fileName) {
        if (!StringUtils.hasText(fileName)) {
            return fileName;
        }
        // 移除路径遍历字符和特殊字符
        String sanitized = INVALID_CHARS_PATTERN.matcher(fileName).replaceAll(FileConstants.UNIQUE_NAME_SEPARATOR);
        sanitized = SPACE_PATTERN.matcher(sanitized).replaceAll(FileConstants.UNIQUE_NAME_SEPARATOR);
        // 去除首尾空白
        sanitized = sanitized.trim();
        return sanitized;
    }

    /**
     * 生成唯一文件名（添加时间戳）
     */
    public static String generateUniqueFileName(String originalFileName) {
        if (!StringUtils.hasText(originalFileName)) {
            return FileConstants.DEFAULT_FILE_PREFIX + generateTimestamp();
        }

        FileNameParts parts = parseFileName(originalFileName);
        String timestamp = generateTimestamp();
        return parts.baseName() + FileConstants.UNIQUE_NAME_SEPARATOR + timestamp
                + (parts.extension().isEmpty() ? SymbolConstants.EMPTY_STR : FileConstants.EXTENSION_SEPARATOR + parts.extension());
    }

    /**
     * 生成唯一文件名（添加指定后缀）
     */
    public static String generateUniqueFileName(String originalFileName, String suffix) {
        if (!StringUtils.hasText(originalFileName)) {
            return FileConstants.DEFAULT_FILE_PREFIX + suffix;
        }

        FileNameParts parts = parseFileName(originalFileName);
        return parts.baseName() + FileConstants.UNIQUE_NAME_SEPARATOR + suffix
                + (parts.extension().isEmpty() ? SymbolConstants.EMPTY_STR : FileConstants.EXTENSION_SEPARATOR + parts.extension());
    }

    /**
     * 生成时间戳
     */
    public static String generateTimestamp() {
        return LocalDateTime.now().format(TIMESTAMP_FORMATTER);
    }

    /**
     * 校验文件名是否合法
     */
    public static boolean isValidFileName(String fileName) {
        if (!StringUtils.hasText(fileName)) {
            return false;
        }
        // 不允许空字符串
        if (fileName.trim().isEmpty()) {
            return false;
        }
        // 不允许以点开头或结尾
        if (fileName.startsWith(FileConstants.EXTENSION_SEPARATOR) || fileName.endsWith(FileConstants.EXTENSION_SEPARATOR)) {
            return false;
        }
        // 不允许包含非法字符
        return !INVALID_CHARS_PATTERN.matcher(fileName).find();
    }

    /**
     * 校验文件扩展名是否允许
     */
    public static boolean isExtensionAllowed(String fileName, List<String> allowedExtensions) {
        if (allowedExtensions == null || allowedExtensions.isEmpty()) {
            return true;
        }
        String extension = getFileExtension(fileName);
        if (extension.isEmpty()) {
            return false;
        }
        return allowedExtensions.stream().anyMatch(ext -> ext.equalsIgnoreCase(extension));
    }

    /**
     * 校验文件扩展名是否被禁止
     */
    public static boolean isExtensionBlocked(String fileName, List<String> blockedExtensions) {
        if (blockedExtensions == null || blockedExtensions.isEmpty()) {
            return false;
        }
        String extension = getFileExtension(fileName);
        if (extension.isEmpty()) {
            return false;
        }
        return blockedExtensions.stream().anyMatch(ext -> ext.equalsIgnoreCase(extension));
    }
}