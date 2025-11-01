package com.yaocode.sts.common.web.config;

import com.yaocode.sts.common.web.mvc.SubRequestMappingHandlerMapping;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * 子路径映射自动配置
 * @author: Jin-LiangBo
 * @date: 2025年10月18日 11:46
 */
@Configuration
@ConditionalOnWebApplication
public class SubRequestMappingAutoConfiguration {

    @Bean
    @Primary
    public RequestMappingHandlerMapping subRequestMappingHandlerMapping() {
        SubRequestMappingHandlerMapping handlerMapping = new SubRequestMappingHandlerMapping();
        // 最高优先级
        handlerMapping.setOrder(0);
        return handlerMapping;
    }

}
