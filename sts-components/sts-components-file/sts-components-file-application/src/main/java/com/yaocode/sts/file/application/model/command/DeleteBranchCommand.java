package com.yaocode.sts.file.application.model.command;

import lombok.Builder;
import lombok.Data;

/**
 * 删除分支命令
 */
@Data
@Builder
public class DeleteBranchCommand {
    private String branchId;
    private String tenantId;
    private String userId;
}
