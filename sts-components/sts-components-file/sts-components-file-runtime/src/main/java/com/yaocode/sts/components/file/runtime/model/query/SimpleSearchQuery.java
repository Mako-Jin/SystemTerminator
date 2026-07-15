package com.yaocode.sts.components.file.runtime.model.query;

import lombok.Builder;
import lombok.Data;

/**
 * 简单搜索查询
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class SimpleSearchQuery {
    /** 关键词 */
    private String keyword;

    /** 页码 */
    @Builder.Default
    private Integer page = 1;

    /** 每页数量 */
    @Builder.Default
    private Integer size = 20;

    /** 租户ID */
    private String tenantId;

    /** 用户ID */
    private String userId;
}
