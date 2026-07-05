package com.yaocode.sts.auth.domain.valueobjects.composites;


import com.yaocode.sts.common.domain.valueobject.UserId;
import lombok.Builder;
import lombok.Value;

import java.time.Instant;

/**
 * 认证令牌值对象
 * 封装认证请求的所有参数
 *
 * @author: Jin-LiangBo
 * @date: 2026-06-08
 */
@Value
@Builder
public class AuthenticationToken {

    UserId userId;

    // ========== 认证类型 ==========
    String grantType;

    String accessToken;

    /**
     * Access Token 有效期（秒）
     */
    Instant accessTokenExpiresAt;

    String refreshToken;

    /**
     * Refresh Token 有效期（秒）
     */
    Instant refreshTokenExpiresAt;

    /**
     * 记住我令牌
     */
    String rememberMeToken;

    /**
     * 记住我令牌有效期（秒）
     */
    Instant rememberMeTokenExpiresAt;

    /**
     * 状态令牌
     */
    String stateToken;

    /**
     * 状态令牌有效期（秒）
     */
    Instant stateTokenExpiresAt;

    /**
     * 是否认证成功
     */
    Boolean isAuthenticated;

}
