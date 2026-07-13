package com.yaocode.sts.components.file.runtime.model.result;

import lombok.Builder;
import lombok.Data;

/**
 * 趋势数据结果
 */
@Data
@Builder
public class TrendDataResult {

    /**
     * 日期
     */
    private String date;

    /**
     * 上传数量
     */
    private Long uploadCount;

    /**
     * 下载数量
     */
    private Long downloadCount;

    /**
     * 总大小（字节）
     */
    private Long totalSize;

    /**
     * 独立用户数
     */
    private Long uniqueUsers;

    /**
     * 平均上传大小（字节）
     */
    private Double avgUploadSize;

    /**
     * 上传大小（字节）
     */
    private Long uploadSize;

    /**
     * 下载大小（字节）
     */
    private Long downloadSize;
}