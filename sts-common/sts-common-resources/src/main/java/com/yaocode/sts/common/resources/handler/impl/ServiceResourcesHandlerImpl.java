package com.yaocode.sts.common.resources.handler.impl;

import com.yaocode.sts.common.resources.annotation.ServiceResources;
import com.yaocode.sts.common.resources.handler.ServerResourcesHandler;
import com.yaocode.sts.common.resources.handler.ServiceResourcesHandler;
import com.yaocode.sts.common.resources.model.ModuleResourcesModel;
import com.yaocode.sts.common.resources.model.ServiceResourcesModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 服务资源扫描类
 * @author: Jin-LiangBo
 * @date: 2025年11月19日 21:07
 */
public class ServiceResourcesHandlerImpl implements ServiceResourcesHandler {

    private static final Logger logger = LoggerFactory.getLogger(ServiceResourcesHandlerImpl.class);

    private final List<ServiceResourcesModel> serviceResourcesModels;

    private ServerResourcesHandler serverResourcesHandler;

    private ApplicationContext applicationContext;

    public ServiceResourcesHandlerImpl(ApplicationContext applicationContext, ServerResourcesHandler serverResourcesHandler) {
        this.applicationContext = applicationContext;
        this.serverResourcesHandler = serverResourcesHandler;
        serviceResourcesModels = new ArrayList<>();
    }

    @Override
    public List<ServiceResourcesModel> getResources() {
        return serviceResourcesModels;
    }

    @Override
    public void addResources(ServiceResourcesModel resource) {
        if (isExist(resource)) {
            logger.warn("service编码={}已存在", resource.getCode());
            return;
        }
        serviceResourcesModels.add(resource);
    }

    @Override
    public boolean isExist(ServiceResourcesModel resource) {
        return isExist(resource.getCode());
    }

    @Override
    public boolean isExist(String code) {
        return serviceResourcesModels.stream().anyMatch(e -> Objects.equals(e.getCode(), code));
    }

    @Override
    public void convert(List<ServiceResources> annotatedResources) {
        if (annotatedResources.isEmpty()) {
            return;
        }
        for (ServiceResources annotatedResource : annotatedResources) {
            ServiceResourcesModel model = new ServiceResourcesModel();
            model.setCode(annotatedResource.code());
            model.setName(annotatedResource.name());
            model.setDesc(annotatedResource.desc());
            model.setMenuIcon(annotatedResource.menuIcon());
            model.setVersion(annotatedResource.version());
            model.setEnable(annotatedResource.isEnabled());
            model.setPath(annotatedResource.path());
            if (annotatedResource.belongTo().length > 0) {
                serverResourcesHandler.convert(Arrays.asList(annotatedResource.belongTo()));
                serverResourcesHandler.addResources(Arrays.asList(annotatedResource.belongTo()), model);
            }
            this.addResources(model);
        }
    }

    @Override
    public void registryResources() {

    }

    @Override
    public boolean checkResources(ServiceResourcesModel resource) {
        return false;
    }

    @Override
    public ApplicationContext getApplicationContext() {
        return this.applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public List<ServiceResourcesModel> getServiceResourcesModels() {
        return serviceResourcesModels;
    }

    public ServerResourcesHandler getServerResourcesHandler() {
        return serverResourcesHandler;
    }

    public void setServerResourcesHandler(ServerResourcesHandler serverResourcesHandler) {
        this.serverResourcesHandler = serverResourcesHandler;
    }

    @Override
    public void addResources(List<ServiceResources> serviceResources, ModuleResourcesModel moduleResourcesModel) {
        List<String> serviceCodeList = serviceResources.stream()
                .map(ServiceResources::code)
                .filter(this::isExist).toList();
        for (String serviceCode : serviceCodeList) {
            Optional<ServiceResourcesModel> optional = serviceResourcesModels.stream()
                    .filter(e -> Objects.equals(e.getCode(), serviceCode)).findFirst();
            if (optional.isEmpty()) {
                continue;
            }
            optional.get().addModuleResources(moduleResourcesModel);
        }
    }
}
