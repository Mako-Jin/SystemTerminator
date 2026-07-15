package com.yaocode.sts.components.file.runtime.model.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 文件下载项（Runtime层）
 */
@Data
@Builder
public class FileDownloadItemDto {

    /** 文件ID */
    private String fileId;

    /** 自定义文件名 */
    private String customFileName;

    /** 相对路径（用于目录结构） */
    private String relativePath;

    /** 是否下载版本 */
    private Boolean downloadVersion;

    /** 版本号 */
    private Integer versionNumber;

}
