package com.yaocode.sts.common.web.utils;

import com.yaocode.sts.common.basic.constants.SymbolConstants;
import com.yaocode.sts.common.web.constants.HeaderConstants;
import com.yaocode.sts.common.web.constants.WebConstants;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

public class WebHttpRequestUtils {

    /**
     * 获取当前请求对象
     */
    public static HttpServletRequest getCurrentRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return Objects.requireNonNull(attributes).getRequest();
    }

    /**
     * 获取客户端真实 IP
     */
    public static String getClientIp() {
        HttpServletRequest request = getCurrentRequest();
        return getClientIp(request);
    }

    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader(HeaderConstants.X_FORWARDED_FOR);
        if (ip == null || ip.isEmpty() || WebConstants.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader(HeaderConstants.PROXY_CLIENT_IP);
        }
        if (ip == null || ip.isEmpty() || WebConstants.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader(HeaderConstants.WL_PROXY_CLIENT_IP);
        }
        if (ip == null || ip.isEmpty() || WebConstants.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip != null && ip.contains(SymbolConstants.COMMA)) {
            ip = ip.split(SymbolConstants.COMMA)[0].trim();
        }
        return ip;
    }

    /**
     * 获取 User-Agent
     */
    public static String getUserAgent() {
        HttpServletRequest request = getCurrentRequest();
        return request.getHeader(HeaderConstants.USER_AGENT);
    }

    /**
     * 获取请求头
     */
    public static String getHeader(String name) {
        HttpServletRequest request = getCurrentRequest();
        return request.getHeader(name);
    }

}
