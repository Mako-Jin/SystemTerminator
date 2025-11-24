package com.yaocode.sts.common.resources.handler.impl;

import com.yaocode.sts.common.resources.annotation.ModuleResources;
import com.yaocode.sts.common.resources.handler.ModuleResourcesHandler;
import com.yaocode.sts.common.resources.handler.ServiceResourcesHandler;
import com.yaocode.sts.common.resources.model.ModuleResourcesModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 模块资源处理类实现层
 * @author: Jin-LiangBo
 * @date: 2025年11月21日 20:47
 */
public class ModuleResourcesHandlerImpl implements ModuleResourcesHandler {

    private static final Logger logger = LoggerFactory.getLogger(ModuleResourcesHandlerImpl.class);

    private final List<ModuleResourcesModel> moduleResourcesModels;

    private ServiceResourcesHandler serviceResourcesHandler;

    private ApplicationContext applicationContext;

    public ModuleResourcesHandlerImpl(ApplicationContext applicationContext, ServiceResourcesHandler serviceResourcesHandler) {
        this.applicationContext = applicationContext;
        this.serviceResourcesHandler = serviceResourcesHandler;
        moduleResourcesModels = new ArrayList<>();
    }

    @Override
    public List<ModuleResourcesModel> getResources() {
        return moduleResourcesModels;
    }

    @Override
    public void addResources(ModuleResourcesModel resource) {
        if (isExist(resource)) {
            logger.warn("module编码={}已存在", resource.getCode());
            return;
        }
        moduleResourcesModels.add(resource);
    }

    @Override
    public boolean isExist(ModuleResourcesModel resource) {
        return isExist(resource.getCode());
    }

    @Override
    public boolean isExist(String code) {
        return moduleResourcesModels.stream().anyMatch(e -> Objects.equals(e.getCode(), code));
    }

    @Override
    public void convert(List<ModuleResources> annotatedResources) {
        if (annotatedResources.isEmpty()) {
            return;
        }
        for (ModuleResources annotatedResource : annotatedResources) {
            ModuleResourcesModel model = new ModuleResourcesModel();
            model.setCode(annotatedResource.code());
            model.setName(annotatedResource.name());
            model.setDesc(annotatedResource.desc());
            model.setPath(annotatedResource.path());
            model.setVersion(annotatedResource.version());
            model.setEnabled(annotatedResource.isEnabled());
            model.setDeprecated(annotatedResource.isDeprecated());
            model.setWhiteList(annotatedResource.isWhiteList());
            if (annotatedResource.belongTo().length > 0) {
                serviceResourcesHandler.convert(Arrays.asList(annotatedResource.belongTo()));
                serviceResourcesHandler.addResources(Arrays.asList(annotatedResource.belongTo()), model);
            }
            this.addResources(model);
        }
    }

    @Override
    public void registryResources() {

    }

    @Override
    public boolean checkResources(ModuleResourcesModel resource) {
        return false;
    }

    @Override
    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public List<ModuleResourcesModel> getModuleResourcesModels() {
        return moduleResourcesModels;
    }

    public ServiceResourcesHandler getServiceResourcesHandler() {
        return serviceResourcesHandler;
    }

    public void setServiceResourcesHandler(ServiceResourcesHandler serviceResourcesHandler) {
        this.serviceResourcesHandler = serviceResourcesHandler;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
