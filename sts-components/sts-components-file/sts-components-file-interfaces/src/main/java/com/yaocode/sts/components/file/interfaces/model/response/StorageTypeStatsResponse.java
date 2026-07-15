package com.yaocode.sts.components.file.interfaces.model.response;


import lombok.Builder;
import lombok.Data;

/**
 * 存储类型统计
 */
@Data
@Builder
public class StorageTypeStatsResponse {

    /**
     * 存储类型
     */
    private String storageType;
    /**
     * 存储类型描述
     */
    private String storageTypeDesc;
    /**
     * 文件数量
     */
    private Long fileCount;
    /**
     * 总大小（字节）
     */
    private Long totalSize;
    /**
     * 已用容量（字节）
     */
    private Long usedCapacity;
    /**
     * 最大容量（字节）
     */
    private Long maxCapacity;
    /**
     * 使用率
     */
    private Double usageRate;

}