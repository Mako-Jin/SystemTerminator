package com.yaocode.sts.components.file.runtime.model.command;


import lombok.Builder;
import lombok.Data;

/**
 * 迁移文件命令
 */
@Data
@Builder
public class MigrateFileCommand {

    /**
     * 文件ID
     */
    private String fileId;

    /**
     * 目标存储类型
     */
    private Integer targetStorageType;

    /**
     * 是否异步执行
     */
    private Boolean async;

    /**
     * 是否保留源文件
     */
    private Boolean keepSource;

    /**
     * 超时时间（秒）
     */
    private Integer timeout;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户名称
     */
    private String userName;
}