package com.yaocode.sts.auth.application.service;

import com.yaocode.sts.auth.application.dto.ResourceDto;
import com.yaocode.sts.common.resources.services.ResourcesService;

import java.util.List;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年11月13日 22:54
 */
public interface ResourceApplicationService extends ResourcesService {

    /**
     * 添加单个资源
     * @param resourceDto dto
     * @return ResourceId
     */
    String singleAdd(ResourceDto resourceDto);

    /**
     * 批量添加资源列表
     * @param resourceDtoList dtoList
     * @return ResourceId
     */
    List<String> batchAdd(List<ResourceDto> resourceDtoList);

}
