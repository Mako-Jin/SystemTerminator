package com.yaocode.sts.file.runtime.model.command;

import lombok.Builder;
import lombok.Data;

/**
 * 取消分片上传命令
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class CancelMultipartCommand {
    /** 上传ID */
    private String uploadId;

    /** 文件ID */
    private String fileId;

    /** 取消原因 */
    private String reason;

    /** 租户ID */
    private String tenantId;

    /** 用户ID */
    private String userId;
}
