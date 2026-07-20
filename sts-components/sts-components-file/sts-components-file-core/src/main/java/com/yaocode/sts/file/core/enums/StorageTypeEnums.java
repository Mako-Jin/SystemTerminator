package com.yaocode.sts.file.core.enums;

import lombok.Getter;

@Getter
public enum StorageTypeEnums {

    LOCAL(1, "local", "本地存储"),
    MINIO(2, "minio", "MinIO对象存储"),
    OSS(3, "oss", "阿里云OSS"),
    RUSTFS(4, "rustfs", "RustFS存储"),
    S3(5, "s3", "AWS S3"),
    AZURE(6, "azure", "Azure Blob"),
    GCS(7, "gcs", "Google Cloud Storage");

    private final Integer code;
    private final String type;
    private final String desc;

    StorageTypeEnums(Integer code, String type, String desc) {
        this.code = code;
        this.type = type;
        this.desc = desc;
    }

    public static StorageTypeEnums fromCode(Integer code) {
        for (StorageTypeEnums e : values()) {
            if (e.code.equals(code)) return e;
        }
        return null;
    }

    /**
     * 根据type字符串获取枚举
     */
    public static StorageTypeEnums fromType(String type) {
        if (type == null || type.isEmpty()) {
            return null;
        }
        for (StorageTypeEnums e : values()) {
            if (e.type.equalsIgnoreCase(type)) {
                return e;
            }
        }
        return null;
    }
}
