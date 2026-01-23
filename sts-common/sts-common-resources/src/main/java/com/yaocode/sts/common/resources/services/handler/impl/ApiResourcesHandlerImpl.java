package com.yaocode.sts.common.resources.services.handler.impl;

import com.yaocode.sts.common.basic.enums.OppositeEnums;
import com.yaocode.sts.common.resources.annotation.ApiResources;
import com.yaocode.sts.common.resources.model.ApiResourcesModel;
import com.yaocode.sts.common.resources.model.ModuleResourcesModel;
import com.yaocode.sts.common.resources.model.ResourcesModel;
import com.yaocode.sts.common.resources.services.handler.ApiResourcesHandler;
import com.yaocode.sts.common.resources.utils.PropertyResolverUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Api接口资源注解
 * @author: Jin-LiangBo
 * @date: 2025年11月26日 22:55
 */
public class ApiResourcesHandlerImpl extends AbstractResourcesHandler<ApiResources, ApiResourcesModel> implements ApiResourcesHandler {

    private static final Logger logger = LoggerFactory.getLogger(ApiResourcesHandlerImpl.class);

    private static final List<ApiResourcesModel> apiResourcesModelList = new ArrayList<>();

    public ApiResourcesHandlerImpl(
            ApplicationContext applicationContext,
            PropertyResolverUtils propertyResolverUtils
    ) {
        super(applicationContext, propertyResolverUtils);
    }

    @Override
    public List<ApiResourcesModel> build() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ApiResourcesModel> build(List<? extends ResourcesModel> parentResourcesModelList) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void buildResourcesModel(ApiResourcesModel resourcesModel, ApiResources resources) {
        super.buildResourcesModel(resourcesModel, resources);
    }

    @Override
    public void convert(ApiResourcesModel resourcesModel, ApiResources annotatedResource) {
        resourcesModel.setCode(annotatedResource.code());
        resourcesModel.setName(annotatedResource.name());
        resourcesModel.setDesc(annotatedResource.desc());
        resourcesModel.setPath(Arrays.asList(annotatedResource.path()));
        resourcesModel.setVersion(annotatedResource.version());
        resourcesModel.setIsWhiteList(OppositeEnums.getCode(annotatedResource.isWhiteList()));
        resourcesModel.setIsDeprecated(OppositeEnums.getCode(annotatedResource.isDeprecated()));
        resourcesModel.setIsEnabled(OppositeEnums.getCode(annotatedResource.isEnabled()));
        resourcesModel.setRequestMethod(Arrays.asList(annotatedResource.requestMethod()));
        resourcesModel.setParentCode(Arrays.asList(annotatedResource.parent()));
    }

    @Override
    public void build(Class<?> beanClazz, ModuleResourcesModel moduleResourcesModel) {
        Method[] methods = beanClazz.getDeclaredMethods();
        ApiResourcesModel apiResourcesModel;
        for (Method method : methods) {
            // 检查方法上是否有指定注解
            ApiResources apiResources = AnnotationUtils.findAnnotation(method, getSupportedAnnotation());
            if (Objects.isNull(apiResources)) {
                continue;
            }
            apiResourcesModel = new ApiResourcesModel();
            this.buildResourcesModel(apiResourcesModel, apiResources);
            List<String> parentCodeList = filterParentCode(
                    apiResourcesModel.getParentCode(),
                    Collections.singleton(moduleResourcesModel.getCode())
            );
            apiResourcesModel.setParentCode(parentCodeList);
            RequestMapping requestMapping = AnnotatedElementUtils.findMergedAnnotation(method, RequestMapping.class);
            checkAndFilterRequestMethod(requestMapping, apiResourcesModel);
            List<String> requestPath = checkAndBuildRequestPath(requestMapping, apiResourcesModel.getPath(), moduleResourcesModel);
            apiResourcesModel.setPath(requestPath);
            this.addResources(apiResourcesModel);
        }
    }

    @Override
    public List<ApiResourcesModel> getResources() {
        return apiResourcesModelList;
    }

    public void addResources(ApiResourcesModel resource) {
        if (isExist(resource)) {
            logger.warn("Api编码={}已存在", resource.getCode());
            return;
        }
        apiResourcesModelList.add(resource);
    }

    public boolean isExist(ApiResourcesModel resource) {
        return isExist(resource.getCode());
    }

    public boolean isExist(String code) {
        return apiResourcesModelList.stream().anyMatch(e -> Objects.equals(e.getCode(), code));
    }

    private void checkAndFilterRequestMethod(RequestMapping requestMapping, ApiResourcesModel apiResourcesModel) {
        List<RequestMethod> requestMethodList = Arrays.asList(requestMapping.method());
        if (requestMethodList.isEmpty()) {
            requestMethodList.add(RequestMethod.GET);
        }
        if (apiResourcesModel.getRequestMethod().isEmpty()) {
            apiResourcesModel.setRequestMethod(requestMethodList);
            return;
        }
        List<RequestMethod> finalRequestMethod = apiResourcesModel.getRequestMethod()
                .stream().filter(requestMethodList::contains).collect(Collectors.toList());
        if (finalRequestMethod.isEmpty()) {
            apiResourcesModel.setRequestMethod(requestMethodList);
        } else {
            apiResourcesModel.setRequestMethod(finalRequestMethod);
        }
    }

    private List<String> checkAndBuildRequestPath(RequestMapping requestMapping, List<String> pathList, ModuleResourcesModel moduleResourcesModel) {
        if (requestMapping == null || moduleResourcesModel == null || pathList == null) {
            return Collections.emptyList();
        }

        String[] requestMappingPaths = requestMapping.path();
        if (requestMappingPaths.length == 0) {
            requestMappingPaths = requestMapping.value();
        }

        List<String> finalPathListResult = new ArrayList<>();

        // 预先处理请求映射路径，转为 Set 提高查找效率
        Set<String> requestPathSet = new HashSet<>(Arrays.asList(requestMappingPaths));

        for (String parentPath : moduleResourcesModel.getPath()) {
            for (String apiPath : pathList) {
                String combinePath = combinePath(parentPath, apiPath);

                // 检查是否是直接匹配
                if (requestPathSet.contains(apiPath)) {
                    logger.warn("方法路径 {} 已自动修改成全路径 {}", apiPath, combinePath);
                    finalPathListResult.add(combinePath);
                    continue;
                }

                // 检查是否已经是完整路径
                if (requestPathSet.contains(combinePath)) {
                    finalPathListResult.add(combinePath);
                    continue;
                }
                logger.warn("路径配置错误，已自动忽略。API路径: {}, 组合路径: {}", apiPath, combinePath);
            }
        }
        return new ArrayList<>(new LinkedHashSet<>(finalPathListResult));
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
}
