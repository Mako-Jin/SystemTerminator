package com.yaocode.sts.components.file.runtime.model.result;

import lombok.Builder;
import lombok.Data;

/**
 * 存储上传统计
 */
@Data
@Builder
public class StorageUploadStatsResult {

    /** 存储类型 */
    private String storageType;

    /** 存储类型描述 */
    private String storageTypeDesc;

    /** 文件数 */
    private Long fileCount;

    /** 总大小 */
    private Long totalSize;

    /** 占比 */
    private Double percentage;

}
