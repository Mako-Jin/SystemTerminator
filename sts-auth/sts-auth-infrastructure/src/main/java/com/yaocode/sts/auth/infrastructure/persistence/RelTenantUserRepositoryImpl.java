package com.yaocode.sts.auth.infrastructure.persistence;

import com.yaocode.sts.auth.domain.entity.relation.RelTenantUserEntity;
import com.yaocode.sts.auth.domain.repository.RelTenantUserRepository;
import com.yaocode.sts.common.domain.valueobject.UserId;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RelTenantUserRepositoryImpl implements RelTenantUserRepository {
    @Override
    public List<RelTenantUserEntity> findByUserId(UserId userId) {
        return List.of();
    }

    @Override
    public RelTenantUserEntity findByUserIdAndTenantId(String userId, String tenantId) {
        return null;
    }

    @Override
    public List<RelTenantUserEntity> findByIdentifier(String identifier) {
        return List.of();
    }

    @Override
    public RelTenantUserEntity findByUsernameAndTenantId(String username, String tenantId) {
        return null;
    }
}
