package com.yaocode.sts.file.application.model.command;

import lombok.Builder;
import lombok.Data;

/**
 * 删除版本标签命令
 */
@Data
@Builder
public class DeleteVersionTagCommand {
    private String tagId;
    private String tenantId;
    private String userId;
}
