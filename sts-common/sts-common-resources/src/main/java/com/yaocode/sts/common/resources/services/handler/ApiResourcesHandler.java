package com.yaocode.sts.common.resources.services.handler;

import com.yaocode.sts.common.resources.annotation.ApiResources;
import com.yaocode.sts.common.resources.model.ApiResourcesModel;
import com.yaocode.sts.common.resources.model.ModuleResourcesModel;

import java.util.List;

/**
 * API资源处理接口
 * @author: Jin-LiangBo
 * @date: 2025年11月15日 22:36
 */
public interface ApiResourcesHandler extends ResourcesHandler<ApiResources, ApiResourcesModel> {

    /**
     * 获取支持扫描的注解类型
     * @return java.lang.Class<ModuleResources>
     */
    @Override
    default Class<ApiResources> getSupportedAnnotation() {
        return ApiResources.class;
    }

    /**
     * 构建接口对象
     * @param beanClazz bean的类定义
     * @param moduleResourcesModel 父资源--模块资源对象
     */
    void build(Class<?> beanClazz, ModuleResourcesModel moduleResourcesModel);

    /**
     * 获取当前接口资源集合
     * @return java.util.List<ApiResourcesModel>
     */
    List<ApiResourcesModel> getResources();

}
