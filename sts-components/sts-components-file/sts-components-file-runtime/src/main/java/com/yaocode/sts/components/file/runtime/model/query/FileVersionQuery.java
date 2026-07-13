package com.yaocode.sts.components.file.runtime.model.query;

import lombok.Builder;
import lombok.Data;

/**
 * 文件版本查询
 */
@Data
@Builder
public class FileVersionQuery {

    /**
     * 文件ID
     */
    private String fileId;

    /**
     * 租户ID
     */
    private String tenantId;
}