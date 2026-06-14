package com.yaocode.sts.auth.infrastructure.persistence;

import com.yaocode.sts.auth.domain.entity.RefreshTokenEntity;
import com.yaocode.sts.auth.domain.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {
    @Override
    public void save(RefreshTokenEntity refreshToken) {

    }
}
