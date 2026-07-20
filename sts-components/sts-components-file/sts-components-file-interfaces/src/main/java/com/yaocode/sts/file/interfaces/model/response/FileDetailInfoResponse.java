package com.yaocode.sts.file.interfaces.model.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 文件详细信息
 */
@Data
@Builder
public class FileDetailInfoResponse {

    /**
     * 文件ID
     */
    private String fileId;

    /**
     * 文件名称
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
     * 文件SHA256值
     */
    private String fileSha256;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件扩展名
     */
    private String fileExtension;

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
     * 上传状态描述
     */
    private String uploadStatusDesc;

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
     * 创建用户名称
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
     * 最后修改时间
     */
    private LocalDateTime lastModifiedTime;

    /**
     * 过期时间
     */
    private LocalDateTime expireTime;

    /**
     * 标签（逗号分隔）
     */
    private List<String> tags;

    /**
     * 文件描述
     */
    private String description;

    /**
     * 是否公开
     */
    private Boolean isPublic;

    /**
     * 更新用户ID
     */
    private String updatedUserId;

    /**
     * 更新用户名称
     */
    private String updatedUserName;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;

    /**
     * 存储桶名称
     */
    private String storageBucket;

    /**
     * 存储区域
     */
    private String storageRegion;

    /**
     * 存储元数据
     */
    private Map<String, String> storageMetadata;

    /**
     * 是否加密
     */
    private Boolean isEncrypted;

    /**
     * 加密算法
     */
    private String encryptionAlgorithm;

    /**
     * 是否压缩
     */
    private Boolean isCompressed;

    /**
     * 压缩算法
     */
    private String compressionAlgorithm;

    /**
     * 业务ID
     */
    private String businessId;

    /**
     * 业务类型
     */
    private String businessType;

    /**
     * 版本数量
     */
    private Integer versionCount;

    /**
     * 当前版本号
     */
    private Integer currentVersion;

    /**
     * 文件版本列表
     */
    private List<FileVersionInfoResponse> versions;

    /**
     * 最近操作日志
     */
    private List<FileAuditLogResponse> recentLogs;

    /**
     * 最近访问记录
     */
    private List<AccessRecordResponse> recentAccessRecords;

    /**
     * 按日期统计的访问次数
     */
    private Map<String, Long> accessByDay;

}