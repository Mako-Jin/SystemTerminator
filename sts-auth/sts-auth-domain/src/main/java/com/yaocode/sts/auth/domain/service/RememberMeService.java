package com.yaocode.sts.auth.domain.service;

import com.yaocode.sts.auth.domain.entity.RememberMeTokenEntity;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ClientId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.DeviceId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.Username;
import com.yaocode.sts.common.domain.valueobject.UserId;

/**
 * 记住我领域服务接口
 * @author: Jin-LiangBo
 * @date: 2026-06-07
 */
public interface RememberMeService {

    /**
     * 创建记住我令牌
     */
    RememberMeTokenEntity create(UserId userId, Username username, ClientId clientId, DeviceId deviceId, int validityDays);

    /**
     * 验证并获取记住我令牌
     *
     * @param jwtValue JWT 字符串
     * @param clientId 客户端ID
     * @param deviceId 设备ID
     * @return 有效的令牌，无效返回 null
     */
    RememberMeTokenEntity validate(String jwtValue, String clientId, String deviceId);

    /**
     * 续期令牌
     */
    RememberMeTokenEntity renew(RememberMeTokenEntity token, int validityDays);

    /**
     * 撤销令牌
     */
    void revoke(String username);

}
