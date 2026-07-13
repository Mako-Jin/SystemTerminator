package com.yaocode.sts.components.file.runtime.model.command;

import lombok.Builder;
import lombok.Data;

/**
 * 清理过期文件命令
 */
@Data
@Builder
public class CleanExpiredCommand {

    /**
     * 过期天数
     */
    private Integer days;

    /**
     * 是否模拟运行
     */
    private Boolean dryRun;

    /**
     * 存储类型
     */
    private String storageType;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 用户ID
     */
    private String userId;
}