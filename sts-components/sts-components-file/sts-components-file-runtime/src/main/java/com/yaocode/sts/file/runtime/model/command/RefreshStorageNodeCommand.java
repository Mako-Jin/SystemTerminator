package com.yaocode.sts.file.runtime.model.command;

import lombok.Builder;
import lombok.Data;

/**
 * 刷新存储节点命令
 */
@Data
@Builder
public class RefreshStorageNodeCommand {

    /**
     * 节点ID
     */
    private Long nodeId;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 用户ID
     */
    private String userId;
}