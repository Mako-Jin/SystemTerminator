package com.yaocode.sts.auth.infrastructure.persistence;

import com.yaocode.sts.auth.domain.entity.BrandConfigEntity;
import com.yaocode.sts.auth.domain.repository.BrandConfigRepository;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.BrandConfigId;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class BrandConfigRepositoryImpl implements BrandConfigRepository {
    @Override
    public Optional<BrandConfigEntity> findByTenantId(TenantId tenantId) {
        return Optional.empty();
    }

    @Override
    public Optional<BrandConfigEntity> findById(BrandConfigId brandConfigId) {
        return Optional.empty();
    }

    @Override
    public BrandConfigId save(BrandConfigEntity aggregate) {
        return null;
    }

    @Override
    public void delete(BrandConfigEntity aggregate) {

    }
}
