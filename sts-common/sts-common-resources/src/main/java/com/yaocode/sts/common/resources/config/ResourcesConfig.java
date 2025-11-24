package com.yaocode.sts.common.resources.config;

import com.yaocode.sts.common.resources.ResourcesAutoInitEngine;
import com.yaocode.sts.common.resources.ResourcesScanner;
import com.yaocode.sts.common.resources.handler.ModuleResourcesHandler;
import com.yaocode.sts.common.resources.handler.ResourcesHandler;
import com.yaocode.sts.common.resources.handler.ServerResourcesHandler;
import com.yaocode.sts.common.resources.handler.ServiceResourcesHandler;
import com.yaocode.sts.common.resources.handler.SystemResourcesHandler;
import com.yaocode.sts.common.resources.handler.impl.ModuleResourcesHandlerImpl;
import com.yaocode.sts.common.resources.handler.impl.ServerResourcesHandlerImpl;
import com.yaocode.sts.common.resources.handler.impl.ServiceResourcesHandlerImpl;
import com.yaocode.sts.common.resources.handler.impl.SystemResourcesHandlerImpl;
import com.yaocode.sts.common.resources.model.ResourcesModel;
import com.yaocode.sts.common.resources.properties.ResourcesConfigProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.Annotation;
import java.util.List;

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
            ResourcesScanner<?, ?> resourcesScanner
    ) {
        return new ResourcesAutoInitEngine(resourcesConfigProperties, resourcesScanner);
    }

    @Bean
    @ConditionalOnMissingBean
    public <A extends Annotation, M extends ResourcesModel> ResourcesScanner<A, M> resourcesScanner(
            List<ResourcesHandler<A, M>> resourceHandlers,
            ResourcesConfigProperties resourcesConfigProperties
    ) {
        return new ResourcesScanner<>(resourcesConfigProperties, resourceHandlers);
    }

    @Bean
    public SystemResourcesHandler systemResourcesHandler(ApplicationContext applicationContext) {
        return new SystemResourcesHandlerImpl(applicationContext);
    }

    @Bean
    public ServerResourcesHandler serverResourcesHandler(ApplicationContext applicationContext, SystemResourcesHandler systemResourcesHandler) {
        return new ServerResourcesHandlerImpl(applicationContext, systemResourcesHandler);
    }

    @Bean
    public ServiceResourcesHandler serviceResourcesHandler(ApplicationContext applicationContext, ServerResourcesHandler serverResourcesHandler) {
        return new ServiceResourcesHandlerImpl(applicationContext, serverResourcesHandler);
    }

    @Bean
    public ModuleResourcesHandler moduleResourcesHandler(ApplicationContext applicationContext, ServiceResourcesHandler serviceResourcesHandler) {
        return new ModuleResourcesHandlerImpl(applicationContext, serviceResourcesHandler);
    }

    // @Bean
    // public ApiResourcesHandler apiResourcesHandler() {
    //     return new ApiResourcesHandler();
    // }
    //
    // @Bean
    // public MenuResourcesHandler menuResourcesHandler() {
    //     return new MenuResourcesHandler();
    // }
    //
    // @Bean
    // public PagesResourcesHandler menuResourcesHandler() {
    //     return new MenuResourcesHandler();
    // }
    //
    // @Bean
    // public DataResourcesHandler dataResourcesHandler() {
    //     return new DataResourcesHandler();
    // }

}
