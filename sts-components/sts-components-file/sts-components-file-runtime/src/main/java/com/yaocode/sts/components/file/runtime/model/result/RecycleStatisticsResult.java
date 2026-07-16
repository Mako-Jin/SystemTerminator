package com.yaocode.sts.components.file.runtime.model.result;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 回收站统计结果
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class RecycleStatisticsResult {
    /** 回收站文件总数 */
    private Long totalFiles;

    /** 回收站占用空间（字节） */
    private Long totalSize;

    /** 今日删除数 */
    private Long todayDeleted;

    /** 今日恢复数 */
    private Long todayRestored;

    /** 即将过期文件数（7天内） */
    private Long expireSoon;

    /** 按存储类型统计 */
    private Map<String, Long> storageTypeStats;

    /** 删除趋势 */
    private List<TrendDataResult> deleteTrend;
}
