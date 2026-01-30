package com.yaocode.sts.common.resources.services.remote.impl;

import com.yaocode.sts.common.resources.model.ResourcesModel;
import com.yaocode.sts.common.resources.services.remote.ResourcesServiceClient;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * 资源远程调用
 * @author: Jin-LiangBo
 * @date: 2026年01月28日 10:25
 */
public class ResourcesServiceClientImpl implements ResourcesServiceClient {

    private final RestTemplate restTemplate;

    public ResourcesServiceClientImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean batchSaveResources(List<ResourcesModel> resourcesModelList) {
        return false;
    }
}
