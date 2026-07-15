package com.yaocode.sts.components.file.interfaces.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 文件下载统计
 */
@Data
@Builder
public class FileDownloadStatisticsResponse {

    /**
     * 文件ID
     */
    private String fileId;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 总下载次数
     */
    private Long totalDownloads;
    /**
     * 独立用户数
     */
    private Long uniqueUsers;
    /**
     * 今日下载次数
     */
    private Long todayDownloads;
    /**
     * 本周下载次数
     */
    private Long weekDownloads;
    /**
     * 本月下载次数
     */
    private Long monthDownloads;
    /**
     * 总下载流量（字节）
     */
    private Long totalDownloadSize;
    /**
     * 平均下载速度（字节/秒）
     */
    private Double avgDownloadSpeed;
    /**
     * 下载趋势
     */
    private List<TrendDataResponse> downloadTrend;
    /**
     * 按类型统计
     */
    private Map<String, Long> downloadByType;
    /**
     * 按地域统计
     */
    private Map<String, Long> downloadByRegion;

}