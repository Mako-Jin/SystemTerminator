package com.yaocode.sts.common.resources.handler.impl;

import com.yaocode.sts.common.resources.annotation.ApiResources;
import com.yaocode.sts.common.resources.handler.ApiResourcesHandler;
import com.yaocode.sts.common.resources.handler.ModuleResourcesHandler;
import com.yaocode.sts.common.resources.model.ApiResourcesModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Api接口资源注解
 * @author: Jin-LiangBo
 * @date: 2025年11月26日 22:55
 */
public class ApiResourcesHandlerImpl implements ApiResourcesHandler {

    private static final Logger logger = LoggerFactory.getLogger(ModuleResourcesHandlerImpl.class);

    private final List<ApiResourcesModel> apiResourcesModels;

    private ModuleResourcesHandler moduleResourcesHandler;

    private ApplicationContext applicationContext;

    public ApiResourcesHandlerImpl(ApplicationContext applicationContext, ModuleResourcesHandler moduleResourcesHandler) {
        this.moduleResourcesHandler = moduleResourcesHandler;
        this.applicationContext = applicationContext;
        apiResourcesModels = new ArrayList<>();
    }

    @Override
    public List<ApiResourcesModel> getResources() {
        return apiResourcesModels;
    }

    @Override
    public void addResources(ApiResourcesModel resource) {
        if (isExist(resource)) {
            logger.warn("Api编码={}已存在", resource.getCode());
            return;
        }
        apiResourcesModels.add(resource);
    }

    @Override
    public boolean isExist(ApiResourcesModel resource) {
        return isExist(resource.getCode());
    }

    @Override
    public boolean isExist(String code) {
        return apiResourcesModels.stream().anyMatch(e -> Objects.equals(e.getCode(), code));
    }

    @Override
    public void convert(List<ApiResources> annotatedResources) {
        if (annotatedResources.isEmpty()) {
            return;
        }
        for (ApiResources annotatedResource : annotatedResources) {
            ApiResourcesModel model = new ApiResourcesModel();
            model.setCode(annotatedResource.code());
            model.setName(annotatedResource.name());
            model.setDesc(annotatedResource.desc());
            model.setPath(annotatedResource.path());
            model.setVersion(annotatedResource.version());
            model.setEnabled(annotatedResource.isEnabled());
            model.setDeprecated(annotatedResource.isDeprecated());
            model.setWhiteList(annotatedResource.isWhiteList());
            model.setRequestMethod(annotatedResource.requestMethod().name());
            if (annotatedResource.belongTo().length > 0) {
                moduleResourcesHandler.convert(Arrays.asList(annotatedResource.belongTo()));
                moduleResourcesHandler.addResources(Arrays.asList(annotatedResource.belongTo()), model);
            }
            this.addResources(model);
        }
    }

    @Override
    public List<ApiResources> scanAnnotatedResources(Class<ApiResources> annotationClass) {
        List<ApiResources> resources = new ArrayList<>();

        // 扫描方法上的注解
        Map<String, Object> beans = getApplicationContext().getBeansWithAnnotation(Controller.class);
        for (Object bean : beans.values()) {
            Class<?> beanClass = bean.getClass();
            // 如果是 CGLIB 代理类，获取原始类
            if (beanClass.getName().contains("$$")) {
                beanClass = beanClass.getSuperclass();
            }

            // 扫描类中的所有方法
            Method[] methods = beanClass.getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(annotationClass)) {
                    resources.add(method.getAnnotation(annotationClass));
                }
            }
        }

        return resources;
    }

    @Override
    public void registryResources() {

    }

    @Override
    public boolean checkResources(ApiResourcesModel resource) {
        return false;
    }

    @Override
    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public ModuleResourcesHandler getModuleResourcesHandler() {
        return moduleResourcesHandler;
    }

    public void setModuleResourcesHandler(ModuleResourcesHandler moduleResourcesHandler) {
        this.moduleResourcesHandler = moduleResourcesHandler;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
