package com.yaocode.sts.components.file.runtime.model.result;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 访问信息结果
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class AccessInfoResult {
    /** 文件ID */
    private String fileId;

    /** 文件名 */
    private String fileName;

    /** 总下载次数 */
    private Long totalDownloads;

    /** 总查看次数 */
    private Long totalViews;

    /** 独立用户数 */
    private Long uniqueUsers;

    /** 今日下载 */
    private Long todayDownloads;

    /** 本周下载 */
    private Long weekDownloads;

    /** 本月下载 */
    private Long monthDownloads;

    /** 平均下载速度 */
    private Double avgDownloadSpeed;

    /** 下载趋势 */
    private List<TrendDataResult> downloadTrend;

    /** 查看趋势 */
    private List<TrendDataResult> viewTrend;

    /** 最近访问记录 */
    private List<AccessRecordResult> recentAccesses;
}
