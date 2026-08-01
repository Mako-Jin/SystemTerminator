package com.yaocode.sts.file.core.model;

import lombok.Builder;
import lombok.Data;

/**
 * 文件信息上下文
 * 用于描述一个文件的基本信息
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class FileInfoContext {

    /** 文件ID */
    private String fileId;

    /** 文件名 */
    private String fileName;

    /** 文件大小 */
    private Long fileSize;

    /** 文件MD5 */
    private String fileMd5;

    /** 文件URL */
    private String fileUrl;

    /** 存储类型 */
    private String storageType;

    /** 租户ID */
    private String tenantId;

    /** 用户ID */
    private String userId;

    /** 版本号 */
    private Integer versionNumber;

    /** 是否为当前版本 */
    private Boolean isCurrentVersion;

}
