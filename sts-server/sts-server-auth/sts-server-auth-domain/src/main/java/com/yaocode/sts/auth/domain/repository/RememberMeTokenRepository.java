package com.yaocode.sts.auth.domain.repository;

import com.yaocode.sts.auth.domain.entity.RememberMeTokenEntity;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ClientId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.DeviceId;
import com.yaocode.sts.common.domain.valueobject.UserId;

import java.util.Optional;

/**
 * 记住我令牌仓储接口
 * @author: Jin-LiangBo
 * @date: 2026-06-07
 */
public interface RememberMeTokenRepository {

    /**
     * 保存令牌
     */
    void save(RememberMeTokenEntity token);

    /**
     * 查找令牌
     * @param userId 用户ID
     * @param clientId 客户端ID
     * @param deviceId 设备ID
     */
    Optional<RememberMeTokenEntity> findRememberMeToken(ClientId clientId, DeviceId deviceId, UserId userId);

    /**
     * 严格匹配查找（所有维度必须完全匹配）
     * ⚠️ 不支持模糊匹配，防止越权
     */
    Optional<RememberMeTokenEntity> findRememberMeToken(
            String userId,
            String username,
            String clientId,
            String deviceId
    );

    /**
     * 删除指定令牌
     */
    void deleteRememberMeToken(String userId, String username, String clientId);

    /**
     * 更新令牌
     */
    void update(RememberMeTokenEntity token);
}
