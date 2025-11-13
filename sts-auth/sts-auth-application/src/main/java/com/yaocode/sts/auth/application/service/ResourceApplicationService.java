package com.yaocode.sts.auth.application.service;

import com.yaocode.sts.auth.application.dto.ResourceDto;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年11月13日 22:54
 */
public interface ResourceApplicationService {

    /**
     * 添加单个资源
     * @param resourceDto dto
     * @return ResourceId
     */
    String singleAdd(ResourceDto resourceDto);

}
