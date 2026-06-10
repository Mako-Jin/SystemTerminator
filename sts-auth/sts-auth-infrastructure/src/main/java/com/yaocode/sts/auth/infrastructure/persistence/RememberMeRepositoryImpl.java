package com.yaocode.sts.auth.infrastructure.persistence;

import com.yaocode.sts.auth.domain.entity.RememberMeTokenEntity;
import com.yaocode.sts.auth.domain.repository.RememberMeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RememberMeRepositoryImpl implements RememberMeRepository {

    @Override
    public void save(RememberMeTokenEntity token) {

    }

    @Override
    public Optional<RememberMeTokenEntity> findRememberMeToken(String userId, String clientId, String deviceId) {
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
