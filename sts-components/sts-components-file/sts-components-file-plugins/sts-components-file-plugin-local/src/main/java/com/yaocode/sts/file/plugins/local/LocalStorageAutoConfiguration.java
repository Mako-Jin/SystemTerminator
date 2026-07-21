package com.yaocode.sts.file.plugins.local;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 本地存储自动配置类
 * 当配置 spring.storage.type=local 时激活
 */
@Configuration
@EnableConfigurationProperties(LocalStorageProperties.class)
@ConditionalOnProperty(
        prefix = "yaocode.storage.local",
        name = "enabled",
        havingValue = "true",
        matchIfMissing = true
)
public class LocalStorageAutoConfiguration {

    private final LocalStorageProperties properties;

    public LocalStorageAutoConfiguration(LocalStorageProperties properties) {
        this.properties = properties;
    }

    /**
     * 配置本地存储插件
     */
    @Bean
    @Primary
    @ConditionalOnMissingBean(LocalStoragePlugin.class)
    public LocalStoragePlugin localStoragePlugin() {
        return new LocalStoragePlugin(properties);
    }
}
