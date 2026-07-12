package com.yaocode.sts.components.file.interfaces.model.response;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 上传文件VO
 */
@Data
public class UploadFileListResponse {

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
     * 文件类型
     */
    private String fileType;
    /**
     * 文件MD5值
     */
    private String fileMd5;
    /**
     * 存储类型
     */
    private String storageType;
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
     * 下载次数
     */
    private Long downloadCount;
    /**
     * 标签（逗号分隔）
     */
    private String tags;
    /**
     * 文件描述
     */
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

}