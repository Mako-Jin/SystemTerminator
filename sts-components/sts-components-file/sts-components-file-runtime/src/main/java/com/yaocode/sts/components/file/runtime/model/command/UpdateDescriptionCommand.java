package com.yaocode.sts.components.file.runtime.model.command;

import lombok.Builder;
import lombok.Data;

/**
 * 更新描述命令
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class UpdateDescriptionCommand {
    /** 文件ID */
    private String fileId;

    /** 文件描述 */
    private String description;

    /** 租户ID */
    private String tenantId;

    /** 用户ID */
    private String userId;

    /** 用户名 */
    private String userName;
}
