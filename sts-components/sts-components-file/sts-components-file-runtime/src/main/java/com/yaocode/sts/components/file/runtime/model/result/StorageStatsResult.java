package com.yaocode.sts.components.file.runtime.model.result;

import lombok.Builder;
import lombok.Data;

/**
 * 存储统计结果
 */
@Data
@Builder
public class StorageStatsResult {

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
     * 已使用空间（字节）
     */
    private Long usedSpace;

    /**
     * 最大容量（字节）
     */
    private Long maxCapacity;

    /**
     * 使用率
     */
    private Double usageRate;

    /**
     * 节点数量
     */
    private Integer nodeCount;

    /**
     * 健康节点数
     */
    private Integer healthNodes;

    /**
     * 可用空间（字节）
     */
    private Long availableSpace;
}