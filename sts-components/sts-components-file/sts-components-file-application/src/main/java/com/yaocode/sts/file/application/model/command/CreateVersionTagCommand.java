package com.yaocode.sts.file.application.model.command;

import lombok.Builder;
import lombok.Data;

/**
 * 创建版本标签命令
 */
@Data
@Builder
public class CreateVersionTagCommand {
    private String fileId;
    private String versionId;
    private String tagName;
    private Integer tagType;
    private String tagDescription;
    private String tenantId;
    private String userId;
    private String userName;
}
