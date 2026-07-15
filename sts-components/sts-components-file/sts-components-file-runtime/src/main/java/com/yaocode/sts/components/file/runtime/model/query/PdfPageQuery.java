package com.yaocode.sts.components.file.runtime.model.query;

import lombok.Builder;
import lombok.Data;

/**
 * PDF页面查询
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class PdfPageQuery {
    /** 文件ID */
    private String fileId;

    /** 页码 */
    private Integer page;

    /** 租户ID */
    private String tenantId;

    /** 用户ID */
    private String userId;
}