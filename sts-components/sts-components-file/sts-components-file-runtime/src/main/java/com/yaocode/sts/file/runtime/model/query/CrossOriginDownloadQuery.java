package com.yaocode.sts.file.runtime.model.query;

import lombok.Builder;
import lombok.Data;

/**
 * 跨域下载查询
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class CrossOriginDownloadQuery {
    /** 文件ID */
    private String fileId;

    /** 过期时间（秒） */
    @Builder.Default
    private Integer expiry = 3600;

    /** 租户ID */
    private String tenantId;

    /** 用户ID */
    private String userId;
}
