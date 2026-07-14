package com.yaocode.sts.components.file.runtime.model.result;

import lombok.Builder;
import lombok.Data;

/**
 * 上传状态结果
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class UploadStatusResult {
    /** 文件ID */
    private String fileId;

    /** 文件名 */
    private String fileName;

    /** 文件大小（字节） */
    private Long fileSize;

    /** 文件MD5 */
    private String fileMd5;

    /** 存储类型 */
    private String storageType;

    /** 上传状态码 */
    private Integer uploadStatus;

    /** 上传状态描述 */
    private String uploadStatusDesc;

    /** 进度百分比 */
    private Integer progress;

    /** 上传ID（分片上传时） */
    private String uploadId;

    /** 总分片数 */
    private Integer totalChunks;

    /** 已上传分片数 */
    private Integer uploadedChunks;

    /** 上传开始时间戳 */
    private Long uploadStartTime;

    /** 上传结束时间戳 */
    private Long uploadEndTime;

    /** 预计剩余时间（秒） */
    private Long estimatedRemainingTime;

    /** 错误信息 */
    private String errorMessage;
}
