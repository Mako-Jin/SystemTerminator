package com.yaocode.sts.common.web.config;

import com.yaocode.sts.common.web.aspect.RequestContextAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContextConfiguration {

    @Bean
    public RequestContextAspect requestContextAspect() {
        return new RequestContextAspect();
    }

}
