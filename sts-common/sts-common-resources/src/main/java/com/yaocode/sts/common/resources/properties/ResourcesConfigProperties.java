package com.yaocode.sts.common.resources.properties;

import org.springdoc.core.configuration.SpringDocConfiguration;
import org.springdoc.core.utils.Constants;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 资源自动扫描入库相关属性配置类
 * @author: Jin-LiangBo
 * @date: 2025年11月15日 16:12
 */
@Configuration(proxyBeanMethods = false)
@ConfigurationProperties(prefix = Constants.SPRINGDOC_PREFIX)
@ConditionalOnProperty(name = "yaocode.web.resources.enabled", matchIfMissing = true)
@ConditionalOnBean(SpringDocConfiguration.class)
public class ResourcesConfigProperties {

    private boolean enabled = true;

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
