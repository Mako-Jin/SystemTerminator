package com.yaocode.sts.components.file.interfaces.model.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 文件版本信息
 */
@Data
@Builder
public class FileVersionInfoResponse {

    /**
     * 版本ID
     */
    private Long versionId;
    /**
     * 文件ID
     */
    private String fileId;
    /**
     * 版本号
     */
    private Integer version;
    /**
     * 版本名称
     */
    private String versionName;
    /**
     * 更新日志
     */
    private String changeLog;
    /**
     * 文件大小（字节）
     */
    private Long fileSize;
    /**
     * 文件MD5值
     */
    private String fileMd5;
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