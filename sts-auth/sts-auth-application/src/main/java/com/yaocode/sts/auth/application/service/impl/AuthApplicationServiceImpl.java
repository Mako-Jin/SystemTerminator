package com.yaocode.sts.auth.application.service.impl;

import com.yaocode.sts.auth.application.converter.AuthApplicationConverter;
import com.yaocode.sts.auth.application.dto.TenantConfigDto;
import com.yaocode.sts.auth.application.dto.request.AuthenticationRequestDto;
import com.yaocode.sts.auth.application.dto.request.PreLoginRequestDto;
import com.yaocode.sts.auth.application.dto.response.AuthenticationResponseDto;
import com.yaocode.sts.auth.application.dto.response.PreLoginResponseDto;
import com.yaocode.sts.auth.application.service.AuthApplicationService;
import com.yaocode.sts.auth.domain.entity.BrandConfigEntity;
import com.yaocode.sts.auth.domain.entity.TenantConfigEntity;
import com.yaocode.sts.auth.domain.entity.TenantInfoEntity;
import com.yaocode.sts.auth.domain.entity.UserInfoEntity;
import com.yaocode.sts.auth.domain.entity.UserProfileEntity;
import com.yaocode.sts.auth.domain.entity.relation.RelTenantUserEntity;
import com.yaocode.sts.auth.domain.repository.BrandConfigRepository;
import com.yaocode.sts.auth.domain.repository.RelTenantUserRepository;
import com.yaocode.sts.auth.domain.repository.TenantConfigRepository;
import com.yaocode.sts.auth.domain.repository.TenantInfoRepository;
import com.yaocode.sts.auth.domain.repository.UserInfoRepository;
import com.yaocode.sts.auth.domain.repository.UserProfileRepository;
import com.yaocode.sts.auth.domain.service.AuthDomainService;
import com.yaocode.sts.auth.domain.service.AuthSecurityDomainService;
import com.yaocode.sts.auth.domain.service.JwtTokenService;
import com.yaocode.sts.auth.domain.service.TenantDomainService;
import com.yaocode.sts.auth.domain.valueobjects.composites.AuthenticationToken;
import com.yaocode.sts.auth.domain.valueobjects.composites.RememberMeAuthCredential;
import com.yaocode.sts.auth.domain.valueobjects.composites.SecurityStatus;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ClientId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.DeviceId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.IpAddress;
import com.yaocode.sts.auth.domain.valueobjects.primitives.TenantCode;
import com.yaocode.sts.common.basic.enums.OppositeEnums;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import com.yaocode.sts.common.tools.StringUtils;
import com.yaocode.sts.common.tools.id.IdFactory;
import com.yaocode.sts.common.tools.id.IdGeneratorType;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 认证服务实现层
 * @author: Jin-LiangBo
 * @date: 2025年10月12日 14:10
 */
@Service
public class AuthApplicationServiceImpl implements AuthApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthApplicationServiceImpl.class);

    @Resource
    private AuthDomainService authDomainService;

    // ==================== 仓储依赖 ====================
    @Resource
    private UserInfoRepository userInfoRepository;
    @Resource
    private RelTenantUserRepository relTenantUserRepository;
    @Resource
    private TenantInfoRepository tenantInfoRepository;
    @Resource
    private TenantConfigRepository tenantConfigRepository;
    @Resource
    private BrandConfigRepository brandConfigRepository;
    @Resource
    private UserProfileRepository userProfileRepository;

    // ==================== 领域服务依赖 ====================
    @Resource
    private AuthSecurityDomainService authSecurityDomainService;
    @Resource
    private TenantDomainService tenantDomainService;
    @Resource
    private JwtTokenService jwtTokenService;

    // ==================== 转换器依赖 ====================
    @Resource
    private AuthApplicationConverter authApplicationConverter;

    // ==================== 预登录接口 ====================

    @Override
    @Transactional
    public PreLoginResponseDto preLogin(PreLoginRequestDto preLoginRequestDto) {
        logger.info("预登录请求: identifier={}, tenantCode={}, clientId={}",
                preLoginRequestDto.getIdentifier(), preLoginRequestDto.getTenantCode(), preLoginRequestDto.getClientId());

        // ============================================
        // Step 1: 尝试Remember Me自动登录
        // ============================================
        AuthenticationToken rememberMeToken = tryRememberMe(preLoginRequestDto);
        if (rememberMeToken != null && rememberMeToken.getIsAuthenticated()) {
            logger.info("Remember Me自动登录成功: userId={}", rememberMeToken.getUserId());
            return buildAuthenticatedResponse(rememberMeToken, preLoginRequestDto);
        }

        // ============================================
        // Step 2: 根据identifier查询用户关联的租户
        // ============================================
        List<RelTenantUserEntity> tenantUsers = findTenantUsers(preLoginRequestDto);
        if (!tenantUsers.isEmpty()) {
            logger.info("根据identifier查询到 {} 个关联租户", tenantUsers.size());
            return buildTenantSelectionResponse(tenantUsers, preLoginRequestDto);
        }

        // ============================================
        // Step 3: 未识别到用户，返回默认租户配置
        // ============================================
        logger.info("未识别到用户，返回默认租户配置");
        return buildDefaultTenantResponse(preLoginRequestDto);
    }

    // ==================== 登录认证接口 ====================

    @Override
    @Transactional
    public AuthenticationResponseDto authentication(AuthenticationRequestDto authenticationRequestDto) {
        return null;
    }

    // ==================== 私有方法 ====================

    /**
     * 尝试Remember Me自动登录
     */
    private AuthenticationToken tryRememberMe(PreLoginRequestDto request) {
        if (!StringUtils.hasText(request.getRememberMeToken())) {
            return null;
        }

        try {
            ClientId clientId = ClientId.of(request.getClientId());
            DeviceId deviceId = DeviceId.of(request.getDeviceId());
            RememberMeAuthCredential credential = new RememberMeAuthCredential(
                    request.getRememberMeToken(),
                    clientId,
                    deviceId
            );
            return authDomainService.authenticate(credential);
        } catch (Exception e) {
            logger.debug("Remember Me令牌无效或已过期: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 根据identifier查询用户关联的租户
     */
    private List<RelTenantUserEntity> findTenantUsers(PreLoginRequestDto request) {
        if (!StringUtils.hasText(request.getIdentifier())) {
            return new ArrayList<>();
        }

        // 尝试通过用户名/手机号/邮箱查询
        List<RelTenantUserEntity> tenantUsers = relTenantUserRepository.findByIdentifier(request.getIdentifier());

        if (tenantUsers == null) {
            return new ArrayList<>();
        }

        // 如果指定了租户，只返回该租户
        if (StringUtils.hasText(request.getTenantId())) {
            return tenantUsers.stream()
                    .filter(tu -> tu.getTenantId().getValue().equals(request.getTenantId()))
                    .collect(Collectors.toList());
        }

        if (StringUtils.hasText(request.getTenantCode())) {
            // 需要先通过tenantCode查询tenantId
            Optional<TenantInfoEntity> tenant = tenantInfoRepository.getByTenantCode(request.getTenantCode());
            if (tenant.isPresent()) {
                String tenantId = tenant.get().getTenantId().getValue();
                return tenantUsers.stream()
                        .filter(tu -> tu.getTenantId().getValue().equals(tenantId))
                        .collect(Collectors.toList());
            }
        }

        return tenantUsers;
    }

    /**
     * 构建认证成功响应（Remember Me场景）
     */
    private PreLoginResponseDto buildAuthenticatedResponse(
            AuthenticationToken authenticationToken,
            PreLoginRequestDto preLoginRequestDto
    ) {

        // 查询用户信息
        Optional<UserInfoEntity> userOpt = userInfoRepository.findById(authenticationToken.getUserId());
        if (userOpt.isEmpty()) {
            return buildDefaultTenantResponse(preLoginRequestDto);
        }

        UserInfoEntity user = userOpt.get();
        Optional<UserProfileEntity> userProfileOpt = userProfileRepository.findById(user.getUserId());

        // 查询用户关联的租户
        List<RelTenantUserEntity> tenantUsers = relTenantUserRepository.findByUserId(authenticationToken.getUserId());

        // 构建租户配置列表
        List<TenantConfigDto> tenantConfigs = buildTenantLoginConfigs(tenantUsers, preLoginRequestDto);

        return authApplicationConverter.toAuthenticatedResponse(
                authenticationToken,
                user,
                userProfileOpt.orElse(null),
                tenantUsers,
                tenantConfigs,
                generateStateToken(preLoginRequestDto),
                preLoginRequestDto.getSessionId()
        );
    }

    /**
     * 构建租户选择响应
     */
    private PreLoginResponseDto buildTenantSelectionResponse(
            List<RelTenantUserEntity> tenantUsers,
            PreLoginRequestDto preLoginRequestDto
    ) {

        // 获取用户信息（第一个租户下的用户信息）
        UserInfoEntity user = null;
        Optional<UserProfileEntity> userProfileOpt = Optional.empty();
        if (!tenantUsers.isEmpty()) {
            Optional<UserInfoEntity> userOpt = userInfoRepository.findById(tenantUsers.get(0).getUserId());
            if (userOpt.isPresent()) {
                user = userOpt.get();
                userProfileOpt = userProfileRepository.findById(user.getUserId());
            }
        }

        // 构建租户配置列表
        List<TenantConfigDto> tenantConfigs = buildTenantLoginConfigs(tenantUsers, preLoginRequestDto);

        return authApplicationConverter.toTenantSelectionResponse(
                user,
                userProfileOpt.orElse(null),
                tenantUsers,
                tenantConfigs,
                generateStateToken(preLoginRequestDto),
                preLoginRequestDto.getSessionId()
        );
    }

    /**
     * 构建默认租户响应
     */
    private PreLoginResponseDto buildDefaultTenantResponse(PreLoginRequestDto preLoginRequestDto) {
        TenantId tenantId = tenantDomainService.resolveTenantId(
                TenantId.of(preLoginRequestDto.getTenantId()),
                TenantCode.of(preLoginRequestDto.getTenantCode()),
                preLoginRequestDto.getDomain()
        );

        TenantConfigDto defaultConfig = buildSingleTenantConfig(tenantId, null, preLoginRequestDto);

        return authApplicationConverter.toDefaultTenantResponse(
                defaultConfig,
                generateStateToken(preLoginRequestDto),
                preLoginRequestDto.getSessionId()
        );
    }

    /**
     * 构建多个租户的登录配置列表
     */
    private List<TenantConfigDto> buildTenantLoginConfigs(
            List<RelTenantUserEntity> tenantUsers,
            PreLoginRequestDto preLoginRequestDto) {

        if (tenantUsers == null || tenantUsers.isEmpty()) {
            return new ArrayList<>();
        }

        return tenantUsers.stream()
                .map(tenantUser -> buildSingleTenantConfig(
                        tenantUser.getTenantId(),
                        tenantUser,
                        preLoginRequestDto
                ))
                .collect(Collectors.toList());
    }

    /**
     * 构建单个租户的登录配置
     */
    private TenantConfigDto buildSingleTenantConfig(
            TenantId tenantId,
            RelTenantUserEntity tenantUser,
            PreLoginRequestDto preLoginRequestDto
    ) {

        // 查询租户信息
        Optional<TenantInfoEntity> tenantOpt = tenantInfoRepository.findById(tenantId);
        Optional<TenantConfigEntity> configOpt = tenantConfigRepository.findByTenantId(tenantId);
        Optional<BrandConfigEntity> brandOpt = brandConfigRepository.findByTenantId(tenantId);

        // 检查安全状态
        SecurityStatus securityStatus;
        if (tenantUser != null) {
            securityStatus = authSecurityDomainService.checkSecurityStatus(
                    tenantUser.getUserId(),
                    tenantId,
                    DeviceId.of(preLoginRequestDto.getDeviceId()),
                    IpAddress.of(preLoginRequestDto.getIpAddress())
            );
        } else {
            securityStatus = SecurityStatus.builder()
                    .locked(false)
                    .remainingAttempts(getMaxLoginAttempts(configOpt))
                    .captchaRequired(false)
                    .trustedDevice(false)
                    .build();
        }

        TenantInfoEntity tenant = tenantOpt.orElse(null);
        BrandConfigEntity brand = brandOpt.orElse(null);
        return authApplicationConverter.toTenantConfigDto(
                tenant,
                brand,
                configOpt.orElse(null),
                securityStatus,
                tenantUser != null && Objects.equals(tenantUser.getMfaBound(), OppositeEnums.YES.getCode())
        );
    }

    /**
     * 获取最大登录尝试次数
     */
    public int getMaxLoginAttempts(Optional<TenantConfigEntity> config) {
        if (config.isPresent() && config.get().getMaxLoginAttempts() != null) {
            return config.get().getMaxLoginAttempts();
        }
        return 5;
    }

    /**
     * 生成State Token
     */
    private String generateStateToken(PreLoginRequestDto preLoginRequestDto) {
        String state = IdFactory.generate(IdGeneratorType.UUID);
        ClientId clientId = ClientId.of(preLoginRequestDto.getClientId());
        DeviceId deviceId = DeviceId.of(preLoginRequestDto.getDeviceId());
        return jwtTokenService.generateStateToken(state, preLoginRequestDto.getRedirectUri(), clientId, deviceId);
    }

}
