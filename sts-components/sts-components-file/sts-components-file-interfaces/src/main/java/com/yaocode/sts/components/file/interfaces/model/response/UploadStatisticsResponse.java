package com.yaocode.sts.components.file.interfaces.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 上传统计响应
 * <p>
 * 包含文件上传的统计信息，包括总量、趋势、排行等数据
 * </p>
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class UploadStatisticsResponse {
    /**
     * 总文件数
     */
    private Long totalFiles;
    /**
     * 总大小（字节）
     */
    private Long totalSize;
    /**
     * 今日上传文件数
     */
    private Long todayUploads;
    /**
     * 今日上传大小（字节）
     */
    private Long todaySize;
    /**
     * 本周上传文件数
     */
    private Long weekUploads;
    /**
     * 本周上传大小（字节）
     */
    private Long weekSize;
    /**
     * 本月上传文件数
     */
    private Long monthUploads;
    /**
     * 本月上传大小（字节）
     */
    private Long monthSize;

    /**
     * 存储分布统计
     */
    private Map<String, StorageUploadStatsResponse> storageStats;

    /**
     * 文件类型分布统计
     */
    private Map<String, TypeUploadStatsResponse> typeStats;

    /**
     * 上传趋势
     */
    private List<TrendDataResponse> uploadTrend;

    /**
     * 活跃用户数
     */
    private Long activeUsers;
    /**
     * 新用户数
     */
    private Long newUsers;
    /**
     * 上传排行榜
     */
    private List<TopUploaderResponse> topUploaders;
}