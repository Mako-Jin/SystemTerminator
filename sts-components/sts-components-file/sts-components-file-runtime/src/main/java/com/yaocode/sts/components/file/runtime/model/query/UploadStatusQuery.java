package com.yaocode.sts.components.file.runtime.model.query;

import lombok.Builder;
import lombok.Data;

/**
 * 上传状态查询
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class UploadStatusQuery {
    /** 文件ID */
    private String fileId;

    /** 租户ID */
    private String tenantId;
}
