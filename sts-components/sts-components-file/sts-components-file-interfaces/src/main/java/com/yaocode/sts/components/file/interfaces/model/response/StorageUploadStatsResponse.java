package com.yaocode.sts.components.file.interfaces.model.response;

import lombok.Builder;
import lombok.Data;

/**
 * 存储上传统计
 */
@Data
@Builder
public class StorageUploadStatsResponse {

    /**
     * 存储类型
     */
    private String storageType;
    /**
     * 存储类型描述
     */
    private String storageTypeDesc;
    /**
     * 文件数量
     */
    private Long fileCount;
    /**
     * 总大小（字节）
     */
    private Long totalSize;
    /**
     * 占比
     */
    private Double percentage;

}