package com.yaocode.sts.common.web.context;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * 请求上下文 - 顶层容器
 * 聚合所有子Context，并提供统一访问入口
 */
@Data
@Builder
public class RequestContext {

    /**
     * 全链路追踪ID - 标识一次完整的请求链路
     * 由前端或网关生成，贯穿整个调用链
     * 示例：trace-20231201-abc123
     */
    private String traceId;

    /**
     * 当前调用段ID - 标识链路中的一段调用
     * 服务端接收请求时生成，用于记录当前服务段的调用
     * 示例：span-20231201-def456
     */
    private String spanId;

    /**
     * 父级调用段ID - 标识上游调用段
     * 用于构建完整的调用树
     * 示例：parent-span-20231201-xyz789
     */
    private String parentSpanId;

    /**
     * 请求ID - 单次请求的唯一标识
     * 可由前端传递，也可服务端生成
     * 通常等于 traceId 或 traceId + 序号
     */
    private String requestId;

    // ========== 请求基础信息 ==========
    private String requestId;          // 请求链路ID
    private Long requestTime;          // 请求时间戳
    private String requestUri;         // 请求URI
    private String requestMethod;      // 请求方法 GET/POST
    private String queryString;        // 查询参数
    private String contentType;        // Content-Type

    // ========== 网络信息 ==========
    private String ipAddress;
    private String ipLocation;          // IP归属地
    private String userAgent;
    private Map<String, String> headers;
    private Map<String, String> cookies;
    private Map<String, Object> attributes;   // 请求属性

    // ========== CSRF/Session ==========
    private String csrfToken;
    private String sessionId;
    private String stateToken;          // 登录状态令牌

    // ========== 安全信息 ==========
    private String referer;
    private String origin;
    private Boolean isHttps;
    private Boolean isAjax;

    // ========== 聚合的子Context ==========
    private ClientInfoContext clientInfo;
    private DeviceInfoContext deviceInfo;
    private UserInfoContext userInfo;
    private TenantInfoContext tenantInfo;

    // ========== 扩展信息 ==========
    private Map<String, Object> extras;

    // ========== 便捷方法 ==========
    public String getClientId() {
        return clientInfo != null ? clientInfo.getClientId() : null;
    }

    public String getDeviceId() {
        return deviceInfo != null ? deviceInfo.getDeviceId() : null;
    }

    public String getUserId() {
        return userInfo != null ? userInfo.getUserId() : null;
    }

    public String getTenantId() {
        return tenantInfo != null ? tenantInfo.getTenantId() : null;
    }

    public boolean isAuthenticated() {
        return userInfo != null && userInfo.isAuthenticated();
    }
}