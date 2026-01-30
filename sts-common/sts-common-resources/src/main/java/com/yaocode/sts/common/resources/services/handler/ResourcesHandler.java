package com.yaocode.sts.common.resources.services.handler;

import com.yaocode.sts.common.resources.model.ResourcesModel;
import com.yaocode.sts.common.resources.utils.PropertyResolverUtils;
import org.springframework.context.ApplicationContext;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

/**
 * 资源处理器
 * @author: Jin-LiangBo
 * @date: 2025年11月19日 20:29
 */
public interface ResourcesHandler<A extends Annotation, M extends ResourcesModel> {

    /**
     * 构建ResourcesModelList
     * @return ResourcesModelList
     */
    List<M> build();

    /**
     * 构建ResourcesModelList
     * @param parentResourcesModelList 父资源集合
     * @return ResourcesModelList
     */
    List<M> build(List<? extends ResourcesModel> parentResourcesModelList);

    /**
     * 获取支持的注解类型
     * @return 支持注解类型
     */
    Class<? extends Annotation> getSupportedAnnotation();

    /**
     * 获取默认的资源名称
     * @return java.lang.String
     */
    String getDefaultResourceName();

    /**
     * 扫描对应资源注解
     * @return java.util.List<A>
     */
    Map<String, Object> scanResources();

    /**
     * 构建默认的资源对象
     * @param model 模型类
     */
    void buildDefaultResourcesModel(M model);

    /**
     * 获取默认资源编码
     * @return ResourcesModel
     */
    String getDefaultResourceCode();

    /**
     * 获取默认资源编码
     * @return ResourcesModel
     */
    String getDefaultResourceDesc();

    /**
     * 获取默认资源编码
     * @return ResourcesModel
     */
    String getDefaultResourceVersion();

    /**
     * 根据resource注解构建resourcesModel对象
     * @param resourcesModel 模型类
     * @param resources 注解
     */
    void buildResourcesModel(M resourcesModel, A resources);

    /**
     * 将注解转换为实体bean
     * @param resourcesModel 模型对象
     * @param annotatedResource 注解对象
     */
    void convert(M resourcesModel, A annotatedResource);

    /**
     * 获取上下文
     * @return org.springframework.context.ApplicationContext
     */
    ApplicationContext getApplicationContext();

    /**
     * 获取属性处理器
     * @return PropertyResolverUtils
     */
    PropertyResolverUtils getPropertyResolverUtils();

    /**
     * 获取所有资源
     * @return java.util.List<M>
     */
    List<M> getResources();

}
