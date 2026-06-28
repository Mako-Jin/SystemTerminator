package com.yaocode.sts.auth.application.converter;

import com.yaocode.sts.auth.application.dto.LoginSuccessDto;
import com.yaocode.sts.auth.application.dto.TenantConfigDto;
import com.yaocode.sts.auth.application.dto.response.PreLoginResponseDto;
import com.yaocode.sts.auth.domain.entity.BrandConfigEntity;
import com.yaocode.sts.auth.domain.entity.TenantConfigEntity;
import com.yaocode.sts.auth.domain.entity.TenantInfoEntity;
import com.yaocode.sts.auth.domain.entity.UserInfoEntity;
import com.yaocode.sts.auth.domain.entity.UserProfileEntity;
import com.yaocode.sts.auth.domain.entity.relation.RelTenantUserEntity;
import com.yaocode.sts.auth.domain.enums.LoginMethodEnums;
import com.yaocode.sts.auth.domain.valueobjects.composites.AuthenticationToken;
import com.yaocode.sts.auth.domain.valueobjects.composites.SecurityStatus;
import com.yaocode.sts.common.tools.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AuthApplicationConverter {

    AuthApplicationConverter INSTANCE = Mappers.getMapper(AuthApplicationConverter.class);

    /**
     * AuthenticationToken → LoginSuccessDto
     */
    default LoginSuccessDto toLoginSuccessDto(AuthenticationToken token) {
        if (token == null) {
            return null;
        }

        return LoginSuccessDto.builder()
                .userId(token.getUserId() != null ? token.getUserId().getValue() : null)
                .loginTime(Instant.now())
                .grantType(token.getGrantType())
                .accessToken(token.getAccessToken())
                .accessTokenExpiresAt(token.getAccessTokenExpiresAt())
                .refreshToken(token.getRefreshToken())
                .refreshTokenExpiresAt(token.getRefreshTokenExpiresAt())
                .rememberMeToken(token.getRememberMeToken())
                .rememberMeTokenExpiresAt(token.getRememberMeTokenExpiresAt())
                .build();
    }

    // ==================== 2. Token + 用户信息 → PreLoginResponseDto ====================

    /**
     * 构建认证成功响应（Remember Me场景）
     */
    default PreLoginResponseDto toAuthenticatedResponse(
            AuthenticationToken token,
            UserInfoEntity user,
            UserProfileEntity userProfile,
            List<RelTenantUserEntity> tenantUsers,
            List<TenantConfigDto> tenantConfigs,
            String stateToken,
            String sessionId
    ) {

        if (token == null || user == null) {
            return null;
        }

        RelTenantUserEntity firstTenantUser = tenantUsers.isEmpty() ? null : tenantUsers.get(0);

        return PreLoginResponseDto.builder()
                .isAuthenticated(true)
                .userId(user.getUserId().getValue())
                .username(user.getUsername().getValue())
                .displayName(buildDisplayName(user, userProfile, firstTenantUser))
                .avatar(buildAvatar(userProfile, firstTenantUser))
                .email(buildEmail(user))
                .mobile(buildMobile(user))
                .loginSuccessDto(toLoginSuccessDto(token))
                .stateToken(stateToken)
                .sessionId(sessionId)
                .needSelectTenant(tenantUsers.size() > 1)
                .tenantLoginConfigs(tenantConfigs)
                .serverTime(Instant.now().toEpochMilli())
                .build();
    }

    /**
     * 构建租户选择响应
     */
    default PreLoginResponseDto toTenantSelectionResponse(
            UserInfoEntity user,
            UserProfileEntity userProfile,
            List<RelTenantUserEntity> tenantUsers,
            List<TenantConfigDto> tenantConfigs,
            String stateToken,
            String sessionId
    ) {

        RelTenantUserEntity firstTenantUser = tenantUsers.isEmpty() ? null : tenantUsers.get(0);

        return PreLoginResponseDto.builder()
                .isAuthenticated(false)
                .userId(user != null ? user.getUserId().getValue() : null)
                .username(user != null ? user.getUsername().getValue() : null)
                .displayName(user != null ? buildDisplayName(user, userProfile, firstTenantUser) : null)
                .stateToken(stateToken)
                .sessionId(sessionId)
                .needSelectTenant(tenantConfigs.size() > 1)
                .tenantLoginConfigs(tenantConfigs)
                .serverTime(Instant.now().toEpochMilli())
                .build();
    }

    /**
     * 构建默认租户响应
     */
    default PreLoginResponseDto toDefaultTenantResponse(
            TenantConfigDto defaultConfig,
            String stateToken,
            String sessionId
    ) {

        return PreLoginResponseDto.builder()
                .isAuthenticated(false)
                .stateToken(stateToken)
                .sessionId(sessionId)
                .needSelectTenant(false)
                .tenantLoginConfigs(List.of(defaultConfig))
                .serverTime(Instant.now().toEpochMilli())
                .build();
    }

    // ==================== 3. 实体 → TenantConfigDto ====================

    /**
     * 构建单个租户配置
     */
    default TenantConfigDto toTenantConfigDto(
            TenantInfoEntity tenant,
            BrandConfigEntity brand,
            TenantConfigEntity config,
            SecurityStatus securityStatus,
            Boolean mfaBound
    ) {

        if (tenant == null) {
            return null;
        }

        return TenantConfigDto.builder()
                // 租户基本信息
                .tenantId(tenant.getTenantId().getValue())
                .tenantName(tenant.getTenantName())
                .tenantCode(tenant.getTenantCode() != null ? tenant.getTenantCode().getValue() : null)
                .tenantLogo(brand != null ? brand.getLogoUrl() : null)
                // 品牌配置
                .brandName(brand != null ? brand.getBrandConfigName() : null)
                .logoUrl(brand != null ? brand.getLogoUrl() : null)
                .loginTitle(brand != null ? brand.getLoginTitle() : null)
                .welcomeMessage(brand != null ? brand.getWelcomeMessage() : null)
                .subtitle(brand != null ? brand.getSubtitle() : null)
                .institution(brand != null ? brand.getInstitution() : null)
                .copyright(brand != null ? brand.getCopyright() : null)
                .primaryColor(brand != null ? brand.getPrimaryColor() : null)
                .loginBackgroundUrl(brand != null ? brand.getLoginBackgroundUrl() : null)
                // 登录方式
                .loginMethods(toLoginMethodStrings(config))
                .defaultLoginMethod(toDefaultLoginMethodString(config))
                // 安全状态
                .isLocked(securityStatus != null && securityStatus.isLocked())
                .lockReason(securityStatus != null ? securityStatus.getLockReason() : null)
                .remainingAttempts(securityStatus != null ? securityStatus.getRemainingAttempts() : getMaxLoginAttempts(config))
                .unlockTime(securityStatus != null ? securityStatus.getUnlockTime() : null)
                .captchaRequired(securityStatus != null && securityStatus.isCaptchaRequired())
                .captchaType(toCaptchaTypeString(config))
                .mfaRequired(isMfaRequired(config))
                .mfaBound(mfaBound != null && mfaBound)
                .isTrustedDevice(securityStatus != null && securityStatus.isTrustedDevice())
                // 注册相关
                .selfRegisterEnabled(isSelfRegisterEnabled(config))
                .forgotPasswordEnabled(isForgotPasswordEnabled(config))
                .build();
    }

    // ==================== 4. 用户信息提取 ====================

    /**
     * 获取用户显示名称
     * 优先级：租户昵称 > 用户Profile显示名 > 用户名
     */
    default String buildDisplayName(UserInfoEntity user, UserProfileEntity userProfile, RelTenantUserEntity tenantUser) {
        if (tenantUser != null && StringUtils.hasText(tenantUser.getTenantNickname())) {
            return tenantUser.getTenantNickname();
        }
        if (userProfile != null && StringUtils.hasText(userProfile.getDisplayName())) {
            return userProfile.getDisplayName();
        }
        if (user != null && user.getUsername() != null) {
            return user.getUsername().getValue();
        }
        return null;
    }

    /**
     * 获取用户头像
     * 优先级：租户头像 > 用户Profile头像
     */
    default String buildAvatar(UserProfileEntity userProfile, RelTenantUserEntity tenantUser) {
        if (tenantUser != null && StringUtils.hasText(tenantUser.getTenantAvatar())) {
            return tenantUser.getTenantAvatar();
        }
        if (userProfile != null && StringUtils.hasText(userProfile.getAvatar())) {
            return userProfile.getAvatar();
        }
        return null;
    }

    /**
     * 获取用户邮箱
     */
    default String buildEmail(UserInfoEntity user) {
        if (user != null && user.getEmail() != null) {
            return user.getEmail().getValue();
        }
        return null;
    }

    /**
     * 获取用户手机号
     */
    default String buildMobile(UserInfoEntity user) {
        if (user != null && user.getPhoneNum() != null) {
            return user.getPhoneNum().getValue();
        }
        return null;
    }

    // ==================== 5. 登录方式解析 ====================

    /**
     * 获取支持的登录方式字符串列表
     */
    default List<String> toLoginMethodStrings(TenantConfigEntity config) {
        if (config == null) {
            return List.of("PASSWORD");
        }

        List<LoginMethodEnums> methods = LoginMethodEnums.getSupportedMethods(config);
        return methods.stream()
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    /**
     * 获取默认登录方式字符串
     */
    default String toDefaultLoginMethodString(TenantConfigEntity config) {
        LoginMethodEnums defaultMethod = LoginMethodEnums.getDefaultMethod(config);
        return defaultMethod != null ? defaultMethod.name() : "PASSWORD";
    }

    /**
     * 获取验证码类型字符串
     */
    default String toCaptchaTypeString(TenantConfigEntity config) {
        if (config == null || config.getCaptchaType() == null) {
            return "MATH";
        }

        return switch (config.getCaptchaType()) {
            case TEXT -> "TEXT";
            case SLIDER -> "SLIDER";
            default -> "MATH";
        };
    }

    // ==================== 6. 配置解析 ====================

    /**
     * 获取最大登录尝试次数
     */
    default int getMaxLoginAttempts(TenantConfigEntity config) {
        if (config != null && config.getMaxLoginAttempts() != null) {
            return config.getMaxLoginAttempts();
        }
        return 5;
    }

    /**
     * 是否启用MFA
     */
    default boolean isMfaRequired(TenantConfigEntity config) {
        return config != null && config.getMfaConfig() != null
                && config.getMfaConfig().isRequired();
    }

    /**
     * 是否允许自助注册
     */
    default boolean isSelfRegisterEnabled(TenantConfigEntity config) {
        return config != null && config.getLoginConfig() != null
                && config.getLoginConfig().isSelfRegisterEnabled();
    }

    /**
     * 是否允许忘记密码
     */
    default boolean isForgotPasswordEnabled(TenantConfigEntity config) {
        return config != null && config.getLoginConfig() != null
                && config.getLoginConfig().isForgotPasswordEnabled();
    }
}
