package com.yaocode.sts.file.runtime.model.query;

import lombok.Builder;
import lombok.Data;

/**
 * 回收站列表查询
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class RecycleBinQuery {
    /** 页码 */
    @Builder.Default
    private Integer page = 1;

    /** 每页数量 */
    @Builder.Default
    private Integer size = 20;

    /** 文件名（模糊匹配） */
    private String fileName;

    /** 开始时间 */
    private String startTime;

    /** 结束时间 */
    private String endTime;

    /** 租户ID */
    private String tenantId;

    /** 用户ID */
    private String userId;
}
