package com.yaocode.sts.components.file.interfaces.model.response;

import lombok.Data;

/**
 * 上传状态响应
 */
@Data
public class UploadStatusResponse {
    /**
     * 文件ID
     */
    private String fileId;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件大小（字节）
     */
    private Long fileSize;
    /**
     * 文件MD5值
     */
    private String fileMd5;
    /**
     * 存储类型
     */
    private String storageType;
    /**
     * 上传状态
     */
    private Integer uploadStatus;
    /**
     * 上传状态描述
     */
    private String uploadStatusDesc;
    /**
     * 进度（0-100）
     */
    private Integer progress;
    /**
     * 上传ID
     */
    private String uploadId;
    /**
     * 总分片数
     */
    private Integer totalChunks;
    /**
     * 已上传分片数
     */
    private Integer uploadedChunks;
    /**
     * 上传开始时间（毫秒级时间戳）
     */
    private Long uploadStartTime;
    /**
     * 上传结束时间（毫秒级时间戳）
     */
    private Long uploadEndTime;
    /**
     * 预计剩余时间（毫秒）
     */
    private Long estimatedRemainingTime;
    /**
     * 错误信息
     */
    private String errorMessage;
}