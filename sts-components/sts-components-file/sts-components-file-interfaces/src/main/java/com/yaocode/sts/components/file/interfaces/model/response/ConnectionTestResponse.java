package com.yaocode.sts.components.file.interfaces.model.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 连接测试结果
 */
@Data
@Builder
public class ConnectionTestResponse {
    /**
     * 是否成功
     */
    private Boolean success;
    /**
     * 延迟（毫秒）
     */
    private Long latency;
    /**
     * 连接测试消息
     */
    private String message;
    /**
     * 服务器版本
     */
    private String serverVersion;
    /**
     * 测试时间
     */
    private LocalDateTime testTime;

    private String serverInfo;

    private Integer statusCode;

}
