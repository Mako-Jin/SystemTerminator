package com.yaocode.sts.common.resources.services.handler.impl;

import com.yaocode.sts.common.basic.enums.OppositeEnums;
import com.yaocode.sts.common.resources.annotation.ModuleResources;
import com.yaocode.sts.common.resources.model.ModuleResourcesModel;
import com.yaocode.sts.common.resources.model.ResourcesModel;
import com.yaocode.sts.common.resources.services.handler.ApiResourcesHandler;
import com.yaocode.sts.common.resources.services.handler.ModuleResourcesHandler;
import com.yaocode.sts.common.resources.services.handler.ServiceResourcesHandler;
import com.yaocode.sts.common.resources.utils.PropertyResolverUtils;
import com.yaocode.sts.common.web.annotation.SubRequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 模块资源处理类实现层
 * @author: Jin-LiangBo
 * @date: 2025年11月21日 20:47
 */
public class ModuleResourcesHandlerImpl extends AbstractResourcesHandler<ModuleResources, ModuleResourcesModel> implements ModuleResourcesHandler {

    private static final Logger logger = LoggerFactory.getLogger(ModuleResourcesHandlerImpl.class);

    private final ServiceResourcesHandler serviceResourcesHandler;

    private final ApiResourcesHandler apiResourcesHandler;

    private List<ModuleResourcesModel> resources;

    public ModuleResourcesHandlerImpl(
            ApplicationContext applicationContext,
            PropertyResolverUtils propertyResolverUtils,
            ServiceResourcesHandler serviceResourcesHandler,
            ApiResourcesHandler apiResourcesHandler
    ) {
        super(applicationContext, propertyResolverUtils);
        this.serviceResourcesHandler = serviceResourcesHandler;
        this.apiResourcesHandler = apiResourcesHandler;
    }

    @Override
    public List<ModuleResourcesModel> build() {
        return this.build(Collections.emptyList());
    }

    @Override
    public List<ModuleResourcesModel> build(List<? extends ResourcesModel> parentResourcesModelList) {
        Map<String, Object> moduleResourcesMap = this.scanResources();
        Set<String> parentServerCodeList = parentResourcesModelList.stream()
                .filter(Objects::nonNull)
                .map(ResourcesModel::getCode)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        this.resources = new ArrayList<>();
        // 构建默认的系统资源对象
        ModuleResourcesModel moduleResourcesModel = new ModuleResourcesModel();
        this.buildDefaultResourcesModel(moduleResourcesModel);
        this.resources.add(moduleResourcesModel);

        if (moduleResourcesMap.isEmpty()) {
            return this.resources;
        }

        for (Object bean : moduleResourcesMap.values()) {
            Class<?> objClz = bean.getClass();
            ModuleResources moduleResources = AnnotatedElementUtils.findMergedAnnotation(objClz, ModuleResources.class);
            moduleResourcesModel = new ModuleResourcesModel();
            this.buildResourcesModel(moduleResourcesModel, moduleResources);
            List<String> parentCodeList = filterParentCode(moduleResourcesModel.getParentCode(), parentServerCodeList);
            moduleResourcesModel.setParentCode(parentCodeList);
            List<String> requestPath = checkAndBuildRequestPath(objClz, moduleResourcesModel.getPath());
            moduleResourcesModel.setPath(requestPath);
            this.resources.add(moduleResourcesModel);
            // api的应该放到这块，没有直接扫描方法注解的，得循环类。
            apiResourcesHandler.build(objClz, moduleResourcesModel);
        }
        return this.resources;
    }

    private List<String> checkAndBuildRequestPath(Class<?> clazz, List<String> annotationPathList) {
        List<String> requestPaths = getAnnotationPaths(clazz, RequestMapping.class);
        List<String> subPaths = getAnnotationPaths(clazz, SubRequestMapping.class);

        List<String> combinePathList = requestPaths.stream().flatMap(
                requestPath -> subPaths.stream().map(subPath -> combinePath(requestPath, subPath))
        ).collect(Collectors.toList());
        if (combinePathList.isEmpty()) {
            logger.warn("扫描路径为空");
            return Collections.emptyList();
        }
        if (annotationPathList.stream().filter(StringUtils::hasText).toList().isEmpty()) {
            logger.warn("配置路径为空，已设置扫描路径");
            return combinePathList;
        }
        List<String> finalResultPathList = annotationPathList.stream().filter(combinePathList::contains).toList();
        if (finalResultPathList.size() != annotationPathList.size()) {
            logger.warn("已过滤掉{}个不生效路径", annotationPathList.size() - finalResultPathList.size());
        }
        return finalResultPathList;
    }

    private <T extends Annotation> List<String> getAnnotationPaths(Class<?> clazz, Class<T> annotationClass) {
        return Optional.ofNullable(AnnotationUtils.findAnnotation(clazz, annotationClass))
                .map(this::extractPaths)
                .orElse(Collections.emptyList());
    }

    private List<String> extractPaths(Annotation annotation) {
        String[] paths;
        if (annotation instanceof RequestMapping rm) {
            paths = rm.path().length > 0 ? rm.path() : rm.value();
        } else if (annotation instanceof SubRequestMapping srm) {
            paths = srm.path().length > 0 ? srm.path() : srm.value();
        } else {
            return Collections.emptyList();
        }
        return Arrays.stream(paths).filter(StringUtils::hasText).collect(Collectors.toList());
    }

    private String combinePath(String path1, String path2) {
        // 使用 Spring 的 UriComponentsBuilder 进行路径拼接
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath("");
        if (StringUtils.hasText(path1)) {
            builder.path(path1);
        }
        if (StringUtils.hasText(path2)) {
            builder.path(path2);
        }
        return builder.build().getPath();
    }

    @Override
    public void buildDefaultResourcesModel(ModuleResourcesModel model) {
        super.buildDefaultResourcesModel(model);
        String defaultParentCode = serviceResourcesHandler.getDefaultResourceCode();
        model.setParentCode(Collections.singletonList(defaultParentCode));
        model.setIsWhiteList(OppositeEnums.NO.getCode());
    }

    @Override
    public void convert(ModuleResourcesModel resourcesModel, ModuleResources annotatedResource) {
        resourcesModel.setCode(annotatedResource.code());
        resourcesModel.setName(annotatedResource.name());
        resourcesModel.setDesc(annotatedResource.desc());
        resourcesModel.setVersion(annotatedResource.version());
        resourcesModel.setIcon(annotatedResource.icon());
        resourcesModel.setPath(Arrays.asList(annotatedResource.path()));
        resourcesModel.setIsWhiteList(OppositeEnums.getCode(annotatedResource.isWhiteList()));
        resourcesModel.setIsDeprecated(OppositeEnums.getCode(annotatedResource.isDeprecated()));
        resourcesModel.setIsEnabled(OppositeEnums.getCode(annotatedResource.isEnabled()));
        resourcesModel.setParentCode(Arrays.asList(annotatedResource.parent()));
    }

    @Override
    public List<ModuleResourcesModel> getResources() {
        return this.resources;
    }

    public void setResources(List<ModuleResourcesModel> resources) {
        this.resources = resources;
    }
}
