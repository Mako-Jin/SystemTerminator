package com.yaocode.sts.components.file.interfaces.model.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 文件详细信息
 */
@Data
public class FileDetailInfoResponse {

    /**
     * 文件ID
     */
    private String fileId;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件路径
     */
    private String filePath;
    /**
     * 文件大小（字节）
     */
    private Long fileSize;
    /**
     * 文件MD5值
     */
    private String fileMd5;
    /**
     * 文件类型
     */
    private String fileType;
    /**
     * 存储类型
     */
    private String storageType;
    /**
     * 存储URL
     */
    private String storageUrl;
    /**
     * 文件状态
     */
    private Integer fileStatus;
    /**
     * 文件状态描述
     */
    private String fileStatusDesc;
    /**
     * 上传状态
     */
    private Integer uploadStatus;
    /**
     * 上传进度（0-100）
     */
    private Integer uploadProgress;
    /**
     * 下载次数
     */
    private Long downloadCount;
    /**
     * 查看次数
     */
    private Long viewCount;
    /**
     * 创建用户ID
     */
    private String createdUserId;
    /**
     * 创建用户名
     */
    private String createdUserName;
    /**
     * 创建时间
     */
    private LocalDateTime createdTime;
    /**
     * 上传时间
     */
    private LocalDateTime uploadTime;
    /**
     * 最后访问时间
     */
    private LocalDateTime lastAccessTime;
    /**
     * 标签（逗号分隔）
     */
    private String tags;
    /**
     * 文件描述
     */
    private String description;
    /**
     * 文件版本列表
     */
    private List<FileVersionInfoResponse> versions;
    /**
     * 最近操作日志
     */
    private List<FileAuditLogResponse> recentLogs;

    // 审计信息
    private String updatedUserId;
    private String updatedUserName;
    private LocalDateTime updatedTime;

    // 存储详细信息
    private String storageBucket;
    private String storageRegion;
    private Map<String, String> storageMetadata;

    // 加密信息
    private Boolean isEncrypted;
    private String encryptionAlgorithm;

    // 压缩信息
    private Boolean isCompressed;
    private String compressionAlgorithm;

    // 访问统计
    private List<AccessRecordResponse> recentAccessRecords;
    private Map<String, Long> accessByDay;

    // 版本信息
    private Integer versionCount;
    private Integer currentVersion;

    // 业务信息
    private String businessId;
    private String businessType;

}