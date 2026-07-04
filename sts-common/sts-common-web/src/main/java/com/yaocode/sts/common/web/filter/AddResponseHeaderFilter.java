package com.yaocode.sts.common.web.filter;

import com.yaocode.sts.common.web.constants.HeaderConstants;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 添加公共的安全响应头
 * @author: Jin-LiangBo
 * @date: 2025年11月02日 14:45
 */
@Component
public class AddResponseHeaderFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request, HttpServletResponse response, @Nonnull FilterChain filterChain) {
        // 防止缓存
        response.setHeader(HeaderConstants.HEADER_CACHE_CONTROL, HeaderConstants.CACHE_CONTROL_NO_CACHE);
        response.setHeader(HeaderConstants.HEADER_PRAGMA, HeaderConstants.PRAGMA_NO_CACHE);
        response.setHeader(HeaderConstants.HEADER_EXPIRES, HeaderConstants.EXPIRES_IMMEDIATE);

        // 安全相关头
        response.setHeader(HeaderConstants.HEADER_X_CONTENT_TYPE_OPTIONS, HeaderConstants.X_CONTENT_TYPE_OPTIONS_NOSNIFF);
        response.setHeader(HeaderConstants.HEADER_X_FRAME_OPTIONS, HeaderConstants.X_FRAME_OPTIONS_DENY);
        response.setHeader(HeaderConstants.HEADER_X_XSS_PROTECTION, HeaderConstants.X_XSS_PROTECTION_ENABLED);
    }
}
