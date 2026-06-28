package com.yaocode.sts.auth.application.dto.request;

import lombok.Data;

/**
 * 认证数据参数
 * @author: Jin-LiangBo
 * @date: 2026年04月14日 10:38
 */
@Data
public class AuthenticationRequestDto {

    /**
     * 状态令牌（CSRF防护）
     */
    private String stateToken;

    /**
     * 会话ID
     */
    private String sessionId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 验证码
     */
    private String captcha;

    /**
     * 验证码Key
     */
    private String captchaKey;

    /**
     * 记住我
     */
    private Boolean rememberMe;

    /**
     * 客户端ID
     */
    private String clientId;

    /**
     * 设备ID
     */
    private String deviceId;

    /**
     * IP地址
     */
    private String ipAddress;

}
