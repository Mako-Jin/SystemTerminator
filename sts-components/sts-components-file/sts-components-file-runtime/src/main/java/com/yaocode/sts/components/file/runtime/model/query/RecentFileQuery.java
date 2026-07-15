package com.yaocode.sts.components.file.runtime.model.query;

import lombok.Builder;
import lombok.Data;

/**
 * 最近文件查询
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class RecentFileQuery {
    /** 数量限制 */
    @Builder.Default
    private Integer limit = 10;

    /** 租户ID */
    private String tenantId;

    /** 用户ID */
    private String userId;
}
