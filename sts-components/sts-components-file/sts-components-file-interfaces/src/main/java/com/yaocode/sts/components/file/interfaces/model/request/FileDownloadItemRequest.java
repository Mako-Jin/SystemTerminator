package com.yaocode.sts.components.file.interfaces.model.request;

import lombok.Data;

/**
 * 文件下载项
 */
@Data
public class FileDownloadItemRequest {

    /**
     * 文件ID
     */
    private String fileId;
    /**
     * 自定义文件名
     */
    private String customFileName;
    /**
     * 相对路径（用于目录结构）
     */
    private String relativePath;
    /**
     * 是否下载历史版本
     */
    private Boolean downloadVersion;
    /**
     * 版本号
     */
    private Integer versionNumber;

}