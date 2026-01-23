package com.yaocode.sts.common.resources.properties;

import org.springdoc.core.utils.Constants;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 资源自动扫描入库相关属性配置类
 * @author: Jin-LiangBo
 * @date: 2025年11月15日 16:12
 */
@Configuration(proxyBeanMethods = false)
@ConfigurationProperties(prefix = ResourcesConfigProperties.RESOURCES_CONFIG_PREFIX)
@ConditionalOnProperty(name = "yaocode.web.resources.enabled", matchIfMissing = true)
public class ResourcesConfigProperties {

    public static final String RESOURCES_CONFIG_PREFIX = "yaocode.web.resources";

    /**
     * 是否开启资源扫描
     */
    private boolean enabled = true;

    /**
     * 扫描的基础包路径
     */
    private String[] basePackages = {"com.yaocode.sts"};

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String[] getBasePackages() {
        return basePackages;
    }

    public void setBasePackages(String[] basePackages) {
        this.basePackages = basePackages;
    }
}
