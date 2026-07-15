package com.yaocode.sts.components.file.runtime.model.query;

import lombok.Builder;
import lombok.Data;

/**
 * 文件类型统计查询
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class FileTypeStatisticsQuery {
    /** 存储类型 */
    private String storageType;

    /** 租户ID */
    private String tenantId;
}
