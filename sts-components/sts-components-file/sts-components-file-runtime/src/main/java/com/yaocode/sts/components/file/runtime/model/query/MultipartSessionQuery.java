package com.yaocode.sts.components.file.runtime.model.query;

import lombok.Builder;
import lombok.Data;

/**
 * 分片会话列表查询
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class MultipartSessionQuery {
    /** 页码 */
    private Integer page;

    /** 每页数量 */
    private Integer size;

    /** 会话状态 */
    private String status;

    /** 租户ID */
    private String tenantId;
}
