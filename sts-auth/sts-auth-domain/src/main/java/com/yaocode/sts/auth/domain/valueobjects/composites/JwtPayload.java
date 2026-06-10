package com.yaocode.sts.auth.domain.valueobjects.composites;

import com.yaocode.sts.auth.domain.enums.TokenTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ClientId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.DeviceId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.TokenId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.Username;
import com.yaocode.sts.common.domain.valueobject.Identifier;
import com.yaocode.sts.common.domain.valueobject.UserId;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.time.Instant;
import java.util.Map;
import java.util.Objects;

@Value
@EqualsAndHashCode(callSuper = true)
public class JwtPayload extends Identifier<TokenId> {

    /**
     * 主体 (sub)
     * 通常存储 userId 或 username
     */
    String subject;

    /**
     * 签发时间 (iat)
     */
    Instant issuedAt;

    /**
     * 过期时间 (exp)
     */
    Instant expiresAt;

    /**
     * 签发者 (iss)
     */
    String issuer;

    /**
     * 受众 (aud)
     */
    String audience;

    /**
     * 用户ID（业务标识）
     */
    UserId userId;

    /**
     * 用户名（用于日志）
     */
    Username username;

    /**
     * 客户端ID
     */
    ClientId clientId;

    /**
     * 设备ID
     */
    DeviceId deviceId;

    /**
     * 令牌类型
     * ACCESS_TOKEN / REFRESH_TOKEN / REMEMBER_ME / STATE
     */
    TokenTypeEnums tokenType;

    /**
     * 其他扩展字段
     */
    Map<String, Object> claims;

    public JwtPayload(
            TokenId jwtId, Instant issuedAt, Instant expiresAt, String issuer,
            String audience, UserId userId, Username username, ClientId clientId,
            DeviceId deviceId, TokenTypeEnums tokenType, Map<String, Object> claims
    ) {
        super(jwtId);
        this.subject = Objects.isNull(userId) ? username.getValue() : userId.getValue();
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
        this.issuer = issuer;
        this.audience = audience;
        this.userId = userId;
        this.username = username;
        this.clientId = clientId;
        this.deviceId = deviceId;
        this.tokenType = tokenType;
        this.claims = claims;
    }

    /**
     * 检查是否过期
     */
    public boolean isExpired() {
        return expiresAt != null && Instant.now().isAfter(expiresAt);
    }

    /**
     * 检查是否在有效期内
     */
    public boolean isValid() {
        return !isExpired();
    }

}
