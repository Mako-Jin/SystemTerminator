package com.yaocode.sts.auth.domain.repository;

import com.yaocode.sts.auth.domain.entity.OrganizationInfoEntity;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.OrganizationId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.TenantId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.OrganizationCode;
import com.yaocode.sts.common.domain.Repository;

import java.util.Optional;

/**
 * 组织结构仓库接口
 * @author: Jin-LiangBo
 * @date: 2025年10月13日 22:55
 */
public interface OrganizationRepository extends Repository<OrganizationInfoEntity, OrganizationId> {

    /**
     * 保存组织和用户关联关系
     * @param tenantId 租户id
     * @param organizationId 组织id
     * @param userId 用户id
     */
    void saveRelOrganizationUser(TenantId tenantId, OrganizationId organizationId, UserId userId);

    /**
     * 根据id查询数据
     * @param tenantId 租户id
     * @param id 主键id
     * @return java.util.Optional<OrganizationInfoEntity>
     */
    Optional<OrganizationInfoEntity> findById(TenantId tenantId, OrganizationId id);

    /**
     * 根据id查询数据
     * @param tenantId 租户id
     * @param organizationCode 组织code
     * @return java.util.Optional<OrganizationInfoEntity>
     */
    Optional<OrganizationInfoEntity> findByOrganizationCode(TenantId tenantId, OrganizationCode organizationCode);

    /**
     * 根据id查询数据
     * @param tenantId 租户id
     * @param organizationName 组织名
     * @return java.util.Optional<OrganizationInfoEntity>
     */
    Optional<OrganizationInfoEntity> findByOrganizationName(TenantId tenantId, String organizationName);

}
