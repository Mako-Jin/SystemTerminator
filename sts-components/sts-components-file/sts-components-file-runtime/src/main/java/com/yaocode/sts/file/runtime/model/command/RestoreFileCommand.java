package com.yaocode.sts.file.runtime.model.command;

import lombok.Builder;
import lombok.Data;

/**
 * 恢复文件命令
 */
@Data
@Builder
public class RestoreFileCommand {

    /**
     * 文件ID
     */
    private String fileId;

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