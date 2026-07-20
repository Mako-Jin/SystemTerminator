package com.yaocode.sts.file.runtime.model.query;

import lombok.Builder;
import lombok.Data;

/**
 * 下载历史查询
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class DownloadHistoryQuery {
    /** 页码 */
    @Builder.Default
    private Integer page = 1;

    /** 每页数量 */
    @Builder.Default
    private Integer size = 20;

    /** 开始时间 */
    private String startTime;

    /** 结束时间 */
    private String endTime;

    /** 租户ID */
    private String tenantId;

    /** 用户ID */
    private String userId;
}
