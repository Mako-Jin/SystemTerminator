package com.yaocode.sts.common.resources.services;

import com.yaocode.sts.common.resources.model.ResourcesModel;

import java.util.List;

/**
 * 资源处理服务
 * @author: Jin-LiangBo
 * @date: 2025年12月03日 20:46
 */
public interface ResourcesService {

    /**
     * 批量保存资源数据
     * @param resourcesModelList 资源数据列表
     * @return boolean true：成功；false：失败
     */
    boolean batchSaveResources(List<ResourcesModel> resourcesModelList);

}
