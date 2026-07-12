package com.yaocode.sts.components.file.interfaces.model.response;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 回收站文件信息
 */
@Data
public class RecycleFileInfoResponse {

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
     * 原始路径
     */
    private String originalPath;
    /**
     * 删除用户ID
     */
    private String deletedUserId;
    /**
     * 删除用户名
     */
    private String deletedUserName;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;
    /**
     * 过期时间（自动清理）
     */
    private LocalDateTime expireTime;
    /**
     * 剩余保留天数
     */
    private Integer remainingDays;
    /**
     * 删除原因
     */
    private String deleteReason;
    /**
     * 是否可恢复
     */
    private Boolean canRestore;

}