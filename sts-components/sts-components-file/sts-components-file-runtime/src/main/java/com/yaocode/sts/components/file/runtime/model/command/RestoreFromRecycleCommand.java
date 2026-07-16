package com.yaocode.sts.components.file.runtime.model.command;

import lombok.Builder;
import lombok.Data;

/**
 * 从回收站恢复文件命令
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class RestoreFromRecycleCommand {
    /** 文件ID */
    private String fileId;

    /** 租户ID */
    private String tenantId;

    /** 用户ID */
    private String userId;

    /** 用户名 */
    private String userName;
}
