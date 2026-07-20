package com.yaocode.sts.file.interfaces.model.response;

import lombok.Builder;
import lombok.Data;

/**
 * 存储统计
 */
@Data
@Builder
public class StorageStatsResponse {

    /**
     * 存储类型
     */
    private String storageType;
    /**
     * 文件数量
     */
    private Long fileCount;
    /**
     * 使用空间（字节）
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
     * 健康节点数量
     */
    private Integer healthNodes;

    private String storageTypeDesc;
    private Long availableSpace;

    /** 总大小（字节） */
    private Long totalSize;

}
