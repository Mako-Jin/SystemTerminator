package com.yaocode.sts.file.runtime.model.command;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 批量恢复命令
 */
@Data
@Builder
public class BatchRestoreCommand {

    /**
     * 文件ID列表
     */
    private List<String> fileIds;

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