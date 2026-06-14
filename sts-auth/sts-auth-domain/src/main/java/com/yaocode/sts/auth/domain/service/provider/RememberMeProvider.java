package com.yaocode.sts.auth.domain.service.provider;

import com.yaocode.sts.auth.domain.entity.RememberMeTokenEntity;
import com.yaocode.sts.auth.domain.entity.UserInfoEntity;
import com.yaocode.sts.auth.domain.enums.GrantTypeEnums;
import com.yaocode.sts.auth.domain.port.JwtTokenPort;
import com.yaocode.sts.auth.domain.repository.RefreshTokenRepository;
import com.yaocode.sts.auth.domain.repository.RememberMeRepository;
import com.yaocode.sts.auth.domain.repository.UserInfoRepository;
import com.yaocode.sts.auth.domain.service.JwtTokenService;
import com.yaocode.sts.auth.domain.valueobjects.AbstractAuthCredential;
import com.yaocode.sts.auth.domain.valueobjects.composites.JwtPayload;
import com.yaocode.sts.auth.domain.valueobjects.composites.RememberMeAuthCredential;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ClientId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.DeviceId;
import com.yaocode.sts.common.basic.exception.ParamCheckException;
import com.yaocode.sts.common.domain.valueobject.UserId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Remember-Me 认证提供者
 * <p>
 * 支持根据配置选择不同的 JWT 加密算法，默认使用 HMAC-SHA512
 * </p>
 *
 * @author yaocode
 * @since 0.0.1
 */
public class RememberMeProvider extends AbstractAuthenticationProvider<RememberMeAuthCredential> {

    private static final Logger logger = LoggerFactory.getLogger(RememberMeProvider.class);

    /**
     * JWT Token 端口（默认使用 HMAC-SHA512 实现）
     * <p>
     * 可通过配置选择不同的 JWT 实现：
     * - HMAC-SHA512: @Qualifier("hmac512JwtAdapter")
     * - 其他算法: 实现 JwtTokenPort 接口并使用相应的 @Qualifier
     * </p>
     */
    private final JwtTokenPort jwtTokenPort;

    /**
     * 用户信息仓储
     */
    private final UserInfoRepository userInfoRepository;

    /**
     * 构造函数注入（支持根据配置选择不同的 JWT 实现）
     *
     * @param jwtTokenPort         JWT Token 端口实现
     * @param rememberMeRepository Remember-Me 令牌仓储
     * @param userInfoRepository   用户信息仓储
     */
    public RememberMeProvider(
            JwtTokenService jwtTokenService,
            JwtTokenPort jwtTokenPort,
            RememberMeRepository rememberMeRepository,
            RefreshTokenRepository refreshTokenRepository,
            UserInfoRepository userInfoRepository
    ) {
        super(jwtTokenService, refreshTokenRepository, rememberMeRepository);
        this.jwtTokenPort = jwtTokenPort;
        this.userInfoRepository = userInfoRepository;
        logger.info("RememberMeProvider initialized with JWT algorithm: HMAC-SHA512");
    }

    @Override
    public GrantTypeEnums getGrantType() {
        return GrantTypeEnums.REMEMBER_ME;
    }

    @Override
    public boolean supports(AbstractAuthCredential credential) {
        return credential instanceof RememberMeAuthCredential;
    }

    @Override
    protected Optional<UserInfoEntity> doAuthenticate(RememberMeAuthCredential credential) {
        // 1. 参数校验
        if (credential == null || credential.getRememberMeToken() == null || credential.getRememberMeToken().isBlank()) {
            logger.debug("Remember-me authentication failed: token is null or blank");
            throw new ParamCheckException("Remember-me authentication failed: token is null or blank");
        }

        try {
            // 2. 验证并解析 Remember-Me Token
            JwtPayload rememberMePayload = jwtTokenPort.parse(credential.getRememberMeToken());
            if (rememberMePayload == null) {
                logger.debug("Remember-me authentication failed: invalid token");
                throw new IllegalArgumentException("Remember-me authentication failed: invalid token");
            }

            // 3. 检查 Token 是否过期
            if (!rememberMePayload.isValid()) {
                logger.debug("Remember-me authentication failed: token has expired");
                throw new IllegalArgumentException("Remember-me authentication failed: token has expired");
            }

            // 4. 获取用户 ID
            DeviceId deviceId = rememberMePayload.getDeviceId();
            ClientId clientId = rememberMePayload.getClientId();
            String series = rememberMePayload.getSeries();
            UserId userId = rememberMePayload.getUserId();
            if (userId == null) {
                logger.debug("Remember-me authentication failed: user ID is null");
                throw new IllegalArgumentException("Remember-me authentication failed: invalid token");
            }

            // 5. 验证数据库中的 Remember-Me 记录
            Optional<RememberMeTokenEntity> tokenEntityOpt = rememberMeRepository.findRememberMeToken(clientId, deviceId, userId);
            if (tokenEntityOpt.isEmpty()) {
                logger.debug("Remember-me authentication failed: no token record found for user {}", userId);
                return Optional.empty();
            }

            RememberMeTokenEntity tokenEntity = tokenEntityOpt.get();

            // 6. 检查 Token 是否已被撤销
            if (tokenEntity.isRevoked()) {
                logger.debug("Remember-me authentication failed: token has been revoked");
                throw new IllegalArgumentException("Remember-me authentication failed: token has been revoked");
            }

            // 7. 检查 series 是否匹配（防止盗用）
            if (series != null && !series.equals(tokenEntity.getSeries())) {
                logger.warn("Remember-me authentication failed: series mismatch, possible token theft for user {}", userId);
                tokenEntity.revoke("SERIES_MISMATCH");
                rememberMeRepository.save(tokenEntity);
                return Optional.empty();
            }

            // 7. 根据用户 ID 查询用户信息
            Optional<UserInfoEntity> userOpt = userInfoRepository.findById(userId);
            if (userOpt.isEmpty()) {
                logger.debug("Remember-me authentication failed: user not found");
                throw new IllegalArgumentException("Remember-me authentication failed: user not found");
            }

            // 8. 更新最后使用时间
            RememberMeTokenEntity newTokenEntity = tokenEntity.renew(30);
            rememberMeRepository.save(newTokenEntity);

            logger.debug("Remember-me authentication succeeded for user {}", userId);
            return userOpt;
        } catch (Exception e) {
            logger.error("Remember-me authentication failed due to exception", e);
            throw new IllegalArgumentException("Remember-me authentication failed: user not found", e);
        }
    }
}