package com.yaocode.sts.components.file.runtime.model.command;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 批量删除命令
 */
@Data
@Builder
public class BatchDeleteCommand {

    /**
     * 文件ID列表
     */
    private List<String> fileIds;

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