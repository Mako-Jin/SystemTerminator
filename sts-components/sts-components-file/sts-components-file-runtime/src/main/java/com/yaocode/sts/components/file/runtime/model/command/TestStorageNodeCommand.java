package com.yaocode.sts.components.file.runtime.model.command;

import lombok.Builder;
import lombok.Data;

/**
 * 测试存储节点命令
 */
@Data
@Builder
public class TestStorageNodeCommand {

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