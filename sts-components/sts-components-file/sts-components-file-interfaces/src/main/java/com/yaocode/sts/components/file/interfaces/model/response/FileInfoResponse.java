package com.yaocode.sts.components.file.interfaces.model.response;

import lombok.Data;

import java.time.LocalDateTime;

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