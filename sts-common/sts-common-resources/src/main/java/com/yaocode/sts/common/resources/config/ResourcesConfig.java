package com.yaocode.sts.common.resources.config;

import com.yaocode.sts.common.resources.ResourcesAutoInitEngine;
import com.yaocode.sts.common.resources.properties.ResourcesConfigProperties;
import com.yaocode.sts.common.resources.services.ResourcesBuilder;
import com.yaocode.sts.common.resources.services.handler.ApiResourcesHandler;
import com.yaocode.sts.common.resources.services.handler.ModuleResourcesHandler;
import com.yaocode.sts.common.resources.services.handler.ServerResourcesHandler;
import com.yaocode.sts.common.resources.services.handler.ServiceResourcesHandler;
import com.yaocode.sts.common.resources.services.handler.SystemResourcesHandler;
import com.yaocode.sts.common.resources.services.handler.impl.ApiResourcesHandlerImpl;
import com.yaocode.sts.common.resources.services.handler.impl.ModuleResourcesHandlerImpl;
import com.yaocode.sts.common.resources.services.handler.impl.ServerResourcesHandlerImpl;
import com.yaocode.sts.common.resources.services.handler.impl.ServiceResourcesHandlerImpl;
import com.yaocode.sts.common.resources.services.handler.impl.SystemResourcesHandlerImpl;
import com.yaocode.sts.common.resources.utils.PropertyResolverUtils;
import com.yaocode.sts.common.tools.messages.MessageUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
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
            ResourcesConfigProperties resourcesConfigProperties,
            ResourcesBuilder resourcesBuilder
    ) {
        return new ResourcesAutoInitEngine(resourcesConfigProperties, resourcesBuilder);
    }

    @Bean
    @ConditionalOnMissingBean
    public ResourcesBuilder resourcesBuilder(
            ApplicationContext applicationContext,
            SystemResourcesHandler systemResourcesHandler,
            ServerResourcesHandler serverResourcesHandler,
            ServiceResourcesHandler serviceResourcesHandler,
            ModuleResourcesHandler moduleResourcesHandler,
            ApiResourcesHandler apiResourcesHandler
    ) {
        return new ResourcesBuilder(
                applicationContext,
                systemResourcesHandler,
                serverResourcesHandler,
                serviceResourcesHandler,
                moduleResourcesHandler,
                apiResourcesHandler
        );
    }

    @Bean
    public PropertyResolverUtils propertyResolverUtils(
            ConfigurableBeanFactory configurableBeanFactory,
            MessageUtils messageUtils,
            ResourcesConfigProperties resourcesConfigProperties
    ) {
        return new PropertyResolverUtils(configurableBeanFactory, messageUtils, resourcesConfigProperties);
    }

    @Bean
    public SystemResourcesHandler systemResourcesHandler(ApplicationContext applicationContext, PropertyResolverUtils propertyResolverUtils) {
        return new SystemResourcesHandlerImpl(applicationContext, propertyResolverUtils);
    }

    @Bean
    public ServerResourcesHandler serverResourcesHandler(
            ApplicationContext applicationContext,
            PropertyResolverUtils propertyResolverUtils,
            SystemResourcesHandler systemResourcesHandler
    ) {
        return new ServerResourcesHandlerImpl(applicationContext, propertyResolverUtils, systemResourcesHandler);
    }

    @Bean
    public ServiceResourcesHandler serviceResourcesHandler(
            ApplicationContext applicationContext,
            PropertyResolverUtils propertyResolverUtils,
            ServerResourcesHandler serverResourcesHandler
    ) {
        return new ServiceResourcesHandlerImpl(applicationContext, propertyResolverUtils, serverResourcesHandler);
    }

    @Bean
    public ModuleResourcesHandler moduleResourcesHandler(
            ApplicationContext applicationContext,
            PropertyResolverUtils propertyResolverUtils,
            ServiceResourcesHandler serviceResourcesHandler,
            ApiResourcesHandler apiResourcesHandler
    ) {
        return new ModuleResourcesHandlerImpl(applicationContext, propertyResolverUtils, serviceResourcesHandler, apiResourcesHandler);
    }

    @Bean
    public ApiResourcesHandler apiResourcesHandler(
            ApplicationContext applicationContext,
            PropertyResolverUtils propertyResolverUtils
    ) {
        return new ApiResourcesHandlerImpl(applicationContext, propertyResolverUtils);
    }

}
