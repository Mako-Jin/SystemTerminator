package com.yaocode.sts.auth.domain.service.provider;

import com.yaocode.sts.auth.domain.entity.RefreshTokenEntity;
import com.yaocode.sts.auth.domain.entity.RememberMeTokenEntity;
import com.yaocode.sts.auth.domain.entity.UserInfoEntity;
import com.yaocode.sts.auth.domain.port.JwtTokenConfigPort;
import com.yaocode.sts.auth.domain.repository.RefreshTokenRepository;
import com.yaocode.sts.auth.domain.repository.RememberMeTokenRepository;
import com.yaocode.sts.auth.domain.service.JwtTokenService;
import com.yaocode.sts.auth.domain.constants.AuthI18nKeyConstants;
import com.yaocode.sts.auth.domain.valueobjects.AbstractAuthCredential;
import com.yaocode.sts.auth.domain.valueobjects.composites.AuthenticationToken;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.TokenId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.IpAddress;
import com.yaocode.sts.common.basic.constants.SymbolConstants;
import com.yaocode.sts.common.basic.enums.EnableEnums;
import com.yaocode.sts.common.tools.id.IdFactory;
import com.yaocode.sts.common.tools.id.IdGeneratorType;
import com.yaocode.sts.common.web.context.RequestContextHolder;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    protected final JwtTokenService jwtTokenService;

    protected RefreshTokenRepository refreshTokenRepository;

    protected RememberMeTokenRepository rememberMeRepository;

    protected JwtTokenConfigPort jwtTokenConfigPort;

    protected AbstractAuthenticationProvider(
            JwtTokenService jwtTokenService,
            JwtTokenConfigPort jwtTokenConfigPort,
            RefreshTokenRepository refreshTokenRepository,
            RememberMeTokenRepository rememberMeRepository
    ) {
        this.jwtTokenService = jwtTokenService;
        this.refreshTokenRepository = refreshTokenRepository;
        this.rememberMeRepository = rememberMeRepository;
        this.jwtTokenConfigPort = jwtTokenConfigPort;
    }

    @Override
    public AuthenticationToken authenticate(T credential) {
        // 1. 前置检查
        preAuthenticateCheck(credential);

        // 2. 执行具体认证逻辑
        Optional<UserInfoEntity> userInfoEntityOptional = doAuthenticate(credential);
        if (userInfoEntityOptional.isEmpty()) {
            throw new IllegalStateException(AuthI18nKeyConstants.AUTHENTICATION_FAILED_USER_NOT_FOUND);
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
            throw new IllegalArgumentException(AuthI18nKeyConstants.AUTH_CREDENTIAL_CANNOT_BE_BLANK);
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
            throw new IllegalStateException(AuthI18nKeyConstants.AUTHENTICATION_FAILED_USER_NOT_FOUND);
        }
        validateUserStatus(userInfoEntity);
    }

    /**
     * 验证用户状态（子类可扩展）
     */
    protected void validateUserStatus(UserInfoEntity userInfoEntity) {
        if (Objects.equals(userInfoEntity.getEnabled(), EnableEnums.DISABLED)) {
            throw new IllegalStateException(AuthI18nKeyConstants.USER_ALREADY_DISABLED);
        }
//        if (userInfoEntity.get()) {
//            throw new IllegalStateException("用户已被锁定");
//        }
    }

    /**
     * 构建成功令牌
     */
    protected AuthenticationToken buildSuccessToken(UserInfoEntity userInfoEntity, T  credential) {
        Instant accessTokenExpiresAt = Instant.now().plusSeconds(jwtTokenConfigPort.getAccessTokenTtl());
        String accessToken = jwtTokenService.generateAccessToken(userInfoEntity, credential.getClientId(), credential.getDeviceId());
        Instant refreshTokenExpiresAt = Instant.now().plusSeconds(jwtTokenConfigPort.getRefreshTokenTtl());
        String refreshToken = generateAndSaveRefreshToken(userInfoEntity, credential, refreshTokenExpiresAt);
        Instant rememberMeTokenExpiresAt = Instant.now().plusSeconds(jwtTokenConfigPort.getRememberMeTtl());
        String rememberMeToken = generateAndSaveRememberMeToken(userInfoEntity, credential, rememberMeTokenExpiresAt);
        return AuthenticationToken.builder()
                .userId(userInfoEntity.getId())
                .grantType(credential.getGrantType().getValue())
                .accessToken(accessToken)
                .accessTokenExpiresAt(accessTokenExpiresAt)
                .refreshToken(refreshToken)
                .refreshTokenExpiresAt(refreshTokenExpiresAt)
                .rememberMeToken(rememberMeToken)
                .rememberMeTokenExpiresAt(rememberMeTokenExpiresAt)
                .isAuthenticated(true)
                .build();
    }

    /**
     * 生成并保存 RememberMe Token
     */
    protected String generateAndSaveRememberMeToken(UserInfoEntity userInfoEntity, T credential, Instant expiresAt) {
        if (!credential.isRememberMe()) {
            return SymbolConstants.EMPTY_STR;
        }

        String series = IdFactory.generate(IdGeneratorType.UUID);
        String token = jwtTokenService.generateRememberMeToken(userInfoEntity, credential.getClientId(), credential.getDeviceId(), series);

        RememberMeTokenEntity entity = RememberMeTokenEntity.create(
                userInfoEntity.getId(),
                userInfoEntity.getUsername(),
                credential.getClientId(),
                credential.getDeviceId(),
                token,
                series,
                expiresAt,
                IpAddress.of(RequestContextHolder.getIpAddress()),
                RequestContextHolder.getUserAgent()
        );
        rememberMeRepository.save(entity);

        logger.debug("RememberMe saved to database: id={}, userId={}", entity.getTokenId(), userInfoEntity.getId());

        // 3. 生成 JWT
        return token;
    }

    /**
     * 生成并保存 Refresh Token
     */
    protected String generateAndSaveRefreshToken(UserInfoEntity userInfoEntity, T credential, Instant expiresAt) {
        if (!credential.needRefreshToken()) {
            return SymbolConstants.EMPTY_STR;
        }

        TokenId tokenId = TokenId.nextId();
        String jti = IdFactory.generate(IdGeneratorType.UUID).toString();
        String token = jwtTokenService.generateRefreshToken(userInfoEntity, credential.getClientId(), credential.getDeviceId(), tokenId, jti);
        RefreshTokenEntity entity = RefreshTokenEntity.create(
                tokenId,
                userInfoEntity.getId(),
                credential.getClientId(),
                credential.getDeviceId(),
                jti,
                token,
                expiresAt
        );
        refreshTokenRepository.save(entity);

        logger.debug("RefreshToken saved to database: id={}, userId={}", entity.getTokenId(), userInfoEntity.getId());

        // 3. 生成 JWT
        return token;
    }

    /**
     * 记录认证成功日志
     */
    protected void logAuthenticationSuccess(UserInfoEntity user) {
        logger.info("认证成功: userId={}", user.getId());
    }

}