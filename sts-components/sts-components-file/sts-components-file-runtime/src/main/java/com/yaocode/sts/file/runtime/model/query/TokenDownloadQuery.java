package com.yaocode.sts.file.runtime.model.query;

import lombok.Builder;
import lombok.Data;

/**
 * Token下载查询
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class TokenDownloadQuery {
    /** 下载Token */
    private String token;

    /** 租户ID */
    private String tenantId;

    /** 用户ID */
    private String userId;
}
