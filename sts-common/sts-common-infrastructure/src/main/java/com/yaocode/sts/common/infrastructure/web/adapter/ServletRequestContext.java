package com.yaocode.sts.common.infrastructure.web.adapter;

import com.yaocode.sts.common.domain.web.HttpRequestContext;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

/**
 * Servlet请求适配器
 * 将HttpServletRequest适配为HttpRequestContext接口
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
}
