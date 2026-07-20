package com.yaocode.sts.file.runtime.model.query;

import lombok.Builder;
import lombok.Data;

/**
 * 完整性检查查询
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class IntegrityCheckQuery {
    /** 文件ID */
    private String fileId;

    /** 租户ID */
    private String tenantId;

    /** 用户ID */
    private String userId;
}
