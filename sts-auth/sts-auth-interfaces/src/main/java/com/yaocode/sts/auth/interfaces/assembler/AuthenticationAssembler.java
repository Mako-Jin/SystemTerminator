package com.yaocode.sts.auth.interfaces.assembler;

import com.yaocode.sts.auth.application.dto.LoginSuccessDto;
import com.yaocode.sts.auth.application.dto.TenantConfigDto;
import com.yaocode.sts.auth.application.dto.request.AuthenticationRequestDto;
import com.yaocode.sts.auth.application.dto.request.PreLoginRequestDto;
import com.yaocode.sts.auth.application.dto.response.PreLoginResponseDto;
import com.yaocode.sts.auth.interfaces.model.params.PreLoginParams;
import com.yaocode.sts.auth.interfaces.model.params.login.LoginRequestParams;
import com.yaocode.sts.auth.interfaces.model.vo.LoginConfigVo;
import com.yaocode.sts.auth.interfaces.model.vo.LoginSuccessVo;
import com.yaocode.sts.auth.interfaces.model.vo.PreLoginVo;
import com.yaocode.sts.auth.interfaces.model.vo.UserInfoVo;
import com.yaocode.sts.common.tools.StringUtils;
import com.yaocode.sts.common.web.context.RequestContextHolder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 参数转化器
 * @author: Jin-LiangBo
 * @date: 2026年04月14日 10:49
 */
@Mapper(componentModel = "spring")
public interface AuthenticationAssembler {

    AuthenticationAssembler INSTANCE = Mappers.getMapper(AuthenticationAssembler.class);

    /**
     * 参数对象转dto
     * @param loginRequestParams 参数对象
     * @return AuthenticationDto
     */
//    @Mapping(target = "grantType", source = "loginRequestParams.grantType")
//    @Mapping(target = "username", source = "loginRequestParams.username")
//    @Mapping(target = "password", source = "loginRequestParams.password")
//    @Mapping(target = "phoneNum", source = "loginRequestParams.phoneNum")
//    @Mapping(target = "verifyCode", source = "loginRequestParams.verifyCode")
//    @Mapping(target = "rememberMe", source = "loginRequestParams.rememberMe")
//    @Mapping(target = "captcha", source = "loginRequestParams.captcha")
//    @Mapping(target = "captchaKey", source = "loginRequestParams.captchaKey")
//    @Mapping(target = "state", source = "loginRequestParams.state")
//    @Mapping(target = "scope", source = "loginRequestParams.scope")
//    @Mapping(target = "clientId", source = "loginRequestParams.clientId")
//    @Mapping(target = "clientType", source = "loginRequestParams.clientType")
//    @Mapping(target = "clientVersion", source = "loginRequestParams.clientVersion")
//    @Mapping(target = "deviceId", source = "loginRequestParams.deviceId")
//    @Mapping(target = "deviceType", source = "loginRequestParams.deviceType")
//    @Mapping(target = "countryCode", source = "loginRequestParams.countryCode")
//    @Mapping(target = "smsSessionId", source = "loginRequestParams.smsSessionId")
//    @Mapping(target = "autoRegister", source = "loginRequestParams.autoRegister")
    AuthenticationRequestDto toAuthenticationDto(LoginRequestParams loginRequestParams);

    /**
     * 转换为应用层命令
     * 自动补充服务端参数
     */
    default PreLoginRequestDto toPreLoginDto(PreLoginParams params) {
        if (Objects.isNull(params)) {
            return null;
        }
        PreLoginRequestDto preLoginDto = new PreLoginRequestDto();

        // 1. 用户输入参数（直接映射）
        preLoginDto.setTenantCode(params.getTenantCode());
        preLoginDto.setTenantId(params.getTenantId());
        preLoginDto.setIdentifier(params.getIdentifier());
        preLoginDto.setRedirectUri(params.getRedirectUri());
        preLoginDto.setRememberMeToken(params.getRememberMeToken());
        preLoginDto.setLanguage(StringUtils.hasText(params.getLanguage()) ?
                params.getLanguage() : RequestContextHolder.getLanguage());
        preLoginDto.setDomain(params.getDomain());

        // 2. 服务端上下文参数（从 RequestContext 获取）
        preLoginDto.setClientId(RequestContextHolder.getClientId());
        preLoginDto.setClientType(RequestContextHolder.getClientType());
        preLoginDto.setClientVersion(RequestContextHolder.getClientVersion());
        preLoginDto.setDeviceId(RequestContextHolder.getDeviceId());
        preLoginDto.setDeviceType(RequestContextHolder.getDeviceType());
        preLoginDto.setSessionId(RequestContextHolder.getSessionId());
        preLoginDto.setIpAddress(RequestContextHolder.getIpAddress());
        preLoginDto.setUserAgent(RequestContextHolder.getUserAgent());

        return preLoginDto;
    }

    /**
     * 预登录响应DTO → 预登录VO
     * 使用 MapStruct + 辅助方法
     */
    @Mapping(target = "userInfo", source = "preLoginResponseDto", qualifiedByName = "buildUserInfo")
    @Mapping(target = "loginSuccessVo", source = "preLoginResponseDto.loginSuccessDto", qualifiedByName = "buildLoginSuccessVo")
    @Mapping(target = "tenantLoginConfigs", source = "preLoginResponseDto.tenantLoginConfigs", qualifiedByName = "buildLoginConfigVoList")
    @Mapping(target = "serverTime", expression = "java(java.time.Instant.now().toEpochMilli())")
    PreLoginVo toPreLoginVo(PreLoginResponseDto preLoginResponseDto);

    /**
     * 构建用户信息VO
     * 只在已认证且有用户ID时才构建
     */
    @Named("buildUserInfo")
    default UserInfoVo buildUserInfo(PreLoginResponseDto dto) {
        if (Objects.isNull(dto)) {
            return null;
        }

        // 纯数据判断，不是业务逻辑
        boolean shouldBuild = Boolean.TRUE.equals(dto.getIsAuthenticated())
                && StringUtils.hasText(dto.getUserId());

        if (!shouldBuild) {
            return null;
        }

        return UserInfoVo.builder()
                .userId(dto.getUserId())
                .username(dto.getUsername())
                .displayName(dto.getDisplayName())
                .avatar(dto.getAvatar())
                .email(dto.getEmail())
                .mobile(dto.getMobile())
                .build();
    }

    /**
     * 构建登录成功VO
     */
    @Named("buildLoginSuccessVo")
    default LoginSuccessVo buildLoginSuccessVo(LoginSuccessDto dto) {
        if (Objects.isNull(dto)) {
            return null;
        }

        return LoginSuccessVo.builder()
                .userId(dto.getUserId())
                .loginTime(dto.getLoginTime())
                .grantType(dto.getGrantType())
                .accessToken(dto.getAccessToken())
                .accessTokenExpiresAt(dto.getAccessTokenExpiresAt())
                .refreshToken(dto.getRefreshToken())
                .refreshTokenExpiresAt(dto.getRefreshTokenExpiresAt())
                .rememberMeToken(dto.getRememberMeToken())
                .rememberMeTokenExpiresAt(dto.getRememberMeTokenExpiresAt())
                .build();
    }

    /**
     * 构建租户登录配置列表VO
     */
    @Named("buildLoginConfigVoList")
    default List<LoginConfigVo> buildLoginConfigVoList(List<TenantConfigDto> dtoList) {
        if (CollectionUtils.isEmpty(dtoList)) {
            return Collections.emptyList();
        }

        List<LoginConfigVo> vos = new ArrayList<>(dtoList.size());
        for (TenantConfigDto dto : dtoList) {
            vos.add(toLoginConfigVo(dto));
        }
        return vos;
    }

    /**
     * 单个租户配置DTO → VO
     * 纯字段映射，用 default 方法确保空安全
     */
    default LoginConfigVo toLoginConfigVo(TenantConfigDto dto) {
        if (dto == null) {
            return null;
        }

        return LoginConfigVo.builder()
                // 租户基本信息
                .tenantId(dto.getTenantId())
                .tenantName(dto.getTenantName())
                .tenantCode(dto.getTenantCode())
                .tenantLogo(dto.getTenantLogo())
                // 品牌配置
                .brandName(dto.getBrandName())
                .logoUrl(dto.getLogoUrl())
                .loginTitle(dto.getLoginTitle())
                .welcomeMessage(dto.getWelcomeMessage())
                .subtitle(dto.getSubtitle())
                .institution(dto.getInstitution())
                .copyright(dto.getCopyright())
                .primaryColor(dto.getPrimaryColor())
                .loginBackgroundUrl(dto.getLoginBackgroundUrl())
                // 登录方式
                .loginMethods(dto.getLoginMethods())
                .defaultLoginMethod(dto.getDefaultLoginMethod())
                // 安全状态
                .isLocked(dto.getIsLocked())
                .lockReason(dto.getLockReason())
                .remainingAttempts(dto.getRemainingAttempts())
                .unlockTime(dto.getUnlockTime())
                .captchaRequired(dto.getCaptchaRequired())
                .captchaType(dto.getCaptchaType())
                .mfaRequired(dto.getMfaRequired())
                .mfaBound(dto.getMfaBound())
                .isTrustedDevice(dto.getIsTrustedDevice())
                // 注册相关
                .selfRegisterEnabled(dto.getSelfRegisterEnabled())
                .forgotPasswordEnabled(dto.getForgotPasswordEnabled())
                .build();
    }

}
