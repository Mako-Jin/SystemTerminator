package com.yaocode.sts.auth.domain.service.provider;

import com.yaocode.sts.auth.domain.entity.UserInfoEntity;
import com.yaocode.sts.auth.domain.enums.GrantTypeEnums;
import com.yaocode.sts.auth.domain.repository.RefreshTokenRepository;
import com.yaocode.sts.auth.domain.repository.RememberMeTokenRepository;
import com.yaocode.sts.auth.domain.repository.UserInfoRepository;
import com.yaocode.sts.auth.domain.service.JwtTokenService;
import com.yaocode.sts.auth.domain.valueobjects.AbstractAuthCredential;
import com.yaocode.sts.auth.domain.valueobjects.composites.PasswordAuthCredential;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 密码模式认证provider
 * @author: Jin-LiangBo
 * @date: 2026年04月14日 10:52
 */
@Service
public class PasswordAuthenticationProvider extends AbstractAuthenticationProvider<PasswordAuthCredential> {

    @Resource
    private UserInfoRepository userInfoRepository;

    protected PasswordAuthenticationProvider(
            JwtTokenService jwtTokenService,
            RememberMeTokenRepository rememberMeRepository,
            RefreshTokenRepository refreshTokenRepository
    ) {
        super(jwtTokenService, refreshTokenRepository, rememberMeRepository);
    }

    @Override
    public GrantTypeEnums getGrantType() {
        return GrantTypeEnums.PASSWORD;
    }

    @Override
    public boolean supports(AbstractAuthCredential credential) {
        return super.supports(credential);
    }

    @Override
    protected Optional<UserInfoEntity> doAuthenticate(PasswordAuthCredential credential) {
        String username = credential.getUsername().getValue();

        // 1. 检查登录尝试次数
//        if (loginAttemptService.isLocked(username)) {
//            return AuthenticationResult.failure(
//                    AuthenticationFailure.accountLocked(loginAttemptService.getLockoutSeconds(username))
//            );
//        }
//
//        // 2. 查找用户
//        UserInfoAggregate user = userInfoRepository.findByUsername(credential.getUsername())
//                .orElse(null);
//
//        if (user == null) {
//            loginAttemptService.recordFailedAttempt(username);
//            return AuthenticationResult.failure(AuthenticationFailure.invalidCredentials());
//        }
//
//        // 3. 验证密码
//        // 注意：密码存储在实际的RelTenantUser中，这里简化处理
//        if (!passwordEncoder.matches(credential.getPassword(), user.getPasswordHash())) {
//            loginAttemptService.recordFailedAttempt(username);
//            return AuthenticationResult.failure(AuthenticationFailure.invalidCredentials());
//        }
//
//        // 4. 验证用户状态
//        if (user.getStatus() == UserStatusEnums.LOCKED) {
//            return AuthenticationResult.failure(
//                    AuthenticationFailure.accountLocked(calculateLockoutSeconds(user))
//            );
//        }
//
//        if (user.getStatus() == UserStatusEnums.INACTIVE) {
//            return AuthenticationResult.failure(AuthenticationFailure.accountDisabled());
//        }
//
//        // 5. 清除登录失败记录
//        loginAttemptService.clearFailedAttempts(username);
//
//        // 6. 检查MFA
//        if (user.isMfaBound()) {
//            return AuthenticationResult.failure(AuthenticationFailure.mfaRequired());
//        }
//
//        // 7. 构建认证成功结果
//        AuthenticationSuccess success = buildSuccess(user, credential);
//
//        log.info("用户密码认证成功: userId={}, username={}", user.getId(), username);
//        return AuthenticationResult.success(success);
        return Optional.empty();
    }
}
