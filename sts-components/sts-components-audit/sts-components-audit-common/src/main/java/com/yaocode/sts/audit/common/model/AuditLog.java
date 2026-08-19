package com.yaocode.sts.audit.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog {

    /** 链路追踪ID（必填） */
    private String traceId;

    /** 租户ID（多租户场景） */
    private String tenantId;

    /** 用户ID（必填） */
    private String userId;

    /** 用户名（冗余字段，方便展示） */
    private String username;

    /** 操作类型 */
    private String operationType;

    /** 操作描述 */
    private String operationDesc;

    /** 请求URL */
    private String requestUrl;

    /** 请求方法（GET/POST） */
    private String requestMethod;

    /** 请求参数（敏感字段需脱敏） */
    private String requestParams;

    /** 响应结果（成功/失败） */
    private String responseResult;

    /** 业务状态码（0=成功，其他=失败） */
    private Integer resultCode;

    /** 错误信息（失败时记录） */
    private String errorMsg;

    /** 耗时（毫秒） */
    private Long costTime;

    /** 客户端IP */
    private String clientIp;

    /** 服务端IP */
    private String serverIp;

    /** 用户代理（浏览器信息） */
    private String userAgent;

    /** 详细扩展字段（JSON格式，存放业务个性化数据） */
    private String detail;

    /** 操作时间（必填） */
    @Builder.Default
    private LocalDateTime createTime = LocalDateTime.now();
}
