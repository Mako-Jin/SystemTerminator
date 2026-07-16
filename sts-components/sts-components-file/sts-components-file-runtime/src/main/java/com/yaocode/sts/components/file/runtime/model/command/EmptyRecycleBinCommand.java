package com.yaocode.sts.components.file.runtime.model.command;

import lombok.Builder;
import lombok.Data;

/**
 * 清空回收站命令
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class EmptyRecycleBinCommand {
    /** 租户ID */
    private String tenantId;

    /** 用户ID */
    private String userId;

    /** 用户名 */
    private String userName;
}
