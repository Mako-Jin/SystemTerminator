package com.yaocode.sts.auth.domain.entity;

import com.yaocode.sts.common.domain.valueobject.TenantId;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Getter
@Builder
public class LoginSuccessEntity {

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
     * 状态令牌
     */
    private String stateToken;

    /**
     * 用户全局信息
     */
    private UserInfoEntity userInfoEntity;

    private final List<TenantInfoEntity> tenantList;

    private final List<RelTenantUserEntity> userTenantList;

    private final List<RelTenantOrganizationEntity> organizationList;

    private final List<RelTenantUserGroupEntity> userGroupList;

    private final List<RelTenantRoleEntity> roleList;

    private final List<TenantSecurityPolicyEntity> securityPolicieList;

    private final List<TenantBrandConfigEntity> brandConfigList;

    private final List<TenantLoginConfigEntity> loginConfigList;

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

    /**
     * 获取用户的所有租户ID列表
     */
    public List<TenantId> getTenantIdList() {
        return tenantList.stream()
                .map(TenantInfoEntity::getId)
                .toList();
    }

    /**
     * 是否只有一个租户
     */
    public boolean isSingleTenant() {
        return tenantList.size() == 1;
    }

    /**
     * 是否需要选择租户
     */
    public boolean isTenantSelectionRequired() {
        return tenantList.size() > 1;
    }

    /**
     * 是否认证成功
     */
    public boolean isAuthenticated() {
        return isAuthenticated != null && isAuthenticated;
    }

}
