package com.yaocode.sts.file.runtime.model.result;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 上传统计结果
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class UploadStatisticsResult {
    /** 总文件数 */
    private Long totalFiles;

    /** 总大小（字节） */
    private Long totalSize;

    /** 今日上传数 */
    private Long todayUploads;

    /** 今日上传大小 */
    private Long todaySize;

    /** 本周上传数 */
    private Long weekUploads;

    /** 本周上传大小 */
    private Long weekSize;

    /** 本月上传数 */
    private Long monthUploads;

    /** 本月上传大小 */
    private Long monthSize;

    /** 存储分布统计 */
    private Map<String, StorageUploadStatsResult> storageStats;

    /** 文件类型统计 */
    private Map<String, TypeUploadStatsResult> typeStats;

    /** 上传趋势 */
    private List<TrendDataResult> uploadTrend;

    /** 活跃用户数 */
    private Long activeUsers;

    /** 新增用户数 */
    private Long newUsers;

    /** 上传排行榜 */
    private List<TopUploaderResult> topUploaders;
}

