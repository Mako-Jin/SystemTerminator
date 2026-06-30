package com.yaocode.sts.auth.infrastructure.persistence;

import com.yaocode.sts.auth.domain.entity.LoginAttemptEntity;
import com.yaocode.sts.auth.domain.repository.LoginAttemptRepository;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.LoginAttemptId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.IpAddress;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import com.yaocode.sts.common.domain.valueobject.UserId;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class LoginAttemptRepositoryImpl implements LoginAttemptRepository {
    @Override
    public LoginAttemptEntity findByUserIdAndTenantId(UserId userId, TenantId tenantId) {
        return null;
    }

    @Override
    public void recordFailedAttempt(UserId userId, TenantId tenantId, IpAddress ipAddress, String failReason) {

    }

    @Override
    public void clearFailedAttempts(UserId userId, TenantId tenantId) {

    }

    @Override
    public void resetAttempts(UserId userId, TenantId tenantId) {

    }

    @Override
    public Optional<LoginAttemptEntity> findById(LoginAttemptId loginAttemptId) {
        return Optional.empty();
    }

    @Override
    public LoginAttemptId save(LoginAttemptEntity aggregate) {
        return null;
    }

    @Override
    public void delete(LoginAttemptEntity aggregate) {

    }
}
