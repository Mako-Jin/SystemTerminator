package com.yaocode.sts.auth.domain.service;

import com.yaocode.sts.auth.domain.entity.UserInfoEntity;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ClientId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.DeviceId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.TokenId;

public interface JwtTokenService {

    /**
     * 生成访问令牌
     */
    String generateAccessToken(UserInfoEntity userInfoEntity, ClientId clientId, DeviceId deviceId);

    /**
     * 生成刷新令牌
     */
    String generateRefreshToken(UserInfoEntity userInfoEntity, ClientId clientId, DeviceId deviceId, TokenId refreshTokenId);

    /**
     * 生成记住我令牌
     */
    String generateRememberMeToken(UserInfoEntity userInfoEntity, ClientId clientId, DeviceId deviceId, String series);

    /**
     * 生成状态令牌
     */
    String generateStateToken(String state, String redirectUri, ClientId clientId, DeviceId deviceId);

}
