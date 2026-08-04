package com.yaocode.sts.common.web.config;

import com.yaocode.sts.common.web.aspect.RequestContextAspect;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnWebApplication
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
public class ContextConfiguration {

    @Bean
    public RequestContextAspect requestContextAspect() {
        return new RequestContextAspect();
    }

}
