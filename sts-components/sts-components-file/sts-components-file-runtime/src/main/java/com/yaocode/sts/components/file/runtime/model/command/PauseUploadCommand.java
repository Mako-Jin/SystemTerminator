package com.yaocode.sts.components.file.runtime.model.command;

import lombok.Builder;
import lombok.Data;

/**
 * 暂停上传命令
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class PauseUploadCommand {
    /** 文件ID */
    private String fileId;

    /** 暂停原因 */
    private String reason;

    /** 租户ID */
    private String tenantId;

    /** 用户ID */
    private String userId;
}
