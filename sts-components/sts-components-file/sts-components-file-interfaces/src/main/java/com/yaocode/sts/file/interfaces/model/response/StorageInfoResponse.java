package com.yaocode.sts.file.interfaces.model.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 存储信息VO
 */
@Data
@Builder
public class StorageInfoResponse {

    /**
     * 文件ID
     */
    private String fileId;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 存储类型
     */
    private String storageType;
    /**
     * 存储类型描述
     */
    private String storageTypeDesc;
    /**
     * 存储桶名称
     */
    private String storageBucket;
    /**
     * 存储区域
     */
    private String storageRegion;
    /**
     * 存储路径
     */
    private String storagePath;
    /**
     * 存储URL
     */
    private String storageUrl;
    /**
     * 存储成本（估算）
     */
    private Double storageCost;
    /**
     * 存储类别：STANDARD, IA, GLACIER
     */
    private String storageClass;
    /**
     * 存储时间
     */
    private LocalDateTime storageTime;
    /**
     * 过期时间
     */
    private LocalDateTime expireTime;

}