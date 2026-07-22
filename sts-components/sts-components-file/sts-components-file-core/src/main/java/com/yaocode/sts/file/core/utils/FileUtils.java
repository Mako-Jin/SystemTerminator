package com.yaocode.sts.file.core.utils;

import com.yaocode.sts.common.basic.constants.SymbolConstants;
import com.yaocode.sts.common.tools.StringUtils;
import com.yaocode.sts.file.core.enums.FileExtensionEnums;
import com.yaocode.sts.file.core.enums.FileTypeEnums;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 文件工具类
 */
@Slf4j
public class FileUtils {

    private static final int BUFFER_SIZE = 8192;

    /**
     * 获取文件扩展名（不含点）
     */
    public static String getFileExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return SymbolConstants.EMPTY_STR;
        }
        int lastDotIndex = fileName.lastIndexOf(SymbolConstants.DOT);
        if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex + 1);
        }
        return SymbolConstants.EMPTY_STR;
    }

    /**
     * 获取基础文件名（不含扩展名）
     */
    public static String getBaseFileName(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return SymbolConstants.EMPTY_STR;
        }
        int lastDotIndex = fileName.lastIndexOf(SymbolConstants.DOT);
        if (lastDotIndex > 0) {
            return fileName.substring(0, lastDotIndex);
        }
        return fileName;
    }

    /**
     * 保存文件并计算MD5
     */
    public static String saveAndCalculateMD5(InputStream inputStream, Path targetPath) throws IOException {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;

            // 确保父目录存在
            Files.createDirectories(targetPath.getParent());

            try (OutputStream os = Files.newOutputStream(targetPath)) {
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    os.write(buffer, 0, bytesRead);
                    md.update(buffer, 0, bytesRead);
                }
                os.flush();
            }

            // 转换为十六进制
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5算法不可用", e);
        }
    }

    /**
     * 安全的文件名（移除特殊字符）
     */
    public static String sanitizeFileName(String fileName) {
        if (fileName == null) {
            return null;
        }
        // 移除路径遍历字符和特殊字符
        return fileName.replaceAll("[\\\\/:*?\"<>|]", "_")
                .replaceAll("\\s+", "_");
    }

    /**
     * 验证文件大小
     */
    public static boolean validateFileSize(long fileSize, long maxSize) {
        return fileSize <= maxSize;
    }

    /**
     * 判断文件是否为图片
     */
    public static boolean isImage(String fileName) {
        FileExtensionEnums extEnum = FileExtensionEnums.fromExtension(getFileExtension(fileName));
        return extEnum.isImage();
    }

    /**
     * 判断文件是否为视频
     */
    public static boolean isVideo(String fileName) {
        FileExtensionEnums extEnum = FileExtensionEnums.fromExtension(getFileExtension(fileName));
        return extEnum.isVideo();
    }

    /**
     * 判断文件是否为音频
     */
    public static boolean isAudio(String fileName) {
        FileExtensionEnums extEnum = FileExtensionEnums.fromExtension(getFileExtension(fileName));
        return extEnum.isAudio();
    }

    /**
     * 判断文件是否为文档
     */
    public static boolean isDocument(String fileName) {
        FileExtensionEnums extEnum = FileExtensionEnums.fromExtension(getFileExtension(fileName));
        return extEnum.isDocument();
    }

    /**
     * 判断文件是否为媒体文件
     */
    public static boolean isMedia(String fileName) {
        FileExtensionEnums extEnum = FileExtensionEnums.fromExtension(getFileExtension(fileName));
        return extEnum.isMedia();
    }

    /**
     * 获取文件类型枚举
     */
    public static FileTypeEnums getFileTypeEnum(String fileName) {
        String extension = getFileExtension(fileName);
        if (extension.isEmpty()) {
            return FileTypeEnums.OTHER;
        }
        FileExtensionEnums extEnum = FileExtensionEnums.fromExtension(extension);
        return extEnum.getFileType();
    }

    /**
     * 获取文件类型代码（用于数据库）
     */
    public static int getFileTypeCode(String fileName) {
        return getFileTypeEnum(fileName).getCode();
    }

    /**
     * 获取文件扩展名枚举
     */
    public static FileExtensionEnums getFileExtensionEnums(String fileName) {
        String extension = getFileExtension(fileName);
        if (extension.isEmpty()) {
            return FileExtensionEnums.UNKNOWN;
        }
        return FileExtensionEnums.fromExtension(extension);
    }

    /**
     * 获取文件扩展名代码（用于数据库）
     */
    public static int getFileExtensionCode(String fileName) {
        return getFileExtensionEnums(fileName).getCode();
    }

    /**
     * 根据文件名获取MIME类型
     */
    public static String getMimeType(String fileName) {
        if (!StringUtils.hasText(fileName)) {
            return FileTypeEnums.OTHER.getMimePrefix();
        }

        // 先尝试从扩展名获取
        String extension = getFileExtension(fileName);
        if (!extension.isEmpty()) {
            String mimeType = getMimeTypeByExtension(extension);
            if (mimeType != null) {
                return mimeType;
            }
        }

        // 使用Java内置方法
        try {
            String mimeType = URLConnection.guessContentTypeFromName(fileName);
            if (mimeType != null) {
                return mimeType;
            }
        } catch (Exception e) {
            log.debug("获取MIME类型失败: {}", e.getMessage());
        }

        return FileTypeEnums.OTHER.getMimePrefix();
    }

    /**
     * 根据扩展名获取MIME类型
     */
    public static String getMimeTypeByExtension(String extension) {
        if (extension == null) {
            return FileTypeEnums.OTHER.getMimePrefix();
        }
        FileExtensionEnums extEnum = FileExtensionEnums.fromExtension(extension);
        return extEnum.getMimeType();
    }

    /**
     * 检查文件大小是否超过限制
     */
    public static boolean isFileSizeValid(long fileSize, long maxSizeMB) {
        return fileSize <= maxSizeMB * 1024 * 1024;
    }

    /**
     * 格式化文件大小
     */
    public static String formatFileSize(long size) {
        if (size < 1024) {
            return size + " B";
        } else if (size < 1024 * 1024) {
            return String.format("%.2f KB", size / 1024.0);
        } else if (size < 1024 * 1024 * 1024) {
            return String.format("%.2f MB", size / (1024.0 * 1024));
        } else {
            return String.format("%.2f GB", size / (1024.0 * 1024 * 1024));
        }
    }

    /**
     * 判断路径是否在安全目录内（防止路径遍历攻击）
     */
    public static boolean isPathSafe(String basePath, String targetPath) {
        try {
            Path base = Paths.get(basePath).toAbsolutePath().normalize();
            Path target = base.resolve(targetPath).toAbsolutePath().normalize();
            return target.startsWith(base);
        } catch (Exception e) {
            return false;
        }
    }
}
