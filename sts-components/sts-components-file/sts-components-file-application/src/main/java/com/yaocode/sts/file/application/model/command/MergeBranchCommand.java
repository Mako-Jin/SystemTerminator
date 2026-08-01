package com.yaocode.sts.file.application.model.command;

import lombok.Builder;
import lombok.Data;

/**
 * 合并分支命令
 */
@Data
@Builder
public class MergeBranchCommand {
    private String fileId;
    private String fromBranchId;
    private String toBranchId;
    private String mergeMessage;
    private Boolean autoResolve;
    private String tenantId;
    private String userId;
    private String userName;
}
