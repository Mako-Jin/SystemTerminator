package com.yaocode.sts.common.resources.handler;

import com.yaocode.sts.common.resources.annotation.ServiceResources;
import com.yaocode.sts.common.resources.model.ModuleResourcesModel;
import com.yaocode.sts.common.resources.model.ServiceResourcesModel;

import java.util.List;

/**
 * 微服务资源处理器接口
 * @author: Jin-LiangBo
 * @date: 2025年11月15日 22:35
 */
public interface ServiceResourcesHandler extends ResourcesHandler<ServiceResources, ServiceResourcesModel> {

    /**
     * 获取支持扫描的注解类型
     * @return java.lang.Class<ServiceResources>
     */
    @Override
    default Class<ServiceResources> getSupportedAnnotation() {
        return ServiceResources.class;
    }

    /**
     * 服务资源下绑定模块资源
     * @param serviceResources 服务资源资源注解
     * @param moduleResourcesModel 模块资源数据
     */
    void addResources(List<ServiceResources> serviceResources, ModuleResourcesModel moduleResourcesModel);

}
