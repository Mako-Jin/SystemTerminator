package com.yaocode.sts.components.file.runtime.model.query;

import lombok.Builder;
import lombok.Data;

/**
 * 文件版本资源查询
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class FileVersionResourceQuery {
    /** 文件ID */
    private String fileId;

    /** 版本号 */
    private Integer version;

    /** 租户ID */
    private String tenantId;

    /** 用户ID */
    private String userId;
}