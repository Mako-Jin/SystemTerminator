package com.yaocode.sts.file.application.model.command;

import lombok.Builder;
import lombok.Data;

/**
 * 创建分支命令
 */
@Data
@Builder
public class CreateBranchCommand {
    private String fileId;
    private String branchName;
    private Integer branchType;
    private String branchDescription;
    private String sourceBranchId;
    private String tenantId;
    private String userId;
    private String userName;
}
