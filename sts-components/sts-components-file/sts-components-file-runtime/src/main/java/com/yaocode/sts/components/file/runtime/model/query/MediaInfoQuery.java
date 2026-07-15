package com.yaocode.sts.components.file.runtime.model.query;

import lombok.Builder;
import lombok.Data;

/**
 * 媒体信息查询
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class MediaInfoQuery {
    /** 文件ID */
    private String fileId;

    /** 租户ID */
    private String tenantId;

    /** 用户ID */
    private String userId;
}