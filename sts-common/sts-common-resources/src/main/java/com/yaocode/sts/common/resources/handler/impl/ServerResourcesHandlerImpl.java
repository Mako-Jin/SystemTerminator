package com.yaocode.sts.common.resources.handler.impl;

import com.yaocode.sts.common.resources.annotation.ServerResources;
import com.yaocode.sts.common.resources.handler.ServerResourcesHandler;
import com.yaocode.sts.common.resources.handler.SystemResourcesHandler;
import com.yaocode.sts.common.resources.model.ServerResourcesModel;
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
 * @date: 2025年11月19日 21:06
 */
public class ServerResourcesHandlerImpl implements ServerResourcesHandler {

    private static final Logger logger = LoggerFactory.getLogger(ServerResourcesHandlerImpl.class);

    private final List<ServerResourcesModel> serverResourcesModels;

    private ApplicationContext applicationContext;

    private SystemResourcesHandler systemResourcesHandler;

    public ServerResourcesHandlerImpl(ApplicationContext applicationContext, SystemResourcesHandler systemResourcesHandler) {
        this.applicationContext = applicationContext;
        this.systemResourcesHandler = systemResourcesHandler;
        this.serverResourcesModels = new ArrayList<>();
    }

    @Override
    public List<ServerResourcesModel> getResources() {
        return serverResourcesModels;
    }

    @Override
    public void addResources(ServerResourcesModel resource) {
        if (isExist(resource)) {
            logger.warn("服务编码={}已注册", resource.getCode());
            return;
        }
        serverResourcesModels.add(resource);
    }

    @Override
    public boolean isExist(ServerResourcesModel resource) {
        return isExist(resource.getCode());
    }

    @Override
    public boolean isExist(String code) {
        return serverResourcesModels.stream().anyMatch(e -> Objects.equals(e.getCode(), code));
    }

    @Override
    public void convert(List<ServerResources> annotatedResources) {
        if (annotatedResources.isEmpty()) {
            return;
        }
        for (ServerResources annotatedResource : annotatedResources) {
            ServerResourcesModel model = new ServerResourcesModel();
            model.setCode(annotatedResource.code());
            model.setName(annotatedResource.name());
            model.setDesc(annotatedResource.desc());
            model.setMenuIcon(annotatedResource.menuIcon());
            model.setVersion(annotatedResource.version());
            model.setEnable(annotatedResource.isEnabled());
            if (annotatedResource.belongTo().length > 0) {
                systemResourcesHandler.convert(Arrays.asList(annotatedResource.belongTo()));
                systemResourcesHandler.addResources(Arrays.asList(annotatedResource.belongTo()), model);
            }
            this.addResources(model);
        }
    }

    @Override
    public void registryResources() {

    }

    @Override
    public boolean checkResources(ServerResourcesModel resource) {
        return false;
    }

    @Override
    public ApplicationContext getApplicationContext() {
        return this.applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public List<ServerResourcesModel> getServerResourcesModels() {
        return serverResourcesModels;
    }

    public SystemResourcesHandler getSystemResourcesHandler() {
        return systemResourcesHandler;
    }

    public void setSystemResourcesHandler(SystemResourcesHandler systemResourcesHandler) {
        this.systemResourcesHandler = systemResourcesHandler;
    }

    @Override
    public void addResources(List<ServerResources> serverResources, ServiceResourcesModel serviceResourcesModel) {
        List<String> serverCodeList = serverResources.stream()
                .map(ServerResources::code)
                .filter(this::isExist).toList();
        for (String serverCode : serverCodeList) {
            Optional<ServerResourcesModel> optional = serverResourcesModels.stream()
                    .filter(e -> Objects.equals(e.getCode(), serverCode)).findFirst();
            if (optional.isEmpty()) {
                continue;
            }
            optional.get().addServiceResources(serviceResourcesModel);
        }
    }
}
