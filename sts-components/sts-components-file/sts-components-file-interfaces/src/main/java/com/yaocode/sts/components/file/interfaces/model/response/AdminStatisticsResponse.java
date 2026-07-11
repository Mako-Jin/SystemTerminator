package com.yaocode.sts.components.file.interfaces.model.response;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 管理员统计信息
 */
@Data
public class AdminStatisticsResponse {

    /**
     * 文件统计
     */
    private Long totalFiles;
    /**
     * 总文件大小
     */
    private Long totalSize;
    /**
     * 总用户数
     */
    private Long totalUsers;
    /**
     * 今日上传文件数
     */
    private Long todayUploads;
    /**
     * 今日下载文件数
     */
    private Long todayDownloads;

    /**
     * 存储统计
     */
    private Map<String, StorageStatsResponse> storageStats;

    /**
     * 上传趋势数据
     */
    private List<TrendDataResponse> uploadTrend;
    /**
     * 下载趋势数据
     */
    private List<TrendDataResponse> downloadTrend;

}
