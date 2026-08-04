package com.yaocode.sts.common.infrastructure.web.adapter;

import com.yaocode.sts.common.domain.web.HttpRequestContext;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Servlet请求适配器
 * 将HttpServletRequest适配为HttpRequestContext接口
 * 支持从 Header 和 Parameter 双通道获取数据
 * 
 * @author: Jin-LiangBo
 * @date: 2026年04月14日 18:34
 */
public class ServletRequestContext implements HttpRequestContext {

    private final HttpServletRequest request;

    public ServletRequestContext(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public Optional<String> getHeader(String name) {
        String value = request.getHeader(name);
        return Optional.ofNullable(value);
    }

    @Override
    public Optional<String> getParameter(String name) {
        String value = request.getParameter(name);
        return Optional.ofNullable(value);
    }

    @Override
    public Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                headers.put(name, request.getHeader(name));
            }
        }
        return Collections.unmodifiableMap(headers);
    }

    @Override
    public Map<String, String[]> getParameters() {
        Map<String, String[]> params = request.getParameterMap();
        return params != null ? params : Collections.emptyMap();
    }
}