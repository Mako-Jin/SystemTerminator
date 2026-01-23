package com.yaocode.sts.common.resources.services;

import com.yaocode.sts.common.resources.model.ApiResourcesModel;
import com.yaocode.sts.common.resources.model.ModuleResourcesModel;
import com.yaocode.sts.common.resources.model.ServerResourcesModel;
import com.yaocode.sts.common.resources.model.ServiceResourcesModel;
import com.yaocode.sts.common.resources.model.SystemResourcesModel;
import com.yaocode.sts.common.resources.services.handler.ApiResourcesHandler;
import com.yaocode.sts.common.resources.services.handler.ModuleResourcesHandler;
import com.yaocode.sts.common.resources.services.handler.ServerResourcesHandler;
import com.yaocode.sts.common.resources.services.handler.ServiceResourcesHandler;
import com.yaocode.sts.common.resources.services.handler.SystemResourcesHandler;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.List;

/**
 * 资源处理服务
 * @author: Jin-LiangBo
 * @date: 2025年12月03日 20:46
 */
public class ResourcesBuilder implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    private final SystemResourcesHandler systemResourcesHandler;

    private final ServerResourcesHandler serverResourcesHandler;

    private final ServiceResourcesHandler serviceResourcesHandler;

    private final ModuleResourcesHandler moduleResourcesHandler;

    private final ApiResourcesHandler apiResourcesHandler;

    public ResourcesBuilder(
            ApplicationContext applicationContext,
            SystemResourcesHandler systemResourcesHandler,
            ServerResourcesHandler serverResourcesHandler,
            ServiceResourcesHandler serviceResourcesHandler,
            ModuleResourcesHandler moduleResourcesHandler,
            ApiResourcesHandler apiResourcesHandler
    ) {
        this.applicationContext = applicationContext;
        this.systemResourcesHandler = systemResourcesHandler;
        this.serverResourcesHandler = serverResourcesHandler;
        this.serviceResourcesHandler = serviceResourcesHandler;
        this.moduleResourcesHandler = moduleResourcesHandler;
        this.apiResourcesHandler = apiResourcesHandler;
    }

    public void build() {
        List<SystemResourcesModel> systemResourcesModelList = this.systemResourcesHandler.build();
        List<ServerResourcesModel> serverResourcesModelList = this.serverResourcesHandler.build(systemResourcesModelList);
        List<ServiceResourcesModel> serviceResourcesModelList = this.serviceResourcesHandler.build(serverResourcesModelList);
        List<ModuleResourcesModel> moduleResourcesModelList = this.moduleResourcesHandler.build(serviceResourcesModelList);
        List<ApiResourcesModel> apiResourcesModelList = this.apiResourcesHandler.getResources();
        // if (applicationContext.get) {
        //
        // }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
