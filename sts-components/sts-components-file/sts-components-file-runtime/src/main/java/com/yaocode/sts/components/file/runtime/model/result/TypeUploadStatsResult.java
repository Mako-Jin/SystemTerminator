package com.yaocode.sts.components.file.runtime.model.result;

import lombok.Builder;
import lombok.Data;

/**
 * 文件类型统计
 */
@Data
@Builder
public class TypeUploadStatsResult {

    /** 文件类型 */
    private String fileType;

    /** 文件数 */
    private Long fileCount;

    /** 总大小 */
    private Long totalSize;

    /** 占比 */
    private Double percentage;

}
