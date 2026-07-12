package com.yaocode.sts.components.file.interfaces.model.response;

import lombok.Data;

import java.util.List;

/**
 * 访问信息VO
 */
@Data
public class AccessInfoResponse {

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
     * 总查看次数
     */
    private Long totalViews;
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
     * 下载趋势
     */
    private List<TrendDataResponse> downloadTrend;
    /**
     * 查看趋势
     */
    private List<TrendDataResponse> viewTrend;
    /**
     * 最近访问记录
     */
    private List<AccessRecordResponse> recentAccesses;

}