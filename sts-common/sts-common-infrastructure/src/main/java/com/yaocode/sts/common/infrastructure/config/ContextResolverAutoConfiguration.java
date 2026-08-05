package com.yaocode.sts.common.infrastructure.config;

import com.yaocode.sts.common.domain.context.spi.ClientInfoResolver;
import com.yaocode.sts.common.domain.context.spi.DeviceInfoResolver;
import com.yaocode.sts.common.domain.context.spi.TenantInfoResolver;
import com.yaocode.sts.common.domain.context.spi.UserInfoResolver;
import com.yaocode.sts.common.infrastructure.web.resolver.ClientInfoResolverImpl;
import com.yaocode.sts.common.infrastructure.web.resolver.DeviceInfoResolverImpl;
import com.yaocode.sts.common.infrastructure.web.resolver.TenantInfoResolverImpl;
import com.yaocode.sts.common.infrastructure.web.resolver.UserInfoResolverImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Resolver 自动配置类
 * <p>
 * 扫描 com.yaocode.sts.common.infrastructure.web.resolver 包，
 * 将所有 @Component 标注的 Resolver 实现类注册为 Spring Bean。
 * 仅在 Web 应用环境下生效。
 *
 * @author: Jin-LiangBo
 */
@Configuration
public class ContextResolverAutoConfiguration {

    @Bean
    public ClientInfoResolver clientInfoResolver() {
        return new ClientInfoResolverImpl();
    }

    @Bean
    public DeviceInfoResolver deviceInfoResolver() {
        return new DeviceInfoResolverImpl();
    }

    @Bean
    public TenantInfoResolver tenantInfoResolver() {
        return new TenantInfoResolverImpl();
    }

    @Bean
    public UserInfoResolver userInfoResolver() {
        return new UserInfoResolverImpl();
    }

}
