package com.yaocode.sts.auth.domain.service.provider;

import com.yaocode.sts.auth.domain.entity.RefreshTokenEntity;
import com.yaocode.sts.auth.domain.entity.RememberMeTokenEntity;
import com.yaocode.sts.auth.domain.entity.UserInfoEntity;
import com.yaocode.sts.auth.domain.repository.RefreshTokenRepository;
import com.yaocode.sts.auth.domain.repository.RememberMeRepository;
import com.yaocode.sts.auth.domain.service.JwtTokenService;
import com.yaocode.sts.auth.domain.valueobjects.AbstractAuthCredential;
import com.yaocode.sts.auth.domain.valueobjects.composites.AuthenticationToken;
import com.yaocode.sts.common.basic.enums.OppositeEnums;
import com.yaocode.sts.common.tools.id.IdFactory;
import com.yaocode.sts.common.tools.id.IdGeneratorType;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

/**
 * provider抽象实现类
 * @author: Jin-LiangBo
 * @date: 2026年04月14日 10:52
 */
@Getter
public abstract class AbstractAuthenticationProvider<T extends AbstractAuthCredential> implements AuthenticationProvider<T> {

    private static final Logger logger = LoggerFactory.getLogger(AbstractAuthenticationProvider.class);

    private final JwtTokenService jwtTokenService;

    protected RefreshTokenRepository refreshTokenRepository;

    protected RememberMeRepository rememberMeRepository;

    @Value("${yaocode.jwt.remember-me.ttl:30*24*60*60}")
    protected long rememberMeTokenTtlSeconds;

    @Value("${yaocode.jwt.refresh.ttl:604800}")
    protected long refreshTokenTtlSeconds;

    protected AbstractAuthenticationProvider(
            JwtTokenService jwtTokenService,
            RefreshTokenRepository refreshTokenRepository,
            RememberMeRepository rememberMeRepository
    ) {
        this.jwtTokenService = jwtTokenService;
        this.refreshTokenRepository = refreshTokenRepository;
        this.rememberMeRepository = rememberMeRepository;
    }

    @Override
    public AuthenticationToken authenticate(T credential) {
        // 1. 前置检查
        preAuthenticateCheck(credential);

        // 2. 执行具体认证逻辑
        Optional<UserInfoEntity> userInfoEntityOptional = doAuthenticate(credential);
        if (userInfoEntityOptional.isEmpty()) {
            throw new IllegalStateException("认证失败：用户不存在");
        }
        // 3. 后置验证
        postAuthenticateValidate(userInfoEntityOptional.get());

        // 4. 构建认证成功的令牌
        AuthenticationToken result = buildSuccessToken(userInfoEntityOptional.get(), credential);

        // 5. 记录认证日志
        logAuthenticationSuccess(userInfoEntityOptional.get());

        return result;
    }

    /**
     * 前置检查
     */
    protected void preAuthenticateCheck(T credential) {
        if (Objects.isNull(credential)) {
            throw new IllegalArgumentException("认证令牌不能为空");
        }
    }

    /**
     * 执行具体认证逻辑（子类实现）
     */
    protected abstract Optional<UserInfoEntity> doAuthenticate(T credential);

    /**
     * 后置验证
     */
    protected void postAuthenticateValidate(UserInfoEntity userInfoEntity) {
        if (Objects.isNull(userInfoEntity)) {
            throw new IllegalStateException("认证失败：用户不存在");
        }
        validateUserStatus(userInfoEntity);
    }

    /**
     * 验证用户状态（子类可扩展）
     */
    protected void validateUserStatus(UserInfoEntity userInfoEntity) {
        if (Objects.equals(userInfoEntity.getIsEnabled(), OppositeEnums.DISABLED.getCode())) {
            throw new IllegalStateException("用户已被禁用");
        }
//        if (userInfoEntity.get()) {
//            throw new IllegalStateException("用户已被锁定");
//        }
    }

    /**
     * 构建成功令牌
     */
    protected AuthenticationToken buildSuccessToken(UserInfoEntity userInfoEntity, T  credential) {
        String accessToken = jwtTokenService.generateAccessToken(userInfoEntity, credential.getClientId(), credential.getDeviceId());
        String refreshToken = generateAndSaveRefreshToken(userInfoEntity, credential);
        String rememberMeToken = generateAndSaveRememberMeToken(userInfoEntity, credential);
        return AuthenticationToken.builder()
                .userId(userInfoEntity.getId())
                .grantType(credential.getGrantType().getValue())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .rememberMeToken(rememberMeToken)
                .authenticated(true)
                .build();
    }

    /**
     * 生成并保存 RememberMe Token
     */
    protected String generateAndSaveRememberMeToken(UserInfoEntity userInfoEntity, T credential) {
        if (!credential.isRememberMe()) {
            return "";
        }

        Instant expiresAt = Instant.now().plusSeconds(rememberMeTokenTtlSeconds);

        RememberMeTokenEntity entity = new RememberMeTokenEntity(
                userInfoEntity.getId(),
                userInfoEntity.getUsername(),
                credential.getClientId(),
                credential.getDeviceId(),
                Instant.now(),
                expiresAt,
                IdFactory.generate(IdGeneratorType.UUID)
        );
        rememberMeRepository.save(entity);

        logger.debug("RememberMe saved to database: id={}, userId={}", entity.getId(), userInfoEntity.getId());

        // 3. 生成 JWT
        return jwtTokenService.generateRememberMeToken(userInfoEntity, credential.getClientId(), credential.getDeviceId(), entity.getSeries());
    }

    /**
     * 生成并保存 Refresh Token
     */
    protected String generateAndSaveRefreshToken(UserInfoEntity userInfoEntity, T credential) {
        if (!credential.needRefreshToken()) {
            return "";
        }

        Instant expiresAt = Instant.now().plusSeconds(refreshTokenTtlSeconds);

        RefreshTokenEntity entity = new RefreshTokenEntity(
                userInfoEntity.getId(),
                credential.getClientId(),
                credential.getDeviceId(),
                expiresAt,
                Instant.now()
        );
        refreshTokenRepository.save(entity);

        logger.debug("RefreshToken saved to database: id={}, userId={}", entity.getId(), userInfoEntity.getId());

        // 3. 生成 JWT
        return jwtTokenService.generateRefreshToken(userInfoEntity, credential.getClientId(), credential.getDeviceId(), entity.getId());
    }

    /**
     * 记录认证成功日志
     */
    protected void logAuthenticationSuccess(UserInfoEntity user) {
        logger.info("认证成功: userId={}", user.getId());
    }

}
