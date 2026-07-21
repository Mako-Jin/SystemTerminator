package com.yaocode.sts.file.core.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 文件工具类
 */
public class FileUtils {

    private static final int BUFFER_SIZE = 8192;

    /**
     * 获取文件扩展名（不含点）
     */
    public static String getFileExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex + 1);
        }
        return "";
    }

    /**
     * 获取基础文件名（不含扩展名）
     */
    public static String getBaseFileName(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }
        int lastDotIndex = fileName.lastIndexOf(".");
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
}
