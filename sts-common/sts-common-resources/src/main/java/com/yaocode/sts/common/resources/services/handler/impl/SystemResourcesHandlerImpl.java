package com.yaocode.sts.common.resources.services.handler.impl;

import com.yaocode.sts.common.basic.enums.OppositeEnums;
import com.yaocode.sts.common.resources.annotation.SystemResources;
import com.yaocode.sts.common.resources.model.ResourcesModel;
import com.yaocode.sts.common.resources.model.SystemResourcesModel;
import com.yaocode.sts.common.resources.services.handler.SystemResourcesHandler;
import com.yaocode.sts.common.resources.utils.PropertyResolverUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 系统资源处理类
 * @author: Jin-LiangBo
 * @date: 2025年11月19日 21:06
 */
public class SystemResourcesHandlerImpl extends AbstractResourcesHandler<SystemResources, SystemResourcesModel> implements SystemResourcesHandler {

    private static final Logger logger = LoggerFactory.getLogger(SystemResourcesHandlerImpl.class);

    public SystemResourcesHandlerImpl(ApplicationContext applicationContext, PropertyResolverUtils propertyResolverUtils) {
        super(applicationContext, propertyResolverUtils);
    }

    @Override
    public List<SystemResourcesModel> build() {
        Map<String, Object> systemResourcesMap = this.scanResources();
        List<SystemResourcesModel> systemResourcesModelList = new ArrayList<>();
        // 构建默认的系统资源对象
        SystemResourcesModel systemResourcesModel = new SystemResourcesModel();
        this.buildDefaultResourcesModel(systemResourcesModel);
        systemResourcesModelList.add(systemResourcesModel);

        if (systemResourcesMap.isEmpty()) {
            ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
            scanner.addIncludeFilter(new AnnotationTypeFilter(SystemResources.class));
            if (!AutoConfigurationPackages.has(getApplicationContext())) {
                return Collections.emptyList();
            }
            List<String> packagesToScan = AutoConfigurationPackages.get(getApplicationContext());
            SystemResources systemResources = getSystemResourcesDefClass(scanner, packagesToScan);
            if (Objects.nonNull(systemResources)) {
                systemResourcesModel = new SystemResourcesModel();
                this.buildResourcesModel(systemResourcesModel, systemResources);
                systemResourcesModelList.add(systemResourcesModel);
            }
            return systemResourcesModelList;
        }

        for (Object bean : systemResourcesMap.values()) {
            Class<?> objClz = bean.getClass();
            SystemResources systemResources = AnnotatedElementUtils.findMergedAnnotation(objClz, SystemResources.class);
            systemResourcesModel = new SystemResourcesModel();
            this.buildResourcesModel(systemResourcesModel, systemResources);
            systemResourcesModelList.add(systemResourcesModel);
        }
        return systemResourcesModelList;
    }

    @Override
    public List<SystemResourcesModel> build(List<? extends ResourcesModel> parentResourcesModelList) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void convert(SystemResourcesModel resourcesModel, SystemResources annotatedResource) {
        resourcesModel.setCode(annotatedResource.code());
        resourcesModel.setName(annotatedResource.name());
        resourcesModel.setDesc(annotatedResource.desc());
        resourcesModel.setVersion(annotatedResource.version());
        resourcesModel.setIcon(annotatedResource.icon());
        if (Objects.nonNull(annotatedResource.isDeprecated())) {
            resourcesModel.setIsDeprecated(OppositeEnums.getCode(annotatedResource.isDeprecated()));
        }
        if (Objects.nonNull(annotatedResource.isDeprecated())) {
            resourcesModel.setIsEnabled(OppositeEnums.getCode(annotatedResource.isEnabled()));
        }
    }

    @Override
    public void buildResourcesModel(SystemResourcesModel resourcesModel, SystemResources resources) {
        super.buildResourcesModel(resourcesModel, resources);
    }

    @Override
    public void buildDefaultResourcesModel(SystemResourcesModel systemResourcesModel) {
        super.buildDefaultResourcesModel(systemResourcesModel);
    }

    private SystemResources getSystemResourcesDefClass(ClassPathScanningCandidateComponentProvider scanner, List<String> packagesToScan) {
        for (String pack : packagesToScan) {
            for (BeanDefinition bd : scanner.findCandidateComponents(pack)) {
                try {
                    return AnnotationUtils.findAnnotation(Class.forName(bd.getBeanClassName()), SystemResources.class);
                } catch (ClassNotFoundException e) {
                    logger.error("Class Not Found in classpath : {}", e.getMessage());
                }
            }
        }
        return null;
    }

}
