package com.yaocode.sts.file.runtime.model.result;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 管理端统计结果
 */
@Data
@Builder
public class AdminStatisticsResult {

    /**
     * 总文件数
     */
    private Long totalFiles;

    /**
     * 总文件大小（字节）
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
     * 今日上传文件大小（字节）
     */
    private Long todaySize;

    /**
     * 存储统计信息，key为存储类型
     */
    private Map<String, StorageStatsResult> storageStats;

    /**
     * 文件状态分布，key为状态值
     */
    private Map<String, Long> statusDistribution;

    /**
     * 文件类型统计，key为文件类型
     */
    private Map<String, TypeStatsResult> typeStats;

    /**
     * 上传趋势数据
     */
    private List<TrendDataResult> uploadTrend;

    /**
     * 下载趋势数据
     */
    private List<TrendDataResult> downloadTrend;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 统计时间
     */
    private LocalDateTime statisticsTime;
}