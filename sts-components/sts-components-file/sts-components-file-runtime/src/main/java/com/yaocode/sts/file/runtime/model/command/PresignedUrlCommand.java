package com.yaocode.sts.file.runtime.model.command;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * 预签名URL命令
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class PresignedUrlCommand {
    /** 文件ID */
    private String fileId;

    /** 过期时间（秒） */
    @Builder.Default
    private Integer expiry = 3600;

    /** HTTP方法（GET/PUT） */
    @Builder.Default
    private String method = "GET";

    /** 自定义响应头 */
    private Map<String, String> responseHeaders;

    /** 租户ID */
    private String tenantId;

    /** 用户ID */
    private String userId;
}
