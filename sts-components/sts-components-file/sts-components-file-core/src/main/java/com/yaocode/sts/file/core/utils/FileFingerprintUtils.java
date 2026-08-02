package com.yaocode.sts.file.core.utils;

/**
 * 文件指纹工具类
 * <p>
 * 指纹用于文件去重判定，由 MD5 + 文件大小 + 存储类型 + 租户ID 组合而成。
 * 相同指纹代表同一租户下、同一存储类型的相同文件。
 * </p>
 *
 * @author yaocode
 * @since 1.0.0
 */
public final class FileFingerprintUtils {

    private static final String DEFAULT_STORAGE_TYPE = "default";

    private FileFingerprintUtils() {
        // 工具类私有构造
    }

    /**
     * 构建文件指纹
     * <p>
     * 指纹格式：{@code MD5_Size_StorageType_TenantId}
     * </p>
     *
     * @param fileMd5     文件MD5值
     * @param fileSize    文件大小（字节）
     * @param storageType 存储类型（可为null，默认使用 "default"）
     * @param tenantId    租户ID
     * @return 指纹字符串
     */
    public static String buildFingerprint(String fileMd5, Long fileSize, Integer storageType, String tenantId) {
        String st = storageType != null ? String.valueOf(storageType) : DEFAULT_STORAGE_TYPE;
        return fileMd5 + "_" + fileSize + "_" + st + "_" + tenantId;
    }
}