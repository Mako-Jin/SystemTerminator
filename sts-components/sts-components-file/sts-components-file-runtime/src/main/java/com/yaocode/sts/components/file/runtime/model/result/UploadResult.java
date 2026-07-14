package com.yaocode.sts.components.file.runtime.model.result;

import lombok.Builder;
import lombok.Data;

/**
 * 上传结果
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class UploadResult {
    /** 文件ID */
    private String fileId;

    /** 文件名 */
    private String fileName;

    /** 文件大小（字节） */
    private Long fileSize;

    /** 文件MD5 */
    private String fileMd5;

    /** 文件URL */
    private String fileUrl;

    /** 存储类型 */
    private String storageType;

    /** 租户ID */
    private String tenantId;

    /** 上传状态码 */
    private Integer uploadStatus;

    /** 上传状态描述 */
    private String uploadStatusDesc;

    /** 是否重复文件 */
    private Boolean isDuplicate;

    /** 重复文件ID（如果是重复文件） */
    private String duplicateFileId;

    /** 上传时间戳 */
    private Long uploadTime;

    /** 处理耗时（毫秒） */
    private Long processingTime;

    /** 消息 */
    private String message;
}
