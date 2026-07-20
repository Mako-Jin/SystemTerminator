package com.yaocode.sts.file.runtime.model.command;

import lombok.Builder;
import lombok.Data;

/**
 * 清理重复文件命令
 */
@Data
@Builder
public class CleanDuplicateCommand {

    /**
     * 存储类型
     */
    private String storageType;

    /**
     * 是否自动删除
     */
    private Boolean autoDelete;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 用户ID
     */
    private String userId;
}