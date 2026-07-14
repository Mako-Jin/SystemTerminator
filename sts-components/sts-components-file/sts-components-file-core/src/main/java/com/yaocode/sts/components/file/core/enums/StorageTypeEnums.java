package com.yaocode.sts.components.file.core.enums;

import lombok.Getter;

@Getter
public enum StorageTypeEnums {

    LOCAL("local", "本地存储"),
    MINIO("minio", "MinIO对象存储"),
    OSS("oss", "阿里云OSS"),
    RUSTFS("rustfs", "RustFS存储"),
    S3("s3", "AWS S3"),
    AZURE("azure", "Azure Blob"),
    GCS("gcs", "Google Cloud Storage");

    private final String code;
    private final String desc;

    StorageTypeEnums(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
