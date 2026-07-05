package com.yaocode.sts.auth.domain.repository;

import com.yaocode.sts.auth.domain.entity.BrandConfigEntity;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.BrandConfigId;
import com.yaocode.sts.common.domain.Repository;
import com.yaocode.sts.common.domain.valueobject.TenantId;

import java.util.Optional;

public interface BrandConfigRepository extends Repository<BrandConfigEntity, BrandConfigId> {

    /**
     * 根据租户ID查询品牌配置
     */
    Optional<BrandConfigEntity> findByTenantId(TenantId tenantId);

}
