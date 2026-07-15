package com.yaocode.sts.components.file.runtime.model.query;

import lombok.Builder;
import lombok.Data;

/**
 * 文件对比查询
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class FileCompareQuery {
    /** 文件1ID */
    private String fileId1;

    /** 文件2ID */
    private String fileId2;

    /** 租户ID */
    private String tenantId;

    /** 用户ID */
    private String userId;
}
