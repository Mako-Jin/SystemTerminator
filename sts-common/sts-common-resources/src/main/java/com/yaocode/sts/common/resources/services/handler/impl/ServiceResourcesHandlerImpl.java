package com.yaocode.sts.common.resources.services.handler.impl;


import com.yaocode.sts.common.basic.enums.OppositeEnums;
import com.yaocode.sts.common.resources.annotation.ServiceResources;
import com.yaocode.sts.common.resources.constants.IConstants;
import com.yaocode.sts.common.resources.model.ResourcesModel;
import com.yaocode.sts.common.resources.model.ServiceResourcesModel;
import com.yaocode.sts.common.resources.services.handler.ServerResourcesHandler;
import com.yaocode.sts.common.resources.services.handler.ServiceResourcesHandler;
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
 * @date: 2025年11月19日 21:07
 */
public class ServiceResourcesHandlerImpl extends AbstractResourcesHandler<ServiceResources, ServiceResourcesModel> implements ServiceResourcesHandler {

    private static final Logger logger = LoggerFactory.getLogger(ServiceResourcesHandlerImpl.class);

    private final ServerResourcesHandler serverResourcesHandler;

    private List<ServiceResourcesModel> resources;

    public ServiceResourcesHandlerImpl(
            ApplicationContext applicationContext,
            PropertyResolverUtils propertyResolverUtils,
            ServerResourcesHandler serverResourcesHandler
    ) {
        super(applicationContext, propertyResolverUtils);
        this.serverResourcesHandler = serverResourcesHandler;
    }

    @Override
    public List<ServiceResourcesModel> build() {
        return this.build(Collections.emptyList());
    }

    @Override
    public List<ServiceResourcesModel> build(List<? extends ResourcesModel> parentResourcesModelList) {
        Map<String, Object> serviceResourcesMap = this.scanResources();
        Set<String> parentServerCodeList = parentResourcesModelList.stream()
                .filter(Objects::nonNull)
                .map(ResourcesModel::getCode)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        this.resources = new ArrayList<>();
        // 构建默认的系统资源对象
        ServiceResourcesModel serviceResourcesModel = new ServiceResourcesModel();
        this.buildDefaultResourcesModel(serviceResourcesModel);
        this.resources.add(serviceResourcesModel);

        if (serviceResourcesMap.isEmpty()) {
            return this.resources;
        }

        for (Object bean : serviceResourcesMap.values()) {
            Class<?> objClz = bean.getClass();
            ServiceResources serviceResources = AnnotatedElementUtils.findMergedAnnotation(objClz, ServiceResources.class);
            serviceResourcesModel = new ServiceResourcesModel();
            this.buildResourcesModel(serviceResourcesModel, serviceResources);
            List<String> parentCodeList = filterParentCode(serviceResourcesModel.getParentCode(), parentServerCodeList);
            serviceResourcesModel.setParentCode(parentCodeList);
            checkAndSetServicePath(serviceResourcesModel);
            this.resources.add(serviceResourcesModel);
        }
        return this.resources;
    }

    private void checkAndSetServicePath(ServiceResourcesModel serviceResourcesModel) {
        String servletPath = getApplicationContext().getEnvironment()
                .getProperty(IConstants.SERVLET_CONTEXT_PATH_KEY, "");
        if (!Objects.equals(serviceResourcesModel.getPath(), servletPath)) {
            logger.warn("路径配置错误，已更新成生效路径");
            serviceResourcesModel.setPath(servletPath);
        }
    }

    @Override
    public void buildDefaultResourcesModel(ServiceResourcesModel model) {
        super.buildDefaultResourcesModel(model);
        String defaultParentCode = serverResourcesHandler.getDefaultResourceCode();
        model.setParentCode(Collections.singletonList(defaultParentCode));
    }

    @Override
    public void buildResourcesModel(ServiceResourcesModel resourcesModel, ServiceResources resources) {
        super.buildResourcesModel(resourcesModel, resources);
    }

    @Override
    public void convert(ServiceResourcesModel resourcesModel, ServiceResources annotatedResource) {
        resourcesModel.setCode(annotatedResource.code());
        resourcesModel.setName(annotatedResource.name());
        resourcesModel.setDesc(annotatedResource.desc());
        resourcesModel.setVersion(annotatedResource.version());
        resourcesModel.setIcon(annotatedResource.icon());
        resourcesModel.setPath(annotatedResource.path());
        resourcesModel.setIsDeprecated(OppositeEnums.getCode(annotatedResource.isDeprecated()));
        resourcesModel.setIsEnabled(OppositeEnums.getCode(annotatedResource.isEnabled()));
        resourcesModel.setParentCode(Arrays.asList(annotatedResource.parent()));
    }

    @Override
    public List<ServiceResourcesModel> getResources() {
        return this.resources;
    }

    public void setResources(List<ServiceResourcesModel> resources) {
        this.resources = resources;
    }
}
