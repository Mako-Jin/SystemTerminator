package com.yaocode.sts.common.resources.services.handler;

import com.yaocode.sts.common.resources.annotation.ServerResources;
import com.yaocode.sts.common.resources.model.ServerResourcesModel;

import java.lang.annotation.Annotation;

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
    default Class<? extends Annotation> getSupportedAnnotation() {
        return ServerResources.class;
    }

}
