package com.yaocode.sts.file.runtime.model.result;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 下载Token结果
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class DownloadTokenResult {
    /** 下载Token */
    private String token;

    /** 文件ID */
    private String fileId;

    /** 文件名 */
    private String fileName;

    /** 文件大小（字节） */
    private Long fileSize;

    /** 下载URL */
    private String downloadUrl;

    /** 过期时间（秒） */
    private Integer expireSeconds;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 过期时间 */
    private LocalDateTime expireTime;

    /** 是否限制IP */
    private Boolean limitIp;

    /** 允许的IP */
    private String allowedIp;

    /** 租户ID */
    private String tenantId;
}
