package com.yaocode.sts.auth.interfaces.model.vo;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class LoginSuccessVo {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 登录时间
     */
    private Instant loginTime;

    private String grantType;

    /**
     * 访问令牌
     */
    private String accessToken;
    /**
     * Access Token 有效期（秒）
     */
    private Instant accessTokenExpiresAt;
    /**
     * 刷新令牌
     */
    private String refreshToken;

    /**
     * Refresh Token 有效期（秒）
     */
    private Instant refreshTokenExpiresAt;

    /**
     * 记住我令牌
     */
    private String rememberMeToken;

    /**
     * 记住我令牌有效期（秒）
     */
    private Instant rememberMeTokenExpiresAt;

}
