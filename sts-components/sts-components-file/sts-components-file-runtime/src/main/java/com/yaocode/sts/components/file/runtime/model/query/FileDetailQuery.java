package com.yaocode.sts.components.file.runtime.model.query;

import lombok.Builder;
import lombok.Data;

/**
 * 文件详情查询
 */
@Data
@Builder
public class FileDetailQuery {

    /**
     * 文件ID
     */
    private String fileId;

    /**
     * 租户ID
     */
    private String tenantId;
}