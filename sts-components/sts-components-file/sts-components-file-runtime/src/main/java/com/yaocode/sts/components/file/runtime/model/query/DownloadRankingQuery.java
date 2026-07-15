package com.yaocode.sts.components.file.runtime.model.query;

import lombok.Builder;
import lombok.Data;

/**
 * 下载排行榜查询
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class DownloadRankingQuery {
    /** 统计周期（day/week/month/year） */
    @Builder.Default
    private String period = "week";

    /** 获取数量 */
    @Builder.Default
    private Integer limit = 10;

    /** 租户ID */
    private String tenantId;
}
