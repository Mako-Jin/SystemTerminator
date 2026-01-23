package com.yaocode.sts.auth.application.service;

import com.yaocode.sts.auth.application.dto.OrganizationDto;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年10月25日 17:18
 */
public interface OrganizationApplicationService {

    /**
     * 添加单个组织
     * @param organizationDto dto
     * @return OrganizationId
     */
    String singleAdd(OrganizationDto organizationDto);

    /**
     * 根据id查询组织结构信息
     * @param organizationId 组织机构id
     * @return OrganizationDto
     */
    OrganizationDto getById(String organizationId);

}
