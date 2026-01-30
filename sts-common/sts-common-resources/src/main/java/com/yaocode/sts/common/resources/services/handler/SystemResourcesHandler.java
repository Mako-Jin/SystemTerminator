package com.yaocode.sts.common.resources.services.handler;

import com.yaocode.sts.common.resources.annotation.SystemResources;
import com.yaocode.sts.common.resources.model.SystemResourcesModel;

import java.lang.annotation.Annotation;

/**
 * 系统资源处理器接口
 * @author: Jin-LiangBo
 * @date: 2025年11月15日 22:34
 */
public interface SystemResourcesHandler extends ResourcesHandler<SystemResources, SystemResourcesModel> {

    /**
     * 获取支持扫描的注解类型
     * @return java.lang.Class<SystemResources>
     */
    @Override
    default Class<? extends Annotation> getSupportedAnnotation() {
        return SystemResources.class;
    }

}
