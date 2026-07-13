package com.yaocode.sts.components.file.interfaces.model.response;

import lombok.Data;

import java.util.List;

/**
 * 文件类型统计
 */
@Data
public class FileTypeStatisticsResponse {

    /**
     * 文件类型
     */
    private String fileType;
    /**
     * 文件类型描述
     */
    private String fileTypeDesc;
    /**
     * 文件数量
     */
    private Long fileCount;
    /**
     * 总大小（字节）
     */
    private Long totalSize;
    /**
     * 占比
     */
    private Double percentage;
    /**
     * 按存储类型统计
     */
    private List<StorageTypeStatsResponse> storageStats;

}