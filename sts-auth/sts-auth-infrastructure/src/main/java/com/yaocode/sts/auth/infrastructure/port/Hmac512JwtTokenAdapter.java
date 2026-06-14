package com.yaocode.sts.auth.infrastructure.port;

import com.yaocode.sts.auth.domain.enums.TokenTypeEnums;
import com.yaocode.sts.auth.domain.port.JwtTokenPort;
import com.yaocode.sts.auth.domain.valueobjects.composites.JwtPayload;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ClientId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.DeviceId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.TokenId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.Username;
import com.yaocode.sts.common.crypto.enums.AlgorithmTypeEnums;
import com.yaocode.sts.common.crypto.utils.JwtTokenUtils;
import com.yaocode.sts.common.domain.valueobject.UserId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * HMAC-SHA512 JWT 适配器
 * <p>
 * 实现 JwtTokenPort 接口，使用 HMAC-SHA512 算法进行 JWT 操作
 * </p>
 *
 * @author yaocode
 * @since 0.0.1
 */
public class Hmac512JwtTokenAdapter implements JwtTokenPort {

    private static final Logger logger = LoggerFactory.getLogger(Hmac512JwtTokenAdapter.class);

    /**
     * JWT 标准字段常量
     */
    private static final String CLAIM_ISS = "iss";
    private static final String CLAIM_SUB = "sub";
    private static final String CLAIM_AUD = "aud";
    private static final String CLAIM_IAT = "iat";
    private static final String CLAIM_EXP = "exp";
    private static final String CLAIM_JTI = "jti";

    /**
     * 自定义字段常量
     */
    private static final String CLAIM_USER_ID = "userId";
    private static final String CLAIM_USERNAME = "username";
    private static final String CLAIM_CLIENT_ID = "clientId";
    private static final String CLAIM_DEVICE_ID = "deviceId";
    private static final String CLAIM_SERIES = "series";
    private static final String CLAIM_TOKEN_TYPE = "tokenType";

    private final AlgorithmTypeEnums algorithm;

    private final String secret;

    private final Long ttlSeconds;

    private final String issuer;

    private final String audience;

    private final TokenTypeEnums tokenType;

    public Hmac512JwtTokenAdapter(
            AlgorithmTypeEnums algorithm,
            String secret,
            Long ttlSeconds,
            String issuer,
            String audience,
            TokenTypeEnums tokenType
    ) {
        this.algorithm = algorithm;
        this.secret = secret;
        this.ttlSeconds = ttlSeconds != null ? ttlSeconds : 3600L;
        this.issuer = issuer != null ? issuer : "sts";
        this.audience = audience != null ? audience : "sts-api";
        this.tokenType = tokenType;
        logger.info("Hmac512JwtAdapter initialized with issuer: {}, audience: {}", issuer, audience);
    }

    @Override
    public String generate(Map<String, Object> payload) {
        // 参数校验
        if (payload == null) {
            throw new IllegalArgumentException("payload 不能为空");
        }
        if (secret == null || secret.isBlank()) {
            throw new IllegalArgumentException("JWT secret 未配置");
        }

        // 使用默认 TTL 如果未指定
        long effectiveTtl = ttlSeconds;

        try {
            // 构建完整的 JWT 载荷
            Map<String, Object> jwtPayload = new HashMap<>(payload);

            // 添加标准 JWT 字段
            Instant now = Instant.now();
            jwtPayload.put(CLAIM_IAT, now.getEpochSecond());
            jwtPayload.put(CLAIM_EXP, now.plusSeconds(effectiveTtl).getEpochSecond());
            jwtPayload.put(CLAIM_ISS, issuer);
            jwtPayload.put(CLAIM_AUD, audience);
            jwtPayload.put("token_type", tokenType.getTokenType());

            // 如果没有 jti，自动生成一个
            if (!jwtPayload.containsKey(CLAIM_JTI)) {
                jwtPayload.put(CLAIM_JTI, TokenId.nextId().getValue());
            }

            // 生成 JWT
            String jwt = JwtTokenUtils.generateTokenWithHmac512(jwtPayload, secret);
            logger.debug("JWT generated successfully, ttl: {}s", effectiveTtl);
            return jwt;
        } catch (Exception e) {
            logger.error("JWT generation failed", e);
            throw new RuntimeException("JWT generation failed", e);
        }
    }

    @Override
    public boolean verify(String jwt) {
        // 参数校验
        if (jwt == null || jwt.isBlank()) {
            logger.debug("JWT is null or blank");
            return false;
        }
        if (secret == null || secret.isBlank()) {
            logger.error("JWT secret 未配置");
            return false;
        }

        try {
            // 1. 验证签名
            if (!JwtTokenUtils.verifyHmac512Token(jwt, secret)) {
                logger.debug("JWT signature verification failed");
                return false;
            }

            // 2. 检查是否过期
            Map<String, Object> claims = JwtTokenUtils.parseHmac512Token(jwt);
            if (claims != null) {
                Object expObj = claims.get(CLAIM_EXP);
                if (expObj != null) {
                    long expTime = parseLong(expObj);
                    if (Instant.now().getEpochSecond() > expTime) {
                        logger.debug("JWT has expired");
                        return false;
                    }
                }
            }

            return true;
        } catch (Exception e) {
            logger.error("JWT verification failed", e);
            return false;
        }
    }

    @Override
    public JwtPayload parse(String jwt) {
        try {
            if (!verify(jwt)) {
                return null;
            }
            Map<String, Object> claims = JwtTokenUtils.parseHmac512Token(jwt);
            if (claims == null) {
                return null;
            }
            return convertToJwtPayload(claims);
        } catch (Exception e) {
            logger.error("JWT parse failed", e);
            return null;
        }
    }

    // ==================== 私有辅助方法 ====================

    /**
     * 将 Map 类型的 claims 转换为 JwtPayload 对象
     *
     * @param claims JWT 载荷 Map
     * @return JwtPayload 对象
     */
    private JwtPayload convertToJwtPayload(Map<String, Object> claims) {
        if (claims == null) {
            return null;
        }

        try {
            // 解析标准字段
            String jti = getString(claims, CLAIM_JTI);
            String iss = getString(claims, CLAIM_ISS);
            String aud = getString(claims, CLAIM_AUD);
            Instant iat = parseInstant(claims, CLAIM_IAT);
            Instant exp = parseInstant(claims, CLAIM_EXP);

            // 解析自定义字段
            String userId = getString(claims, CLAIM_USER_ID);
            String username = getString(claims, CLAIM_USERNAME);
            String clientId = getString(claims, CLAIM_CLIENT_ID);
            String deviceId = getString(claims, CLAIM_DEVICE_ID);
            String tokenType = getString(claims, CLAIM_TOKEN_TYPE);
            String series = getString(claims, CLAIM_SERIES);

            // 构建额外的 claims（移除已解析的字段）
            Map<String, Object> extraClaims = buildExtraClaims(claims);

            // 创建值对象
            TokenId tokenId = jti != null ? TokenId.of(jti) : TokenId.nextId();
            UserId user = userId != null ? UserId.of(userId) : null;
            Username userUsername = username != null ? Username.of(username) : null;
            ClientId client = clientId != null ? ClientId.of(clientId) : null;
            DeviceId device = deviceId != null ? DeviceId.of(deviceId) : null;
            TokenTypeEnums type = tokenType != null ? TokenTypeEnums.valueOf(tokenType) : null;


            return new JwtPayload(
                    tokenId,
                    iat != null ? iat : Instant.now(),
                    exp,
                    iss != null ? iss : issuer,
                    aud != null ? aud : audience,
                    user,
                    userUsername,
                    client,
                    device,
                    type,
                    series,
                    extraClaims
            );
        } catch (Exception e) {
            logger.warn("Failed to convert claims to JwtPayload", e);
            return null;
        }
    }

    /**
         * 构建额外的 claims（移除已解析的标准字段和自定义字段）
         *
         * @param claims 原始 claims
         * @return 额外的 claims，如果为空则返回 null
         */
        private Map<String, Object> buildExtraClaims(Map<String, Object> claims) {
            Map<String, Object> extraClaims = new HashMap<>(claims);
            // 移除标准 JWT 字段
            extraClaims.remove(CLAIM_JTI);
            extraClaims.remove(CLAIM_ISS);
            extraClaims.remove(CLAIM_SUB);
            extraClaims.remove(CLAIM_AUD);
            extraClaims.remove(CLAIM_IAT);
            extraClaims.remove(CLAIM_EXP);
            // 移除此适配器处理的自定义字段
            extraClaims.remove(CLAIM_USER_ID);
            extraClaims.remove(CLAIM_USERNAME);
            extraClaims.remove(CLAIM_CLIENT_ID);
            extraClaims.remove(CLAIM_DEVICE_ID);
            extraClaims.remove(CLAIM_TOKEN_TYPE);
            // 如果没有额外字段，返回 null
            return extraClaims.isEmpty() ? null : extraClaims;
        }

        /**
         * 从 Map 中获取字符串值
         */
        private String getString(Map<String, Object> claims, String key) {
        Object value = claims.get(key);
        return value != null ? String.valueOf(value) : null;
    }

    /**
     * 从 Map 中解析 Instant（秒级时间戳）
     */
    private Instant parseInstant(Map<String, Object> claims, String key) {
        Object value = claims.get(key);
        if (value == null) {
            return null;
        }
        long timestamp = parseLong(value);
        return Instant.ofEpochSecond(timestamp);
    }

    /**
     * 安全地将对象转换为 long
     */
    private long parseLong(Object value) {
        if (value instanceof Number) {
            return ((Number) value).longValue();
        }
        if (value instanceof String) {
            return Long.parseLong((String) value);
        }
        return 0;
    }

    @Override
    public String getAlgorithm() {
        return this.algorithm.getDisplayName();
    }

}