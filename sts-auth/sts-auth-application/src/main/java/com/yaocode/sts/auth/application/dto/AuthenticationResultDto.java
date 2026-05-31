package com.yaocode.sts.auth.application.dto;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 认证结果参数
 * @author: Jin-LiangBo
 * @date: 2026年04月14日 10:45
 */
public class AuthenticationResultDto {

    /**
     * 访问令牌
     */
    private String accessToken;

    /**
     * 刷新令牌
     */
    private String refreshToken;

    /**
     * 令牌类型（Bearer）
     */
    private String tokenType;

    /**
     * 过期时间（秒）
     */
    private Long expiresIn;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 用户角色列表
     */
    private java.util.List<String> roles;

    /**
     * 用户权限列表
     */
    private java.util.List<String> permissions;

    /**
     * 扩展信息
     */
    private Map<String, Object> extraInfo;

    /**
     * 认证时间
     */
    private LocalDateTime authenticationTime;

    /**
     * 认证方式
     */
    private String authenticationMethod;

}
