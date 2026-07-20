package com.yaocode.sts.file.runtime.model.query;

import lombok.Builder;
import lombok.Data;

/**
 * 上传进度查询
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class UploadProgressQuery {
    /** 上传ID */
    private String uploadId;

    /** 租户ID */
    private String tenantId;
}
