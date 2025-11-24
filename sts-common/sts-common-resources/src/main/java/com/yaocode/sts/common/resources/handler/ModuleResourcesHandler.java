package com.yaocode.sts.common.resources.handler;

import com.yaocode.sts.common.resources.annotation.ModuleResources;
import com.yaocode.sts.common.resources.model.ModuleResourcesModel;

/**
 * 模块资源处理器
 * @author: Jin-LiangBo
 * @date: 2025年11月15日 22:35
 */
public interface ModuleResourcesHandler extends ResourcesHandler<ModuleResources, ModuleResourcesModel> {

    /**
     * 获取支持扫描的注解类型
     * @return java.lang.Class<ModuleResources>
     */
    @Override
    default Class<ModuleResources> getSupportedAnnotation() {
        return ModuleResources.class;
    }

}
