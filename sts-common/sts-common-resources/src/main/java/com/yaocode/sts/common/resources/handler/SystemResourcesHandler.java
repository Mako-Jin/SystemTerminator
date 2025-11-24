package com.yaocode.sts.common.resources.handler;

import com.yaocode.sts.common.resources.annotation.SystemResources;
import com.yaocode.sts.common.resources.model.ServerResourcesModel;
import com.yaocode.sts.common.resources.model.SystemResourcesModel;

import java.util.List;

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
    default Class<SystemResources> getSupportedAnnotation() {
        return SystemResources.class;
    }

    /**
     * 系统资源下绑定服务资源
     * @param systemResources 系统资源注解
     * @param serverResourcesModel 服务资源数据
     */
    void addResources(List<SystemResources> systemResources, ServerResourcesModel serverResourcesModel);

}
