package com.yaocode.sts.common.resources.services;

import com.yaocode.sts.common.resources.model.ModuleResourcesModel;
import com.yaocode.sts.common.resources.model.ResourcesModel;
import com.yaocode.sts.common.resources.model.ServerResourcesModel;
import com.yaocode.sts.common.resources.model.ServiceResourcesModel;
import com.yaocode.sts.common.resources.model.SystemResourcesModel;
import com.yaocode.sts.common.resources.services.handler.ApiResourcesHandler;
import com.yaocode.sts.common.resources.services.handler.ModuleResourcesHandler;
import com.yaocode.sts.common.resources.services.handler.ServerResourcesHandler;
import com.yaocode.sts.common.resources.services.handler.ServiceResourcesHandler;
import com.yaocode.sts.common.resources.services.handler.SystemResourcesHandler;
import com.yaocode.sts.common.resources.services.remote.ResourcesServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 资源处理服务
 * @author: Jin-LiangBo
 * @date: 2025年12月03日 20:46
 */
public class ResourcesBuilder implements ApplicationContextAware, ApplicationListener<ApplicationReadyEvent> {

    private static final Logger logger = LoggerFactory.getLogger(ResourcesBuilder.class);

    private ApplicationContext applicationContext;

    private final SystemResourcesHandler systemResourcesHandler;

    private final ServerResourcesHandler serverResourcesHandler;

    private final ServiceResourcesHandler serviceResourcesHandler;

    private final ModuleResourcesHandler moduleResourcesHandler;

    private final ApiResourcesHandler apiResourcesHandler;

    private final ResourcesServiceClient resourcesServiceClient;

    private final List<ResourcesModel> resourcesModelList = new ArrayList<>();

    public ResourcesBuilder(
            ApplicationContext applicationContext,
            SystemResourcesHandler systemResourcesHandler,
            ServerResourcesHandler serverResourcesHandler,
            ServiceResourcesHandler serviceResourcesHandler,
            ModuleResourcesHandler moduleResourcesHandler,
            ApiResourcesHandler apiResourcesHandler,
            ResourcesServiceClient resourcesServiceClient
    ) {
        this.applicationContext = applicationContext;
        this.systemResourcesHandler = systemResourcesHandler;
        this.serverResourcesHandler = serverResourcesHandler;
        this.serviceResourcesHandler = serviceResourcesHandler;
        this.moduleResourcesHandler = moduleResourcesHandler;
        this.apiResourcesHandler = apiResourcesHandler;
        this.resourcesServiceClient = resourcesServiceClient;
    }

    public void build() {
        List<SystemResourcesModel> systemResourcesModelList = this.systemResourcesHandler.build();
        addResources(systemResourcesModelList);
        List<ServerResourcesModel> serverResourcesModelList = this.serverResourcesHandler.build(systemResourcesModelList);
        addResources(serverResourcesModelList);
        List<ServiceResourcesModel> serviceResourcesModelList = this.serviceResourcesHandler.build(serverResourcesModelList);
        addResources(serviceResourcesModelList);
        List<ModuleResourcesModel> moduleResourcesModelList = this.moduleResourcesHandler.build(serviceResourcesModelList);
        addResources(moduleResourcesModelList);
        addResources(apiResourcesHandler.getResources());
    }

    private void addResources (List<? extends ResourcesModel> resourcesModelList) {
        if (CollectionUtils.isEmpty(resourcesModelList)) {
            return;
        }
        this.resourcesModelList.addAll(resourcesModelList);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        try {
            ResourcesService serviceBean = applicationContext.getBean(ResourcesService.class);
            serviceBean.batchSaveResources(resourcesModelList);
        } catch (NoSuchBeanDefinitionException exception) {
            logger.debug("没有发现Bean：ResourceService, 远程调用");
            resourcesServiceClient.batchSaveResources(resourcesModelList);
        }
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }
}
