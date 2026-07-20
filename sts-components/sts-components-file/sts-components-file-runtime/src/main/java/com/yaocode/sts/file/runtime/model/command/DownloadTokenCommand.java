package com.yaocode.sts.file.runtime.model.command;

import lombok.Builder;
import lombok.Data;

/**
 * 下载Token命令
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class DownloadTokenCommand {
    /** 文件ID */
    private String fileId;

    /** 过期时间（秒） */
    @Builder.Default
    private Integer expireSeconds = 300;

    /** 是否限制IP */
    @Builder.Default
    private Boolean limitIp = true;

    /** 客户端IP */
    private String clientIp;

    /** 用户代理（可选） */
    private String userAgent;

    /** 租户ID */
    private String tenantId;

    /** 用户ID */
    private String userId;
}