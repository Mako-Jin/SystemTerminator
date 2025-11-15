package com.yaocode.sts.common.resources.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年11月15日 20:08
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnBean({ResourcesApiDocConfig.class})
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnProperty(name = {
        "yaocode.web.resources.enabled, " +
        "yaocode.web.resources.api-docs.enabled, " +
        "yaocode.web.resources.api-ui.enabled"
}, matchIfMissing = true)
public class ResourcesApiUiConfig {
}
