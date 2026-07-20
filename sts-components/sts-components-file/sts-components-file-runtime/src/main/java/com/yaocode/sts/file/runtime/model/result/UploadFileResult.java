package com.yaocode.sts.file.runtime.model.result;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户上传文件列表结果
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class UploadFileResult {
    /** 文件ID */
    private String fileId;

    /** 文件名 */
    private String fileName;

    /** 文件大小（字节） */
    private Long fileSize;

    /** 文件类型 */
    private String fileType;

    /** 文件MD5 */
    private String fileMd5;

    /** 存储类型 */
    private String storageType;

    /** 文件状态码 */
    private Integer fileStatus;

    /** 文件状态描述 */
    private String fileStatusDesc;

    /** 上传状态码 */
    private Integer uploadStatus;

    /** 下载次数 */
    private Long downloadCount;

    /** 标签 */
    private String tags;

    /** 描述 */
    private String description;

    /** 创建用户ID */
    private String createdUserId;

    /** 创建用户名 */
    private String createdUserName;

    /** 创建时间 */
    private LocalDateTime createdTime;

    /** 租户ID */
    private String tenantId;
}
