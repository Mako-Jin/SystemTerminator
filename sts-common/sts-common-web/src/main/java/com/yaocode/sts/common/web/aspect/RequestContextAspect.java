package com.yaocode.sts.common.web.aspect;

import com.yaocode.sts.common.tools.StringUtils;
import com.yaocode.sts.common.tools.id.IdFactory;
import com.yaocode.sts.common.tools.id.IdGeneratorType;
import com.yaocode.sts.common.web.constants.HeaderConstants;
import com.yaocode.sts.common.web.constants.HttpConstants;
import com.yaocode.sts.common.web.constants.RequestConstants;
import com.yaocode.sts.common.web.context.ClientInfoContext;
import com.yaocode.sts.common.web.context.DeviceInfoContext;
import com.yaocode.sts.common.web.context.RequestContext;
import com.yaocode.sts.common.web.context.RequestContextHolder;
import com.yaocode.sts.common.web.utils.WebHttpRequestUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.servlet.http.Cookie;

import java.time.Instant;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Aspect
public class RequestContextAspect {

    private static final Logger logger = LoggerFactory.getLogger(RequestContextAspect.class);

    @Resource
    private HttpServletRequest request;

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
        // 提取客户端信息
        ClientInfoContext clientInfo = extractClientInfo(request);

        // 提取设备信息
        DeviceInfoContext deviceInfo = extractDeviceInfo(request);

        // 提取 Remember Me Token（从参数、Cookie、Header 三个地方尝试）
//        String rememberMeToken = extractRememberMeToken(request);

        // 构建请求上下文
        return RequestContext.builder()
                // 请求基础信息
                .traceId(extractTraceId(request))
                .spanId(extractSpanId(request))
                .parentSpanId(extractParentSpanId(request))
                .requestId(extractRequestId(request))
                .requestTime(Instant.now().toEpochMilli())
                .requestUri(request.getRequestURI())
                .requestMethod(request.getMethod())
                .queryString(request.getQueryString())
                .contentType(request.getContentType())

                // 网络信息
                .ipAddress(getClientIp(request))
                .userAgent(request.getHeader(HeaderConstants.USER_AGENT))
                .headers(getHeaders(request))
                .cookies(getCookies(request))

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
    private String extractSpanId(HttpServletRequest request) {
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
     * 生成 TraceId
     * 格式：trace-{timestamp}-{uuid前8位}
     */
    private String generateTraceId() {
        return String.format("trace-%d-%s",
                System.currentTimeMillis(),
                IdFactory.generate());
    }

    /**
     * 生成 SpanId
     * 格式：span-{timestamp}-{uuid前6位}
     */
    private String generateSpanId() {
        return String.format("span-%d-%s",
                System.currentTimeMillis(),
                IdFactory.generate());
    }

    /**
     * 提取客户端信息
     */
    private ClientInfoContext extractClientInfo(HttpServletRequest request) {
        ClientInfoContext context = ClientInfoContext.createDefault();

        // 从请求参数中获取
        context.setClientId(getHeader(request, HeaderConstants.CLIENT_ID, request.getParameter(RequestConstants.CLIENT_ID)));
        context.setClientType(getHeader(request, HeaderConstants.CLIENT_TYPE, request.getParameter(RequestConstants.CLIENT_TYPE)));
        context.setClientVersion(getHeader(request, HeaderConstants.CLIENT_VERSION, request.getParameter(RequestConstants.CLIENT_VERSION)));
        context.setClientName(getHeader(request, HeaderConstants.CLIENT_NAME, request.getParameter(RequestConstants.CLIENT_NAME)));

        context.setAppId(getHeader(request, HeaderConstants.APP_ID, request.getParameter(RequestConstants.APP_ID)));
        context.setAppVersion(getHeader(request, HeaderConstants.APP_VERSION, request.getParameter(RequestConstants.APP_VERSION)));
        context.setAppPackage(getHeader(request, HeaderConstants.APP_PACKAGE, request.getParameter(RequestConstants.APP_PACKAGE)));
        context.setGrantType(getHeader(request, HeaderConstants.GRANT_TYPE, request.getParameter(RequestConstants.GRANT_TYPE)));
        context.setScope(getHeader(request, HeaderConstants.SCOPE, request.getParameter(RequestConstants.SCOPE)));
        context.setRedirectUri(getHeader(request, HeaderConstants.REDIRECT_URI, request.getParameter(RequestConstants.REDIRECT_URI)));

        return context;
    }

    /**
     * 提取设备信息
     */
    private DeviceInfoContext extractDeviceInfo(HttpServletRequest request) {
        DeviceInfoContext context = DeviceInfoContext.createDefault();

        context.setDeviceId(getHeader(request, HeaderConstants.DEVICE_ID, request.getParameter(RequestConstants.DEVICE_ID)));
        context.setDeviceType(getHeader(request, HeaderConstants.DEVICE_TYPE, request.getParameter(RequestConstants.DEVICE_TYPE)));
        context.setDeviceName(getHeader(request, HeaderConstants.DEVICE_NAME, request.getParameter(RequestConstants.DEVICE_NAME)));
        context.setDeviceModel(getHeader(request, HeaderConstants.DEVICE_MODEL, request.getParameter(RequestConstants.DEVICE_MODEL)));
        context.setOsName(getHeader(request, HeaderConstants.OS_NAME, request.getParameter(RequestConstants.OS_NAME)));
        context.setOsVersion(getHeader(request, HeaderConstants.OS_VERSION, request.getParameter(RequestConstants.OS_VERSION)));
        context.setOsBuild(getHeader(request, HeaderConstants.OS_BUILD, request.getParameter(RequestConstants.OS_BUILD)));
        context.setScreenResolution(getHeader(request, HeaderConstants.SCREEN_RESOLUTION, request.getParameter(RequestConstants.SCREEN_RESOLUTION)));
        context.setScreenSize(getHeader(request, HeaderConstants.SCREEN_SIZE, request.getParameter(RequestConstants.SCREEN_SIZE)));
        context.setDeviceFingerprint(getHeader(request, HeaderConstants.DEVICE_FINGERPRINT, request.getParameter(RequestConstants.DEVICE_FINGERPRINT)));
        context.setImei(getHeader(request, HeaderConstants.IMEI, request.getParameter(RequestConstants.IMEI)));
        context.setIdfa(getHeader(request, HeaderConstants.IDFA, request.getParameter(RequestConstants.IDFA)));
        context.setMacAddress(getHeader(request, HeaderConstants.MAC_ADDRESS, request.getParameter(RequestConstants.MAC_ADDRESS)));
        context.setCountryCode(getHeader(request, HeaderConstants.COUNTRY_CODE, request.getParameter(RequestConstants.COUNTRY_CODE)));
        context.setLanguage(getHeader(request, HeaderConstants.LANGUAGE, request.getParameter(RequestConstants.LANGUAGE)));
        context.setTimezone(getHeader(request, HeaderConstants.TIMEZONE, request.getParameter(RequestConstants.TIMEZONE)));

        String jailBrokenStr = getHeader(request, HeaderConstants.IS_JAIL_BROKEN, request.getParameter(RequestConstants.IS_JAIL_BROKEN));
        if (jailBrokenStr != null) {
            context.setIsJailbroken(Boolean.parseBoolean(jailBrokenStr));
        }

        String emulatorStr = getHeader(request, HeaderConstants.IS_EMULATOR, request.getParameter(RequestConstants.IS_EMULATOR));
        if (emulatorStr != null) {
            context.setIsEmulator(Boolean.parseBoolean(emulatorStr));
        }

        // ===== 屏幕密度（Integer） =====
        String densityStr = getHeader(request, HeaderConstants.SCREEN_DENSITY, request.getParameter(RequestConstants.SCREEN_DENSITY));
        if (densityStr != null) {
            try {
                context.setScreenDensity(Integer.parseInt(densityStr));
            } catch (NumberFormatException ignored) {}
        }

        // ===== 默认值 =====
        if (context.getDeviceType() == null) {
            context.setDeviceType(RequestConstants.DEFAULT_DEVICE_TYPE);
        }
        if (context.getLanguage() == null) {
            context.setLanguage(RequestConstants.DEFAULT_LANGUAGE);
        }
        if (context.getTimezone() == null) {
            context.setTimezone(RequestConstants.DEFAULT_TIMEZONE);
        }
        if (context.getIsJailbroken() == null) {
            context.setIsJailbroken(RequestConstants.DEFAULT_IS_JAIL_BROKEN);
        }
        if (context.getIsEmulator() == null) {
            context.setIsEmulator(RequestConstants.DEFAULT_IS_EMULATOR);
        }
        if (context.getIsTrusted() == null) {
            context.setIsTrusted(RequestConstants.DEFAULT_IS_TRUSTED);
        }

        return context;
    }

    /**
     * 提取 Remember Me Token
     * 优先级：参数 > Cookie > Header
     */
    private String extractRememberMeToken(HttpServletRequest request) {
        // 1. 从请求参数中获取
        String token = request.getParameter("rememberMeToken");
        if (token != null && !token.isEmpty()) {
            return token;
        }

        token = request.getParameter("rememberMe");
        if (token != null && !token.isEmpty()) {
            return token;
        }

        // 2. 从 Cookie 中获取
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("remember_me".equals(cookie.getName())
                        || "remember-me".equals(cookie.getName())
                        || "rememberMe".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        // 3. 从 Header 中获取
        token = request.getHeader("X-Remember-Me-Token");
        if (token != null && !token.isEmpty()) {
            return token;
        }

        token = request.getHeader("Cookie-Remember-Me");
        if (token != null && !token.isEmpty()) {
            return token;
        }

        return null;
    }

    /**
     * 获取请求参数（防止 null）
     */
    private String getParameter(HttpServletRequest request, String name) {
        String value = request.getParameter(name);
        return value != null && !value.isEmpty() ? value : null;
    }

    /**
     * 获取客户端真实IP
     */
    private String getClientIp(HttpServletRequest request) {
        return WebHttpRequestUtils.getClientIp(request);
    }

    private String getHeader(HttpServletRequest request, String name, String defaultValue) {
        String value = request.getHeader(name);
        return value != null && !value.isEmpty() ? value : defaultValue;
    }

    /**
     * 获取所有请求头
     */
    private Map<String, String> getHeaders(HttpServletRequest request) {
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
     * 获取所有Cookie
     */
    private Map<String, String> getCookies(HttpServletRequest request) {
        Map<String, String> cookies = new HashMap<>();
        Cookie[] cookieArray = request.getCookies();
        if (cookieArray != null) {
            for (Cookie cookie : cookieArray) {
                cookies.put(cookie.getName(), cookie.getValue());
            }
        }
        return cookies;
    }

    /**
     * 创建默认上下文（当无法获取请求对象时）
     */
    private RequestContext createDefaultContext() {
        return RequestContext.builder()
                .traceId(generateTraceId())
                .requestId(IdFactory.generate(IdGeneratorType.UUID))
                .requestTime(Instant.now().toEpochMilli())
                .clientInfo(ClientInfoContext.createDefault())
                .deviceInfo(DeviceInfoContext.createDefault())
                .build();
    }
}
