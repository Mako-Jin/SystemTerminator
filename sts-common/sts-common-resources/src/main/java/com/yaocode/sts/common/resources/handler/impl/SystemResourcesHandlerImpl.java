package com.yaocode.sts.common.resources.handler.impl;

import com.yaocode.sts.common.resources.annotation.SystemResources;
import com.yaocode.sts.common.resources.handler.SystemResourcesHandler;
import com.yaocode.sts.common.resources.model.ServerResourcesModel;
import com.yaocode.sts.common.resources.model.SystemResourcesModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 系统资源处理类
 * @author: Jin-LiangBo
 * @date: 2025年11月19日 21:06
 */
public class SystemResourcesHandlerImpl implements SystemResourcesHandler {

    private static final Logger logger = LoggerFactory.getLogger(SystemResourcesHandlerImpl.class);

    private final List<SystemResourcesModel> systemResourcesModelList;

    private ApplicationContext applicationContext;

    public SystemResourcesHandlerImpl(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.systemResourcesModelList = new ArrayList<>();
    }

    @Override
    public List<SystemResourcesModel> getResources() {
        return systemResourcesModelList;
    }

    @Override
    public void addResources(SystemResourcesModel resource) {
        if (isExist(resource)) {
            logger.warn("系统编码={}已注册", resource.getCode());
            return;
        }
        systemResourcesModelList.add(resource);
    }

    @Override
    public boolean isExist(SystemResourcesModel resource) {
        if (isExist(resource.getCode())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isExist(String code) {
        if (systemResourcesModelList.stream().anyMatch(e -> Objects.equals(e.getCode(), code))) {
            return true;
        }
        return false;
    }

    @Override
    public void convert(List<SystemResources> annotatedResources) {
        if (annotatedResources.isEmpty()) {
            return;
        }
        for (SystemResources annotatedResource : annotatedResources) {
            SystemResourcesModel model = new SystemResourcesModel();
            model.setCode(annotatedResource.code());
            model.setName(annotatedResource.name());
            model.setDesc(annotatedResource.desc());
            model.setMenuIcon(annotatedResource.menuIcon());
            model.setVersion(annotatedResource.version());
            this.addResources(model);
        }
    }

    @Override
    public void addServerResource(List<SystemResources> systemResources, ServerResourcesModel serverResourcesModel) {
        List<String> systemCodeList = systemResources.stream()
                    .map(SystemResources::code)
                    .filter(this::isExist).toList();
        for (String systemCode : systemCodeList) {
            Optional<SystemResourcesModel> optional = systemResourcesModelList.stream()
                    .filter(e -> Objects.equals(e.getCode(), systemCode)).findFirst();
            if (optional.isEmpty()) {
                continue;
            }
            optional.get().addServerResources(serverResourcesModel);
        }
    }

    @Override
    public void registryResources() {

    }

    @Override
    public boolean checkResources(SystemResourcesModel resource) {
        return false;
    }

    @Override
    public ApplicationContext getApplicationContext() {
        return this.applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
