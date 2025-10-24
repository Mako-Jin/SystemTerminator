package com.yaocode.sts.auth.domain.service;

import com.yaocode.sts.auth.domain.valueobjects.identifiers.OrganizationId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.TenantId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserId;

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

    /**
     * 组织分配用户
     * @param tenantId 租户id
     * @param organizationId 组织id
     * @param userId 用户id
     */
    void associatedOrganizationUser(TenantId tenantId, OrganizationId organizationId, UserId userId);

}
