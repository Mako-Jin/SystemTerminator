package com.yaocode.sts.components.file.interfaces.model.response;

import lombok.Builder;
import lombok.Data;

/**
 * 类型上传统计
 */
@Data
@Builder
public class TypeUploadStatsResponse {

    /**
     * 文件类型
     */
    private String fileType;
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