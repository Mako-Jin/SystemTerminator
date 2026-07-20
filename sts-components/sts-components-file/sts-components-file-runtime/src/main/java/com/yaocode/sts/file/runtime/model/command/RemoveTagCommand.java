package com.yaocode.sts.file.runtime.model.command;

import lombok.Builder;
import lombok.Data;

/**
 * 删除标签命令
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class RemoveTagCommand {
    /** 文件ID */
    private String fileId;

    /** 要删除的标签 */
    private String tag;

    /** 租户ID */
    private String tenantId;

    /** 用户ID */
    private String userId;

    /** 用户名 */
    private String userName;
}
