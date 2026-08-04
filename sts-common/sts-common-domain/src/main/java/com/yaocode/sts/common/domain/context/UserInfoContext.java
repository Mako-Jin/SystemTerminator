package com.yaocode.sts.common.domain.context;

import com.yaocode.sts.common.domain.constants.ContextConstants;
import com.yaocode.sts.common.domain.valueobject.TenantCode;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import com.yaocode.sts.common.domain.valueobject.UserId;
import com.yaocode.sts.common.domain.valueobject.Username;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 用户信息上下文
 * 包含：用户ID、用户名、权限、角色等
 * 只在用户登录后填充
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserInfoContext extends BaseAbstractContext<UserInfoContext> {

    // ========== 用户基础信息 ==========
    private UserId userId;
    private Username username;
    private String nickname;
    private String realName;
    private String avatar;
    private String email;
    private String phone;

    // ========== 租户信息 ==========
    private TenantId tenantId;
    private String tenantName;
    private TenantCode tenantCode;

    // ========== 权限信息 ==========
    private Set<String> roles;           // 角色编码
    private Set<String> permissions;     // 权限编码
    private List<String> roleIds;        // 角色ID列表

    // ========== 组织信息 ==========
    private String organizationId;
    private String organizationName;
    private String organizationCode;
    private String departmentId;
    private String departmentName;
    private String position;
    private String employeeNumber;

    // ========== 会话信息 ==========
    private String sessionId;
    private String accessToken;
    private String refreshToken;
    private LocalDateTime loginTime;
    private LocalDateTime tokenExpireTime;

    // ========== 安全信息 ==========
    private Boolean isEnabled;
    private Boolean isLocked;
    private Boolean isMfaBound;
    private Integer mfaType;
    private Boolean isAdmin;            // 是否管理员
    private Boolean isTrusted;            // 是否可信任

    public static UserInfoContext createDefault() {
        return new UserInfoContext();
    }

    @Override
    protected UserInfoContext getDefault() {
        return createDefault();
    }

    @Override
    protected String getContextName() {
        return ContextConstants.CONTEXT_NAME_USER_INFO;
    }

    // ========== 便捷方法 ==========
    public boolean isAuthenticated() {
        return !Objects.isNull(userId);
    }

    public boolean hasRole(String roleCode) {
        return roles != null && roles.contains(roleCode);
    }

    public boolean hasPermission(String permission) {
        return permissions != null && permissions.contains(permission);
    }

    public boolean isTokenExpired() {
        if (tokenExpireTime == null) {
            return true;
        }
        return LocalDateTime.now().isAfter(tokenExpireTime);
    }
}
