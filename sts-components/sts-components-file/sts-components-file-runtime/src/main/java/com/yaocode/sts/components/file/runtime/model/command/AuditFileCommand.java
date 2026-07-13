package com.yaocode.sts.components.file.runtime.model.command;

import lombok.Builder;
import lombok.Data;

/**
 * 审核文件命令
 */
@Data
@Builder
public class AuditFileCommand {

    /**
     * 文件ID
     */
    private String fileId;

    /**
     * 是否通过审核
     */
    private Boolean approved;

    /**
     * 审核备注
     */
    private String comment;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户名称
     */
    private String userName;
}