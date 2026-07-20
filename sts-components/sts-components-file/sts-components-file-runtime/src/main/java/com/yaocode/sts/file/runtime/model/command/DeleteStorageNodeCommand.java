package com.yaocode.sts.file.runtime.model.command;

import lombok.Builder;
import lombok.Data;

/**
 * 删除存储节点命令
 */
@Data
@Builder
public class DeleteStorageNodeCommand {

    /**
     * 节点ID
     */
    private Long nodeId;

    /**
     * 是否强制删除（即使节点上有文件）
     */
    private Boolean force;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 用户ID
     */
    private String userId;
}