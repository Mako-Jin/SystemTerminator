package com.yaocode.sts.file.application.model.command;

import lombok.Builder;
import lombok.Data;

/**
 * 回滚版本命令
 */
@Data
@Builder
public class RollbackVersionCommand {
    private String fileId;
    private String targetVersionId;
    private String rollbackReason;
    private String tenantId;
    private String userId;
    private String userName;
}
