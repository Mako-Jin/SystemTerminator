package com.yaocode.sts.auth.domain.service;

import com.yaocode.sts.auth.domain.valueobjects.identifiers.OrganizationId;

import java.util.List;

/**
 * 组织机构领域服务
 * @author: Jin-LiangBo
 * @date: 2025年10月14日 20:11
 */
public interface OrganizationDomainService {

    /**
     * 验证组织结构id有效性
     * @param organizationId 组织结构id列表
     * @return boolean
     */
    boolean validateOrganizationId(OrganizationId organizationId);

    /**
     * 验证组织结构id有效性
     * @param organizationIdList 组织结构id列表
     * @return boolean
     */
    boolean validateOrganizationId(List<OrganizationId> organizationIdList);

}
