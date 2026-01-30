package com.yaocode.sts.common.resources.services.remote;

import com.yaocode.sts.common.resources.model.ResourcesModel;

import java.util.List;

/**
 * 资源远程服务类
 * @author: Jin-LiangBo
 * @date: 2026年01月28日 10:24
 */
public interface ResourcesServiceClient {

    /**
     * 批量保存资源数据
     * @param resourcesModelList 资源数据列表
     * @return boolean true：成功；false：失败
     */
    boolean batchSaveResources(List<ResourcesModel> resourcesModelList);

}
