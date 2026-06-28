package com.yaocode.sts.auth.domain.repository;

import com.yaocode.sts.auth.domain.entity.TenantConfigEntity;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.TenantConfigId;
import com.yaocode.sts.common.domain.Repository;
import com.yaocode.sts.common.domain.valueobject.TenantId;

import java.util.List;
import java.util.Optional;

public interface TenantConfigRepository extends Repository<TenantConfigEntity, TenantConfigId> {

    /**
     * 根据租户ID列表查询租户配置实体列表
     * @param tenantIds 租户ID列表
     * @return 租户配置实体列表
     */
    List<TenantConfigEntity> findByTenantIds(List<TenantId> tenantIds);

    /**
     * 根据租户ID查询租户配置实体
     * @param tenantId 租户ID
     * @return 租户配置实体
     */
    Optional<TenantConfigEntity> findByTenantId(TenantId tenantId);

}
