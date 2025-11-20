package com.yaocode.sts.common.resources.handler;

import com.yaocode.sts.common.resources.annotation.ServerResources;
import com.yaocode.sts.common.resources.model.ServerResourcesModel;
import com.yaocode.sts.common.resources.model.ServiceResourcesModel;

import java.util.List;

/**
 * 服务资源处理器接口
 * @author: Jin-LiangBo
 * @date: 2025年11月15日 22:35
 */
public interface ServerResourcesHandler extends ResourcesHandler<ServerResources, ServerResourcesModel> {

    /**
     * 获取支持扫描的注解类型
     * @return java.lang.Class<ServerResources>
     */
    @Override
    default Class<ServerResources> getSupportedAnnotation() {
        return ServerResources.class;
    }

    /**
     * 大服务资源下绑定小服务资源
     * @param serverResources 大服务资源资源注解
     * @param serviceResourcesModel 小服务资源数据
     */
    void addServiceResource(List<ServerResources> serverResources, ServiceResourcesModel serviceResourcesModel);

}
