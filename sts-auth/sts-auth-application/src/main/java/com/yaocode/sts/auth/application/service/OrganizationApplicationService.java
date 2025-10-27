package com.yaocode.sts.auth.application.service;

import com.yaocode.sts.auth.application.dto.OrganizationDto;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.OrganizationId;

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
    OrganizationId singleAdd(OrganizationDto organizationDto);

}
