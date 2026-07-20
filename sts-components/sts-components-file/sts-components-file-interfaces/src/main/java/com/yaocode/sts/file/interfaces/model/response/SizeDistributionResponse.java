package com.yaocode.sts.file.interfaces.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 大小分布VO
 */
@Data
@Builder
public class SizeDistributionResponse {

    /**
     * 大小区间列表
     */
    private List<SizeRangeResponse> ranges;
    /**
     * 文件总数
     */
    private Long totalFiles;
    /**
     * 总大小（字节）
     */
    private Long totalSize;
    /**
     * 平均大小（字节）
     */
    private Long avgSize;
    /**
     * 最小大小（字节）
     */
    private Long minSize;
    /**
     * 最大大小（字节）
     */
    private Long maxSize;

}