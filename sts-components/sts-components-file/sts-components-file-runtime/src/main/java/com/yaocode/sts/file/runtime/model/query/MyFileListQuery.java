package com.yaocode.sts.file.runtime.model.query;

import lombok.Builder;
import lombok.Data;

/**
 * 我的文件列表查询
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class MyFileListQuery {
    /** 文件状态 */
    private Integer status;

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
