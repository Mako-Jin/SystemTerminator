package com.yaocode.sts.components.file.runtime.model.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * 上传结果
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
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
    private Integer storageType;

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

    /** 上传时间 */
    private LocalDateTime uploadTime;

    /** 处理耗时（毫秒） */
    private Long processingTime;

    /** 消息 */
    private String message;
}
