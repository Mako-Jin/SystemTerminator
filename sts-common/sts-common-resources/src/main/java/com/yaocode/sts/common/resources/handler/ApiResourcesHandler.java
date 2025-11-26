package com.yaocode.sts.common.resources.handler;

import com.yaocode.sts.common.resources.annotation.ApiResources;
import com.yaocode.sts.common.resources.model.ApiResourcesModel;

/**
 *
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

}
