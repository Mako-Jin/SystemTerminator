package com.yaocode.sts.auth.infrastructure.persistence;

import com.yaocode.sts.auth.domain.entity.TenantConfigEntity;
import com.yaocode.sts.auth.domain.repository.TenantConfigRepository;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.TenantConfigId;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TenantConfigRepositoryImpl implements TenantConfigRepository {
    @Override
    public List<TenantConfigEntity> findByTenantIds(List<TenantId> tenantIds) {
        return List.of();
    }

    @Override
    public Optional<TenantConfigEntity> findByTenantId(TenantId tenantId) {
        return Optional.empty();
    }

    @Override
    public Optional<TenantConfigEntity> findById(TenantConfigId tenantConfigId) {
        return Optional.empty();
    }

    @Override
    public TenantConfigId save(TenantConfigEntity aggregate) {
        return null;
    }

    @Override
    public void delete(TenantConfigEntity aggregate) {

    }
}
