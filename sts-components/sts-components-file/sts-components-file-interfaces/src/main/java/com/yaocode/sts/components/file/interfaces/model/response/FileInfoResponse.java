package com.yaocode.sts.components.file.interfaces.model.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 文件信息VO
 */
@Data
@Builder
public class FileInfoResponse {

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
    private Integer storageType;

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
     * 标签列表
     */
    private List<String> tags;

    /**
     * 文件描述
     */
    private String description;

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

}