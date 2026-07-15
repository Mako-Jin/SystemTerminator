package com.yaocode.sts.components.file.runtime.model.result;

import lombok.Builder;
import lombok.Data;

/**
 * 大小区间
 */
@Data
@Builder
public class SizeRangeResult {

    /** 区间名称（如：0-1KB） */
    private String range;

    /** 最小大小（字节） */
    private Long minSize;

    /** 最大大小（字节） */
    private Long maxSize;

    /** 文件数量 */
    private Long fileCount;

    /** 总大小（字节） */
    private Long totalSize;

    /** 占比 */
    private Double percentage;

}
