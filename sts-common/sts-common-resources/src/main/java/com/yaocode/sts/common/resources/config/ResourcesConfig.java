package com.yaocode.sts.common.resources.config;

import com.yaocode.sts.common.resources.ResourcesAutoInitEngine;
import com.yaocode.sts.common.resources.properties.ResourcesConfigProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 资源配置类
 * @author: Jin-LiangBo
 * @date: 2025年11月15日 20:11
 */
@ConditionalOnWebApplication
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(ResourcesConfigProperties.class)
@ConditionalOnProperty(name = "yaocode.web.resources.enabled", matchIfMissing = true)
public class ResourcesConfig {

    @Bean
    @ConditionalOnMissingBean
    public ResourcesAutoInitEngine resourcesAutoInitEngine(
            ResourcesConfigProperties resourcesConfigProperties
    ) {
        return new ResourcesAutoInitEngine(resourcesConfigProperties);
    }

}
