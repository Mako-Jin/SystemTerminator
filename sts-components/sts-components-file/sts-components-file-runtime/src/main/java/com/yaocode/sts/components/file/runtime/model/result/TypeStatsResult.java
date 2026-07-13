package com.yaocode.sts.components.file.runtime.model.result;

import lombok.Builder;
import lombok.Data;

/**
 * 类型统计结果
 */
@Data
@Builder
public class TypeStatsResult {

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件类型描述
     */
    private String fileTypeDesc;

    /**
     * 文件数量
     */
    private Long fileCount;

    /**
     * 总大小（字节）
     */
    private Long totalSize;

    /**
     * 占比百分比
     */
    private Double percentage;
}