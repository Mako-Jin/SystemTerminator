package com.yaocode.sts.auth.domain.repository;

import com.yaocode.sts.auth.domain.entity.RefreshTokenEntity;

public interface RefreshTokenRepository {

    /**
     * 保存令牌
     */
    void save(RefreshTokenEntity refreshToken);

}
