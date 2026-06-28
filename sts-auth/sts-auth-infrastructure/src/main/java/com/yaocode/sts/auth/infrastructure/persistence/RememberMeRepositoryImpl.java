package com.yaocode.sts.auth.infrastructure.persistence;

import com.yaocode.sts.auth.domain.entity.RememberMeTokenEntity;
import com.yaocode.sts.auth.domain.repository.RememberMeRepository;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ClientId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.DeviceId;
import com.yaocode.sts.common.domain.valueobject.UserId;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RememberMeRepositoryImpl implements RememberMeRepository {

    @Override
    public void save(RememberMeTokenEntity token) {

    }

    @Override
    public Optional<RememberMeTokenEntity> findRememberMeToken(ClientId clientId, DeviceId deviceId, UserId userId) {
        return Optional.empty();
    }

    @Override
    public Optional<RememberMeTokenEntity> findRememberMeToken(String userId, String username, String clientId, String deviceId) {
        return Optional.empty();
    }

    @Override
    public void deleteRememberMeToken(String userId, String username, String clientId) {

    }

    @Override
    public void update(RememberMeTokenEntity token) {

    }
}
