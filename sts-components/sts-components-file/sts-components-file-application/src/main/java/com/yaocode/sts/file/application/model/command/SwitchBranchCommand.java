package com.yaocode.sts.file.application.model.command;

import lombok.Builder;
import lombok.Data;

/**
 * 切换分支命令
 */
@Data
@Builder
public class SwitchBranchCommand {
    private String fileId;
    private String targetBranchId;
    private String tenantId;
    private String userId;
}
