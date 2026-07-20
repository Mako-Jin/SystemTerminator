package com.yaocode.sts.file.runtime.model.command;

import lombok.Builder;
import lombok.Data;

/**
 * 设置文件公开命令
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class SetFilePublicCommand {
    /** 文件ID */
    private String fileId;

    /** 是否公开 */
    private Boolean publicAccess;

    /** 租户ID */
    private String tenantId;

    /** 用户ID */
    private String userId;

    /** 用户名 */
    private String userName;
}
