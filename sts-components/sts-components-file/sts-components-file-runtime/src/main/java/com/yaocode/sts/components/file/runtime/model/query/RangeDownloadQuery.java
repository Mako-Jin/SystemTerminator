package com.yaocode.sts.components.file.runtime.model.query;

import lombok.Builder;
import lombok.Data;

/**
 * 分段下载查询
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class RangeDownloadQuery {
    /** 文件ID */
    private String fileId;

    /** Range请求头（如: bytes=0-1023） */
    private String range;

    /** 租户ID */
    private String tenantId;

    /** 用户ID */
    private String userId;
}