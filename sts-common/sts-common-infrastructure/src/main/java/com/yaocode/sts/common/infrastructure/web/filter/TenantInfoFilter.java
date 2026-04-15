package com.yaocode.sts.common.infrastructure.web.filter;

import com.yaocode.sts.common.domain.context.TenantInfoContext;
import com.yaocode.sts.common.domain.context.spi.TenantInfoResolver;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import com.yaocode.sts.common.domain.web.HttpRequestContext;
import com.yaocode.sts.common.infrastructure.web.adapter.ServletRequestContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

/**
 * 租户信息封装过滤器
 * @author: Jin-LiangBo
 * @date: 2026年04月14日 18:08
 */
@Component
public class TenantInfoFilter extends OncePerRequestFilter {

    private final TenantInfoResolver tenantInfoResolver;

    public TenantInfoFilter(TenantInfoResolver tenantInfoResolver) {
        this.tenantInfoResolver = tenantInfoResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. 适配：将Servlet请求适配为领域层接口
        HttpRequestContext context = new ServletRequestContext(request);

        // 2. 调用领域层的租户解析器（依赖倒置）
        Optional<TenantId> tenantId = tenantInfoResolver.resolve(context);

        // 3. 设置租户上下文
        tenantId.ifPresent(TenantInfoContext::setTenantId);

        try {
            filterChain.doFilter(request, response);
        } finally {
            TenantInfoContext.reset();
        }
    }

    public TenantInfoResolver getTenantInfoResolver() {
        return tenantInfoResolver;
    }

}
