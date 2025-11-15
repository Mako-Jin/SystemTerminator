package com.yaocode.sts.common.resources.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年11月15日 19:09
 */
@ConditionalOnWebApplication
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(name = {
        "yaocode.web.resources.enabled, " +
        "yaocode.web.resources.api-docs.enabled"
}, matchIfMissing = true)
public class ResourcesApiDocConfig {
}
