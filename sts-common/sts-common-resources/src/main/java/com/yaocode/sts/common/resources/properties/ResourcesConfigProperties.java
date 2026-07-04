package com.yaocode.sts.common.resources.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 资源自动扫描入库相关属性配置类
 * @author: Jin-LiangBo
 * @date: 2025年11月15日 16:12
 */
@Setter
@Getter
@Configuration(proxyBeanMethods = false)
@ConfigurationProperties(prefix = ResourcesConfigProperties.RESOURCES_CONFIG_PREFIX)
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

}
