package com.yaocode.sts.auth.application.service.impl;

import com.yaocode.sts.auth.application.converter.AuthApplicationConverter;
import com.yaocode.sts.auth.application.dto.request.AuthenticationRequestDto;
import com.yaocode.sts.auth.application.dto.request.PreLoginRequestDto;
import com.yaocode.sts.auth.application.dto.response.AuthenticationResponseDto;
import com.yaocode.sts.auth.application.dto.response.PreLoginResponseDto;
import com.yaocode.sts.auth.application.exception.AuthException;
import com.yaocode.sts.auth.application.service.AuthApplicationService;
import com.yaocode.sts.auth.domain.entity.LoginSuccessEntity;
import com.yaocode.sts.auth.domain.entity.UserInfoEntity;
import com.yaocode.sts.auth.domain.repository.UserInfoRepository;
import com.yaocode.sts.auth.domain.service.AuthDomainService;
import com.yaocode.sts.auth.domain.valueobjects.composites.AuthenticationToken;
import com.yaocode.sts.auth.domain.valueobjects.composites.RememberMeAuthCredential;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ClientId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.DeviceId;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;

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

    @Resource
    private UserInfoRepository userInfoRepository;

    @Resource
    private AuthApplicationConverter authApplicationConverter;

    @Override
    @Transactional
    public PreLoginResponseDto preLogin(PreLoginRequestDto preLoginDto) {

        ClientId clientId = ClientId.of(preLoginDto.getClientId());
        DeviceId deviceId = DeviceId.of(preLoginDto.getDeviceId());

        if (StringUtils.hasText(preLoginDto.getRememberMe())) {
            RememberMeAuthCredential rememberMeAuthCredential = new RememberMeAuthCredential(preLoginDto.getRememberMe(), clientId, deviceId);
            AuthenticationToken authenticationToken = authDomainService.authenticate(rememberMeAuthCredential);

            if (authenticationToken.getIsAuthenticated()) {
                logger.info("记住我自动登录成功: userId={}", authenticationToken.getUserId());
                Optional<UserInfoEntity> userInfoEntityOptional = userInfoRepository.findById(authenticationToken.getUserId());
                if (userInfoEntityOptional.isEmpty()) {
                    throw new AuthException("用户不存在");
                }
                LoginSuccessEntity loginSuccessEntity = authDomainService.loginSuccess(authenticationToken);
                return authApplicationConverter.toLoginSuccessDto(loginSuccessEntity);
            }
            logger.debug("记住我令牌无效或已过期，继续返回登录页面");
        }

        // ========== 2. 获取登录配置 ==========
//        LoginConfig loginConfig = configRepository.getByClientId(preLoginDto.getClientId());
//        if (loginConfig == null) {
//            loginConfig = getDefaultLoginConfig();
//        }
//
//        // ========== 3. 获取当前会话的失败次数 ==========
//        Integer failCount = null;
//        Boolean mfaBound = null;
//        if (preLoginDto.getSessionId() != null) {
//            failCount = userRiskRepository.getFailCount(preLoginDto.getSessionId());
//            mfaBound = userRiskRepository.isMfaBound(preLoginDto.getSessionId());
//        }
//
//        // ========== 4. 动态判断是否需要验证码 ==========
//        boolean needCaptcha = loginConfig.isCaptchaEnabled();
//        if (failCount != null && failCount >= 3) {
//            needCaptcha = true;  // 失败3次后强制验证码
//        }
//
//        // ========== 5. 创建登录会话（聚合根） ==========
//        StateToken stateToken = stateTokenFactory.create(
//                preLoginDto.getClientId(),
//                preLoginDto.getDeviceId(),
//                preLoginDto.getIpAddress(),
//                preLoginDto.getUserAgent()
//        );
//
//        String sessionId = generateSessionId();
//        LoginSession session = LoginSession.builder()
//                .sessionId(sessionId)
//                .clientId(preLoginDto.getClientId())
//                .deviceId(preLoginDto.getDeviceId())
//                .clientType(preLoginDto.getClientType())
//                .clientVersion(preLoginDto.getClientVersion())
//                .stateToken(stateToken)
//                .ipAddress(preLoginDto.getIpAddress())
//                .userAgent(preLoginDto.getUserAgent())
//                .build();
//
//        // ========== 6. 保存会话 ==========
//        sessionRepository.save(session);

        // ========== 7. 生成验证码key（如果需要） ==========
//        String captchaKey = null;
//        if (needCaptcha) {
//            captchaKey = generateCaptchaKey();
//        }
//
//        // ========== 8. 构建页面响应 ==========
//        LoginPageConfig pageConfig = LoginPageConfig.builder()
//                .rememberMeEnabled(loginConfig.isRememberMeEnabled())
//                .captchaEnabled(needCaptcha)  // 动态覆盖
//                .captchaType(loginConfig.getCaptchaType())
//                .captchaKey(captchaKey)
//                .mfaEnabled(loginConfig.isMfaEnabled())
//                .otpType(loginConfig.getOtpType())
//                .otpInterval(loginConfig.getOtpInterval())
//                .kerberosEnabled(loginConfig.isKerberosEnabled())
//                .passkeyEnabled(loginConfig.isPasskeyEnabled())
//                .passkeyAllowedOrigins(loginConfig.getPasskeyAllowedOrigins())
//                .socialProviders(loginConfig.getSocialProviders())
//                .institution(loginConfig.getInstitution())
//                .kerberosProxies(loginConfig.getKerberosProxies())
//                .logoUrl(loginConfig.getLogoUrl())
//                .copyright(loginConfig.getCopyright())
//                .loginTitle(loginConfig.getLoginTitle())
//                .registerEnabled(loginConfig.isRegisterEnabled())
//                .forgotPasswordEnabled(loginConfig.isForgotPasswordEnabled())
//                .smsLoginEnabled(loginConfig.isSmsLoginEnabled())
//                .build();
//
//        return PreLoginResponseDto.page(
//                pageConfig,
//                stateToken.getValue(),
//                sessionId,
//                failCount,
//                mfaBound
//        );
        return null;
    }

    @Override
    public AuthenticationResponseDto authentication(AuthenticationRequestDto authenticationDto) {
        return null;
    }

    // public AuthenticationResult authenticate(LoginCommand command) {
    //     try {
    //         User user = userRepository.findByUsername(command.username())
    //                 .orElseThrow(() -> new BadCredentialsException("用户不存在"));
    //
    //         // 验证密码
    //         if (!passwordEncoder.matches(command.password(), user.getPassword())) {
    //             // 发布登录失败事件
    //             eventPublisher.publish(new UserLoginFailedEvent(
    //                     command.username(),
    //                     "INVALID_PASSWORD",
    //                     command.ipAddress()
    //             ));
    //             throw new BadCredentialsException("密码错误");
    //         }
    //
    //         // 验证用户状态
    //         if (user.getStatus() != UserStatus.ACTIVE) {
    //             eventPublisher.publish(new UserLoginFailedEvent(
    //                     command.username(),
    //                     "ACCOUNT_INACTIVE",
    //                     command.ipAddress()
    //             ));
    //             throw new AccountStatusException("账户状态异常");
    //         }
    //
    //         // 发布登录成功事件
    //         eventPublisher.publish(new UserLoginSuccessEvent(
    //                 user.getId(),
    //                 "PASSWORD",
    //                 command.ipAddress(),
    //                 command.userAgent()
    //         ));
    //
    //         // 清除登录失败记录
    //         loginAttemptService.clearFailedAttempts(command.username());
    //
    //         log.info("用户登录成功: userId={}, username={}", user.getId(), user.getUsername());
    //
    //         return AuthenticationResult.success(user);
    //
    //     } catch (AuthenticationException e) {
    //         log.warn("用户登录失败: username={}, reason={}", command.username(), e.getMessage());
    //         throw e;
    //     }
    // }

}
