package com.yaocode.sts.components.file.interfaces.model.request;

import lombok.Data;

import java.util.List;

/**
 * 批量下载请求
 */
@Data
public class BatchDownloadRequest {
    /**
     * 待下载文件列表
     */
    private List<FileDownloadItemRequest> files;
    /**
     * 压缩包文件名
     */
    private String zipFileName;
    /**
     * 是否保留目录结构
     */
    private Boolean preserveStructure = true;
    /**
     * 是否包含元数据
     */
    private Boolean includeMetadata = false;
    /**
     * 最大文件数
     */
    private Integer maxFileCount = 100;
    /**
     * 完成回调URL
     */
    private String callbackUrl;
}