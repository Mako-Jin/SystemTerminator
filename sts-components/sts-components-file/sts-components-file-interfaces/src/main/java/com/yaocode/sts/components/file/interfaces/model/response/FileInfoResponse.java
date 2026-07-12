package com.yaocode.sts.components.file.interfaces.model.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 文件信息VO
 */
@Data
public class FileInfoResponse {

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
    private Integer uploadStatus;
    private Integer uploadProgress;
    private Long downloadCount;
    private Long viewCount;
    private List<String> tags;
    private String description;
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
    private LocalDateTime uploadTime;
    private LocalDateTime lastAccessTime;
    private LocalDateTime lastModifiedTime;

}