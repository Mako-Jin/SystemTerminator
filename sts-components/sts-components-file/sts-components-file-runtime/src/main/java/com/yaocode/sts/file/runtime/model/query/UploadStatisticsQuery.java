package com.yaocode.sts.file.runtime.model.query;

import lombok.Builder;
import lombok.Data;

/**
 * 上传统计查询
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class UploadStatisticsQuery {
    /** 统计周期（day/week/month/year） */
    private String period;

    /** 租户ID */
    private String tenantId;
}
