package com.yaocode.sts.auth.domain.port;

/**
 * JWT Token 配置端口
 * domain 层定义接口，infrastructure 层实现
 */
public interface JwtTokenConfigPort {

    /**
     * 获取 Access Token 有效期（秒）
     */
    long getAccessTokenTtl();

    /**
     * 获取 Refresh Token 有效期（秒）
     */
    long getRefreshTokenTtl();

    /**
     * 获取 Remember Me Token 有效期（秒）
     */
    long getRememberMeTtl();

    /**
     * 获取 State Token 有效期（秒）
     */
    long getStateTokenTtl();
}
