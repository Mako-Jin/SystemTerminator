package com.yaocode.sts.file.runtime.model.command;

import lombok.Builder;
import lombok.Data;

/**
 * 更新文件名命令
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class UpdateFileNameCommand {
    /** 文件ID */
    private String fileId;

    /** 新文件名 */
    private String newFileName;

    /** 修改原因 */
    private String reason;

    /** 租户ID */
    private String tenantId;

    /** 用户ID */
    private String userId;

    /** 用户名 */
    private String userName;
}
