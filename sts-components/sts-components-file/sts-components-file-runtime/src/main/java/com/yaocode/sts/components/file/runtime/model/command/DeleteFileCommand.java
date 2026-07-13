package com.yaocode.sts.components.file.runtime.model.command;

import lombok.Builder;
import lombok.Data;

/**
 * 删除文件命令
 */
@Data
@Builder
public class DeleteFileCommand {

    /**
     * 文件ID
     */
    private String fileId;

    /**
     * 删除原因
     */
    private String reason;

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