package com.yaocode.sts.components.file.runtime.model.query;

import lombok.Builder;
import lombok.Data;

/**
 * 文件资源查询
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class FileResourceQuery {
    /** 文件ID */
    private String fileId;

    /** 是否预览模式 */
    @Builder.Default
    private Boolean preview = false;

    /** 租户ID */
    private String tenantId;

    /** 用户ID */
    private String userId;
}
