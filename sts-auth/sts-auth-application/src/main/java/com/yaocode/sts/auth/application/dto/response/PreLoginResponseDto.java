package com.yaocode.sts.auth.application.dto.response;

import com.yaocode.sts.auth.application.dto.*;
import lombok.Data;

import java.time.Instant;
import java.util.List;

/**
 * 登录成功响应 DTO
 * 一次性返回用户在所有租户下的完整信息
 *
 * @author yaocode
 * @since 0.0.1
 */
@Data
public class PreLoginResponseDto {

    /**
     * 是否认证成功
     */
    private Boolean isAuthenticated = false;

    /**
     * 访问令牌
     */
    private String accessToken;

    /**
     * 令牌类型
     */
    private String tokenType = "Bearer";

    /**
     * Access Token 有效期（秒）
     */
    private Long expiresIn;

    /**
     * 刷新令牌
     */
    private String refreshToken;

    /**
     * Refresh Token 有效期（秒）
     */
    private Long refreshExpiresIn;

    /**
     * 记住我令牌
     */
    private String rememberMeToken;

    /**
     * 记住我令牌有效期（秒）
     */
    Long rememberMeTokenExpiresIn;

    /**
     * 状态令牌
     */
    private String stateToken;

    /**
     * 状态令牌有效期（秒）
     */
    Long stateTokenExpiresIn;

    // ==================== 用户基本信息（全局，与租户无关） ====================

    /**
     * 用户全局信息
     */
    private UserInfoDto userInfoDto;

    // ==================== 租户相关信息（按租户隔离） ====================

    /**
     * 用户可访问的租户列表
     */
    private List<TenantInfoDto> availableTenants;

    /**
     * 租户下的组织列表（按租户隔离）
     */
    private List<TenantOrganizationDto> organizations;

    /**
     * 租户下的用户组列表（按租户隔离）
     */
    private List<TenantUserGroupDto> userGroups;

    /**
     * 租户下的角色列表（按租户隔离）
     */
    private List<TenantRoleDto> roles;

    /**
     * 租户安全策略配置
     */
    private List<TenantSecurityPolicyDto> securityPolicies;

    /**
     * 租户品牌配置
     */
    private List<TenantBrandConfigDto> brandConfigs;

    /**
     * 租户登录方式配置
     */
    private List<TenantLoginConfigDto> loginConfigs;

    // ==================== 会话信息 ====================

    /**
     * 会话 ID
     */
    private String sessionId;

    /**
     * 设备 ID
     */
    private String deviceId;

    /**
     * 客户端 ID
     */
    private String clientId;

    /**
     * 登录时间
     */
    private Instant loginTime;

    /**
     * 登录 IP
     */
    private String loginIp;

    /**
     * 用户代理
     */
    private String userAgent;

    // ==================== MFA 信息 ====================

    /**
     * 是否已绑定 MFA
     */
    private Boolean mfaBound = false;

    /**
     * 是否需要 MFA 验证
     */
    private Boolean mfaRequired = false;

    /**
     * MFA 类型（SMS/TOTP/EMAIL）
     */
    private String mfaType;

    /**
     * MFA 会话 ID
     */
    private String mfaSessionId;

    // ==================== 安全提示 ====================

    /**
     * 是否需要修改密码
     */
    private Boolean passwordChangeRequired = false;

    /**
     * 密码过期时间
     */
    private Instant passwordExpireTime;

    /**
     * 上次登录时间
     */
    private Instant lastLoginTime;

    /**
     * 上次登录 IP
     */
    private String lastLoginIp;

    /**
     * 安全提示信息
     */
    private List<String> securityTips;

    /**
     * 登录欢迎语
     */
    private String welcomeMessage;

    // ==================== 内部类 ====================

    /**
     * 租户信息 DTO
     */
    @Data
    public static class TenantInfoDto {
        /**
         * 租户 ID
         */
        private String tenantId;

        /**
         * 租户名称
         */
        private String tenantName;

        /**
         * 租户编码
         */
        private String tenantCode;

        /**
         * 租户 Logo
         */
        private String logoUrl;

        /**
         * 租户描述
         */
        private String description;

        /**
         * 租户域名
         */
        private String domain;

        /**
         * 是否为默认租户
         */
        private Boolean isDefault = false;

        /**
         * 用户在该租户下的状态：ACTIVE, INACTIVE, LOCKED
         */
        private String userStatus;

        /**
         * 用户在该租户下的昵称
         */
        private String nickname;

        /**
         * 用户在该租户下的头像
         */
        private String avatar;

        /**
         * 加入该租户的时间
         */
        private Instant joinedAt;
    }

    /**
     * 租户安全策略 DTO
     */
    @Data
    public static class TenantSecurityPolicyDto {
        /**
         * 租户 ID
         */
        private String tenantId;

        /**
         * 是否启用 MFA
         */
        private Boolean mfaEnabled = false;

        /**
         * MFA 类型：SMS/TOTP/EMAIL
         */
        private String mfaType;

        /**
         * 会话超时时间（秒）
         */
        private Integer sessionTimeout;

        /**
         * 密码是否启用过期策略
         */
        private Boolean passwordExpiryEnabled = false;

        /**
         * 密码过期天数
         */
        private Integer passwordExpiryDays;

        /**
         * 是否启用单点登录
         */
        private Boolean ssoEnabled = false;

        /**
         * 是否只允许单设备登录
         */
        private Boolean singleSessionEnabled = false;

        /**
         * 密码错误最大尝试次数
         */
        private Integer maxLoginAttempts;

        /**
         * 登录锁定时间（秒）
         */
        private Integer lockoutDuration;

        /**
         * 是否启用 IP 白名单
         */
        private Boolean ipWhitelistEnabled = false;

        /**
         * IP 白名单列表
         */
        private List<String> ipWhitelist;
    }

    /**
     * 租户品牌配置 DTO
     */
    @Data
    public static class TenantBrandConfigDto {
        /**
         * 租户 ID
         */
        private String tenantId;

        /**
         * 机构/公司名称
         */
        private String institution;

        /**
         * 登录页标题
         */
        private String loginTitle;

        /**
         * 系统名称
         */
        private String systemName;

        /**
         * Logo URL
         */
        private String logoUrl;

        /**
         * 登录页背景图 URL
         */
        private String loginBackgroundUrl;

        /**
         * 版权信息
         */
        private String copyright;

        /**
         * 主题色（十六进制）
         */
        private String primaryColor;

        /**
         * 是否启用自定义 CSS
         */
        private Boolean customCssEnabled = false;

        /**
         * 自定义 CSS 内容
         */
        private String customCss;

        /**
         * 登录页提示信息
         */
        private String loginNotice;
    }

    /**
     * 租户登录方式配置 DTO
     */
    @Data
    public static class TenantLoginConfigDto {
        /**
         * 租户 ID
         */
        private String tenantId;

        /**
         * 是否支持账号密码登录
         */
        private Boolean passwordLoginEnabled = true;

        /**
         * 是否支持短信验证码登录
         */
        private Boolean smsLoginEnabled = false;

        /**
         * 是否支持邮箱验证码登录
         */
        private Boolean emailLoginEnabled = false;

        /**
         * 是否支持扫码登录
         */
        private Boolean qrCodeLoginEnabled = false;

        /**
         * 是否支持第三方登录
         */
        private List<SocialProviderDto> socialProviders;

        /**
         * 是否启用验证码
         */
        private Boolean captchaEnabled = false;

        /**
         * 验证码类型：MATH（算术）、TEXT（字符）、SLIDER（滑块）
         */
        private String captchaType;

        /**
         * 验证码触发条件：ALWAYS（始终）、FAILED（失败后）、SENSITIVE（敏感操作）
         */
        private String captchaTrigger;

        /**
         * 是否启用自助注册
         */
        private Boolean selfRegisterEnabled = false;

        /**
         * 是否启用忘记密码
         */
        private Boolean forgotPasswordEnabled = true;
    }

    /**
     * 第三方登录提供商 DTO
     */
    @Data
    public static class SocialProviderDto {
        /**
         * 提供商名称：WECHAT, QQ, GITHUB, GOOGLE, DINGTALK
         */
        private String provider;

        /**
         * 显示名称
         */
        private String displayName;

        /**
         * 图标 URL
         */
        private String iconUrl;

        /**
         * 是否启用
         */
        private Boolean enabled = true;
    }
}