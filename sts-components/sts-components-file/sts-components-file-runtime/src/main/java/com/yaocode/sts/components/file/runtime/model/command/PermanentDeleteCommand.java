package com.yaocode.sts.components.file.runtime.model.command;

import lombok.Builder;
import lombok.Data;

/**
 * 永久删除命令
 */
@Data
@Builder
public class PermanentDeleteCommand {
    private String fileId;
    private String reason;
    private String tenantId;
    private String userId;
    private String userName;
}
