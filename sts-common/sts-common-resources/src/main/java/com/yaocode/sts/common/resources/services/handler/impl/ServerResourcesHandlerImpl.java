package com.yaocode.sts.common.resources.services.handler.impl;

import com.yaocode.sts.common.basic.enums.OppositeEnums;
import com.yaocode.sts.common.resources.annotation.ServerResources;
import com.yaocode.sts.common.resources.model.ResourcesModel;
import com.yaocode.sts.common.resources.model.ServerResourcesModel;
import com.yaocode.sts.common.resources.services.handler.ServerResourcesHandler;
import com.yaocode.sts.common.resources.services.handler.SystemResourcesHandler;
import com.yaocode.sts.common.resources.utils.PropertyResolverUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 服务资源扫描类
 * @author: Jin-LiangBo
 * @date: 2025年11月19日 21:06
 */
public class ServerResourcesHandlerImpl extends AbstractResourcesHandler<ServerResources, ServerResourcesModel> implements ServerResourcesHandler {

    private static final Logger logger = LoggerFactory.getLogger(ServerResourcesHandlerImpl.class);

    private List<ServerResourcesModel> resources;

    private final SystemResourcesHandler systemResourcesHandler;

    public ServerResourcesHandlerImpl(
            ApplicationContext applicationContext,
            PropertyResolverUtils propertyResolverUtils,
            SystemResourcesHandler systemResourcesHandler
    ) {
        super(applicationContext, propertyResolverUtils);
        this.systemResourcesHandler = systemResourcesHandler;
    }

    @Override
    public List<ServerResourcesModel> build() {
        return this.build(Collections.emptyList());
    }

    @Override
    public List<ServerResourcesModel> build(List<? extends ResourcesModel> parentResourcesModelList) {
        Map<String, Object> serverResourcesMap = this.scanResources();
        Set<String> parentSystemCodeList = parentResourcesModelList.stream()
                .filter(Objects::nonNull)
                .map(ResourcesModel::getCode)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        this.resources = new ArrayList<>();
        // 构建默认的系统资源对象
        ServerResourcesModel serverResourcesModel = new ServerResourcesModel();
        this.buildDefaultResourcesModel(serverResourcesModel);
        this.resources.add(serverResourcesModel);

        if (serverResourcesMap.isEmpty()) {
            return this.resources;
        }

        for (Object bean : serverResourcesMap.values()) {
            Class<?> objClz = bean.getClass();
            ServerResources serverResources = AnnotatedElementUtils.findMergedAnnotation(objClz, ServerResources.class);
            serverResourcesModel = new ServerResourcesModel();
            this.buildResourcesModel(serverResourcesModel, serverResources);
            List<String> parentCodeList = filterParentCode(serverResourcesModel.getParentCode(), parentSystemCodeList);
            serverResourcesModel.setParentCode(parentCodeList);
            this.resources.add(serverResourcesModel);
        }
        return this.resources;
    }

    @Override
    public void buildResourcesModel(ServerResourcesModel resourcesModel, ServerResources resources) {
        super.buildResourcesModel(resourcesModel, resources);
    }

    @Override
    public void buildDefaultResourcesModel(ServerResourcesModel model) {
        super.buildDefaultResourcesModel(model);
        String defaultParentCode = systemResourcesHandler.getDefaultResourceCode();
        model.setParentCode(Collections.singletonList(defaultParentCode));
    }

    @Override
    public void convert(ServerResourcesModel resourcesModel, ServerResources annotatedResource) {
        resourcesModel.setCode(annotatedResource.code());
        resourcesModel.setName(annotatedResource.name());
        resourcesModel.setDesc(annotatedResource.desc());
        resourcesModel.setVersion(annotatedResource.version());
        resourcesModel.setIcon(annotatedResource.icon());
        resourcesModel.setIsDeprecated(OppositeEnums.getCode(annotatedResource.isDeprecated()));
        resourcesModel.setIsEnabled(OppositeEnums.getCode(annotatedResource.isEnabled()));
        resourcesModel.setParentCode(Arrays.asList(annotatedResource.parent()));
    }

    @Override
    public List<ServerResourcesModel> getResources() {
        return this.resources;
    }

    public void setResources(List<ServerResourcesModel> resources) {
        this.resources = resources;
    }

    public SystemResourcesHandler getSystemResourcesHandler() {
        return systemResourcesHandler;
    }
}
