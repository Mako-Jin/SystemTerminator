package com.yaocode.sts.auth.infrastructure.persistence;

import com.yaocode.sts.auth.domain.entity.UserProfileEntity;
import com.yaocode.sts.auth.domain.repository.UserProfileRepository;
import com.yaocode.sts.common.domain.valueobject.UserId;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserProfileRepositoryImpl implements UserProfileRepository {
    @Override
    public Optional<UserProfileEntity> findByUserId(UserId userId) {
        return Optional.empty();
    }
}
