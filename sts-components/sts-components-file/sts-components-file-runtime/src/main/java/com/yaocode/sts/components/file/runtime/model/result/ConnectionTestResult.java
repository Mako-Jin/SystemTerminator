package com.yaocode.sts.components.file.runtime.model.result;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 连接测试结果
 */
@Data
@Builder
public class ConnectionTestResult {

    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 延迟（毫秒）
     */
    private Long latency;

    /**
     * 结果消息
     */
    private String message;

    /**
     * 服务器版本
     */
    private String serverVersion;

    /**
     * 服务器信息
     */
    private String serverInfo;

    /**
     * 测试时间
     */
    private LocalDateTime testTime;

    /**
     * 状态码
     */
    private Integer statusCode;
}