package com.yaocode.sts.common.web.aspect;

import com.yaocode.sts.common.domain.constants.HeaderConstants;
import com.yaocode.sts.common.domain.constants.RequestConstants;
import com.yaocode.sts.common.domain.context.ClientInfoContext;
import com.yaocode.sts.common.domain.context.DeviceInfoContext;
import com.yaocode.sts.common.domain.context.RequestContext;
import com.yaocode.sts.common.domain.context.RequestContextHolder;
import com.yaocode.sts.common.domain.context.TenantInfoContext;
import com.yaocode.sts.common.domain.context.UserInfoContext;
import com.yaocode.sts.common.domain.context.spi.ClientInfoResolver;
import com.yaocode.sts.common.domain.context.spi.DeviceInfoResolver;
import com.yaocode.sts.common.domain.context.spi.TenantInfoResolver;
import com.yaocode.sts.common.domain.context.spi.UserInfoResolver;
import com.yaocode.sts.common.domain.valueobject.IpAddress;
import com.yaocode.sts.common.domain.web.HttpRequestContext;
import com.yaocode.sts.common.infrastructure.web.adapter.ServletRequestContext;
import com.yaocode.sts.common.tools.StringUtils;
import com.yaocode.sts.common.tools.id.IdFactory;
import com.yaocode.sts.common.tools.id.IdGeneratorType;
import com.yaocode.sts.common.web.constants.HttpConstants;
import com.yaocode.sts.common.web.utils.WebHttpRequestUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Aspect
public class RequestContextAspect {

    private static final Logger logger = LoggerFactory.getLogger(RequestContextAspect.class);

    @Resource
    private HttpServletRequest request;

    @Resource
    private TenantInfoResolver tenantInfoResolver;

    @Resource
    private UserInfoResolver userInfoResolver;

    @Resource
    private ClientInfoResolver clientInfoResolver;

    @Resource
    private DeviceInfoResolver deviceInfoResolver;

    @Pointcut("execution(* com.yaocode.sts..*.controller..*(..))")
    public void controllerMethods() {}

    /**
     * 环绕通知：自动注入请求上下文
     */
    @Around("controllerMethods()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            // 1. 构建请求上下文
            RequestContext context = buildRequestContext();

            // 2. 设置到 ThreadLocal
            RequestContextHolder.setContext(context);

            org.slf4j.MDC.put(RequestConstants.TRACE_ID, context.getTraceId());
            org.slf4j.MDC.put(RequestConstants.REQUEST_ID, context.getRequestId());
            org.slf4j.MDC.put(RequestConstants.PARENT_SPAN_ID, context.getParentSpanId());
            org.slf4j.MDC.put(RequestConstants.SPAN_ID, context.getSpanId());

            // 3. 执行目标方法
            return joinPoint.proceed();

        } finally {
            // 清理 MDC
            org.slf4j.MDC.clear();
            // 4. 清理上下文（防止内存泄漏）
            RequestContextHolder.clear();
        }
    }

    /**
     * 构建请求上下文
     */
    private RequestContext buildRequestContext() {
        // 1. 适配为领域层接口
        HttpRequestContext httpContext = new ServletRequestContext(request);

        // 2. 通过 SPI 解析器构建子 Context
        TenantInfoContext tenantInfo = tenantInfoResolver.resolve(httpContext).orElse(TenantInfoContext.createDefault());
        UserInfoContext userInfo = userInfoResolver.resolve(httpContext).orElse(UserInfoContext.createDefault());
        ClientInfoContext clientInfo = clientInfoResolver.resolve(httpContext).orElse(ClientInfoContext.createDefault());
        DeviceInfoContext deviceInfo = deviceInfoResolver.resolve(httpContext).orElse(DeviceInfoContext.createDefault());

        // 构建请求上下文
        return RequestContext.builder()
                // 请求基础信息
                .traceId(extractTraceId(request))
                .spanId(extractSpanId())
                .parentSpanId(extractParentSpanId(request))
                .requestId(extractRequestId(request))
                .requestTime(Instant.now().toEpochMilli())
                .requestUri(request.getRequestURI())
                .requestMethod(request.getMethod())
                .queryString(request.getQueryString())
                .contentType(request.getContentType())

                // 网络信息
                .ipAddress(getClientIp())
                .userAgent(request.getHeader(HeaderConstants.USER_AGENT))
                .headers(getHeaders())
                .cookies(getCookies())
                .domain(request.getServerName())

                // CSRF/Session
                .csrfToken(request.getHeader(HeaderConstants.CSRF_TOKEN))
                .sessionId(request.getSession().getId())

                // 安全信息
                .referer(request.getHeader(HeaderConstants.REFERER))
                .origin(request.getHeader(HeaderConstants.ORIGIN))
                .isHttps(HttpConstants.HTTPS.equalsIgnoreCase(request.getScheme()))
                .isAjax(HttpConstants.XML_HTTP_REQUEST.equals(request.getHeader(HeaderConstants.REQUESTED_WITH)))

                // 聚合的子Context
                .clientInfo(clientInfo)
                .deviceInfo(deviceInfo)
                .tenantInfo(tenantInfo)
                .userInfo(userInfo)
                .build();
    }

    // ==========================================
    // 链路追踪ID提取方法
    // ==========================================

    /**
     * 提取 TraceId
     * 优先级：请求头 > 请求参数 > 自动生成
     */
    private String extractTraceId(HttpServletRequest request) {
        // 1. 从标准 Header 获取
        String traceId = request.getHeader(HeaderConstants.TRACE_ID);
        if (StringUtils.hasText(traceId)) {
            return traceId;
        }

        // 3. 从请求参数获取
        traceId = request.getParameter(RequestConstants.TRACE_ID);
        if (StringUtils.hasText(traceId)) {
            return traceId;
        }

        // 4. 自动生成（格式：trace-{timestamp}-{uuid简写}）
        return generateTraceId();
    }

    /**
     * 提取 SpanId
     * 如果没有传递，服务端自动生成
     */
    private String extractSpanId() {
        // 自动生成
        return generateSpanId();
    }

    /**
     * 提取 ParentSpanId
     */
    private String extractParentSpanId(HttpServletRequest request) {
        String parentSpanId = request.getHeader(HeaderConstants.SPAN_ID);
        if (StringUtils.hasText(parentSpanId)) {
            return parentSpanId;
        }

        parentSpanId = request.getParameter(RequestConstants.SPAN_ID);
        if (StringUtils.hasText(parentSpanId)) {
            return parentSpanId;
        }

        return null;
    }

    /**
     * 提取 RequestId
     * 如果前端没有传递，则使用 traceId
     */
    private String extractRequestId(HttpServletRequest request) {
        String requestId = request.getHeader(HeaderConstants.REQUEST_ID);
        if (StringUtils.hasText(requestId)) {
            return requestId;
        }

        requestId = request.getParameter(RequestConstants.REQUEST_ID);
        if (StringUtils.hasText(requestId)) {
            return requestId;
        }

        // 使用 traceId 作为 requestId
        return extractTraceId(request);
    }

    // ==========================================
    // ID生成方法
    // ==========================================

    /**
     * 生成 TraceId（格式：trace-{timestamp}-{uuid简写}）
     */
    private String generateTraceId() {
        return String.format(RequestConstants.TRACE_ID_FORMAT,
                System.currentTimeMillis(),
                IdFactory.generate(IdGeneratorType.UUID));
    }

    /**
     * 生成 SpanId（格式：span-{timestamp}-{uuid简写}）
     */
    private String generateSpanId() {
        return String.format(RequestConstants.SPAN_ID_FORMAT,
                System.currentTimeMillis(),
                IdFactory.generate(IdGeneratorType.UUID));
    }

    // ==========================================
    // HTTP 工具方法
    // ==========================================

    /**
     * 获取客户端真实 IP
     */
    private IpAddress getClientIp() {
        return IpAddress.of(WebHttpRequestUtils.getClientIp(request));
    }

    /**
     * 获取所有请求头（只读快照）
     */
    private Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                headers.put(name, request.getHeader(name));
            }
        }
        return headers;
    }

    /**
     * 获取所有 Cookie
     */
    private Map<String, String> getCookies() {
        Map<String, String> cookies = new HashMap<>();
        Cookie[] cookieArray = request.getCookies();
        if (cookieArray != null) {
            for (Cookie cookie : cookieArray) {
                cookies.put(cookie.getName(), cookie.getValue());
            }
        }
        return cookies;
    }
}