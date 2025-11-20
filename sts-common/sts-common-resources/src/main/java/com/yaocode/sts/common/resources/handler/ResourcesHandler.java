package com.yaocode.sts.common.resources.handler;

import com.yaocode.sts.common.resources.model.ResourcesModel;
import org.springframework.context.ApplicationContext;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 资源处理器
 * @author: Jin-LiangBo
 * @date: 2025年11月19日 20:29
 */
public interface ResourcesHandler<A extends Annotation, M extends ResourcesModel> {

    /**
     * 获取所有资源
     * @return java.util.List<M>
     */
    List<M> getResources();

    /**
     * 添加资源
     * @param resource 资源
     */
    void addResources(M resource);

    /**
     * 资源是否存在
     * @param resource 资源
     * @return boolean
     */
    boolean isExist(M resource);

    /**
     * 资源是否存在
     * @param code 资源编码
     * @return boolean
     */
    boolean isExist(String code);

    /**
     * 将注解转换为实体bean
     * @param annotatedResources 注解对象
     */
    void convert(List<A> annotatedResources);

    /**
     * 注册资源
     */
    void registryResources();

    /**
     * 检查父资源是否可用
     * @param resource 资源
     * @return boolean
     */
    boolean checkResources(M resource);

    /**
     * 获取支持的注解类型
     * @return 支持注解类型
     */
    Class<A> getSupportedAnnotation();

    /**
     * 获取上下文
     * @return org.springframework.context.ApplicationContext
     */
    ApplicationContext getApplicationContext();

    /**
     * 扫描对应资源注解
     * @return java.util.List<A>
     */
    default List<A> scanResources() {
        return scanAnnotatedResources(getSupportedAnnotation());
    }

    /**
     * 扫描类上指定注解的资源
     * @param annotationClass 注解类
     * @return 扫描到的注解结果
     */
    default List<A> scanAnnotatedResources(Class<A> annotationClass) {
        List<A> resources = new ArrayList<>();

        // 扫描类上的注解
        Map<String, Object> beans = getApplicationContext().getBeansWithAnnotation(annotationClass);
        for (Object bean : beans.values()) {
            A annotation = bean.getClass().getAnnotation(annotationClass);
            resources.add(annotation);
        }

        return resources;
    }

}
