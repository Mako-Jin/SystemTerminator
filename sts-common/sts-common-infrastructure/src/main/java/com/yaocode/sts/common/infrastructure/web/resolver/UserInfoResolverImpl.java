package com.yaocode.sts.common.infrastructure.web.resolver;

import com.yaocode.sts.common.domain.constants.HeaderConstants;
import com.yaocode.sts.common.domain.constants.RequestConstants;
import com.yaocode.sts.common.domain.context.UserInfoContext;
import com.yaocode.sts.common.domain.context.spi.UserInfoResolver;
import com.yaocode.sts.common.domain.valueobject.TenantCode;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import com.yaocode.sts.common.domain.valueobject.UserId;
import com.yaocode.sts.common.domain.valueobject.Username;
import com.yaocode.sts.common.domain.web.HttpRequestContext;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * 用户信息解析器
 * <p>
 * 支持双通道解析：优先从 Query/Form Parameter 获取，Header 不存在时从 HTTP Header 获取
 */
public class UserInfoResolverImpl implements UserInfoResolver {

    @Override
    public Optional<UserInfoContext> resolve(HttpRequestContext context) {
        UserInfoContext userInfo = UserInfoContext.createDefault();

        // ========== 用户基础信息 ==========
        context.getHeaderOrParameter(HeaderConstants.USER_ID)
                .or(() -> context.getHeaderOrParameter(RequestConstants.USER_ID))
                .ifPresent(v -> userInfo.setUserId(UserId.of(v)));
        context.getHeaderOrParameter(HeaderConstants.USER_NAME)
                .or(() -> context.getHeaderOrParameter(RequestConstants.USER_NAME))
                .ifPresent(v -> userInfo.setUsername(Username.of(v)));
        context.getHeaderOrParameter(HeaderConstants.NICKNAME)
                .or(() -> context.getHeaderOrParameter(RequestConstants.NICKNAME))
                .ifPresent(userInfo::setNickname);
        context.getHeaderOrParameter(HeaderConstants.REAL_NAME)
                .or(() -> context.getHeaderOrParameter(RequestConstants.REAL_NAME))
                .ifPresent(userInfo::setRealName);
        context.getHeaderOrParameter(HeaderConstants.AVATAR_URL)
                .or(() -> context.getHeaderOrParameter(RequestConstants.AVATAR_URL))
                .ifPresent(userInfo::setAvatar);
        context.getHeaderOrParameter(HeaderConstants.EMAIL)
                .or(() -> context.getHeaderOrParameter(RequestConstants.EMAIL))
                .ifPresent(userInfo::setEmail);
        context.getHeaderOrParameter(HeaderConstants.PHONE)
                .or(() -> context.getHeaderOrParameter(RequestConstants.PHONE))
                .ifPresent(userInfo::setPhone);

        // ========== 租户信息 ==========
        context.getHeaderOrParameter(HeaderConstants.TENANT_ID)
                .ifPresent(v -> userInfo.setTenantId(TenantId.of(v)));
        context.getHeaderOrParameter(HeaderConstants.TENANT_NAME)
                .ifPresent(userInfo::setTenantName);
        context.getHeaderOrParameter(HeaderConstants.TENANT_CODE)
                .ifPresent(v -> userInfo.setTenantCode(TenantCode.of(v)));

        // ========== 权限信息 ==========
        context.getHeaderOrParameter(HeaderConstants.ROLE)
                .or(() -> context.getHeaderOrParameter(RequestConstants.ROLE))
                .ifPresent(rolesStr -> {
                    Set<String> roles = new HashSet<>();
                    for (String r : rolesStr.split(",")) {
                        String trimmed = r.trim();
                        if (!trimmed.isEmpty()) roles.add(trimmed);
                    }
                    userInfo.setRoles(roles);
                });

        // ========== 组织信息 ==========
        context.getHeaderOrParameter(HeaderConstants.ORGANIZATION_ID)
                .ifPresent(userInfo::setOrganizationId);
        context.getHeaderOrParameter(HeaderConstants.DEPARTMENT_ID)
                .ifPresent(userInfo::setDepartmentId);

        // ========== 会话信息 ==========
        context.getHeaderOrParameter(HeaderConstants.ACCESS_TOKEN)
                .ifPresent(userInfo::setAccessToken);

        // ========== 安全信息 ==========
        context.getHeaderOrParameter(HeaderConstants.IS_ADMIN)
                .or(() -> context.getHeaderOrParameter(RequestConstants.IS_ADMIN))
                .ifPresent(v -> userInfo.setIsAdmin(Boolean.parseBoolean(v)));
        context.getHeaderOrParameter(HeaderConstants.IS_TRUSTED)
                .or(() -> context.getHeaderOrParameter(RequestConstants.IS_TRUSTED))
                .ifPresent(v -> userInfo.setIsTrusted(Boolean.parseBoolean(v)));

        return Optional.of(userInfo);
    }

}