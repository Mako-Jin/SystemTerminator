package com.yaocode.sts.components.file.runtime.model.result;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 大小分布结果
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class SizeDistributionResult {
    /** 大小区间列表 */
    private List<SizeRangeResult> ranges;

    /** 总文件数 */
    private Long totalFiles;

    /** 总大小（字节） */
    private Long totalSize;

    /** 平均大小（字节） */
    private Long avgSize;

    /** 最小大小（字节） */
    private Long minSize;

    /** 最大大小（字节） */
    private Long maxSize;
}
