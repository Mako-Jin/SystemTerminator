package com.yaocode.sts.file.application.model.result;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 分片初始化结果
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class MultipartInitResult {
    /** 上传ID */
    private String uploadId;

    /** 文件ID */
    private String fileId;

    /** 文件名 */
    private String fileName;

    /** 文件大小（字节） */
    private Long fileSize;

    /** 分片大小（字节） */
    private Long chunkSize;

    /** 总分片数 */
    private Integer totalChunks;

    /** 过期时间戳 */
    private LocalDateTime expireTime;

    /** 存储类型 */
    private Integer storageType;

    /** 文件MD5 */
    private String fileMd5;
    /** 文件类型(MIME) */
    private String fileType;
    /** 文件标签 */
    private String tags;
    /** 文件描述 */
    private String description;
    /** 是否公开: 0-否 1-是 */
    private Integer isPublic;
    /** 元数据(JSON格式) */
    private String metadata;
    /** 文件SHA256值 */
    private String fileSha256;

    /**
     * 是否为重复文件（秒传场景）
     */
    private Boolean isDuplicate;

    /**
     * 重复文件的fileId（秒传场景返回已有文件ID）
     */
    private String duplicateFileId;

    /**
     * 是否为续传（已有活动会话）
     */
    private Boolean isResume;

    /**
     * 续传时已上传的分片数
     */
    private Integer uploadedChunks;
}