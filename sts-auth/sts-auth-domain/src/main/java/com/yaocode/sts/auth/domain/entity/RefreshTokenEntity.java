package com.yaocode.sts.auth.domain.entity;

import com.yaocode.sts.auth.domain.valueobjects.identifiers.ClientId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.DeviceId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.TokenId;
import com.yaocode.sts.common.domain.model.AbstractAggregate;
import com.yaocode.sts.common.domain.valueobject.UserId;
import lombok.Getter;

import java.time.Instant;

/**
 * Refresh Token 实体
 */
@Getter
public class RefreshTokenEntity extends AbstractAggregate<TokenId> {

    private final UserId userId;
    private final ClientId clientId;
    private final DeviceId deviceId;
    boolean revoked;        // 是否已撤销
    Instant revokedAt;
    String revokedReason;   // LOGOUT, PASSWORD_CHANGE, REPLACED
    String replacedBy;      // 被哪个新Token替换（刷新时使用）
    private final Instant createdAt;
    private final Instant expiresAt;
    private final Instant lastUsedAt;

    public RefreshTokenEntity(
            TokenId tokenId, UserId userId, ClientId clientId, DeviceId deviceId,
            Instant expiresAt, Instant lastUsedAt
    ) {
        super(tokenId);
        this.userId = userId;
        this.clientId = clientId;
        this.deviceId = deviceId;
        this.createdAt = Instant.now();
        this.expiresAt = expiresAt;
        this.lastUsedAt = lastUsedAt;
    }

    public RefreshTokenEntity(
            UserId userId, ClientId clientId, DeviceId deviceId,
            Instant expiresAt, Instant lastUsedAt
    ) {
        this(TokenId.nextId(), userId, clientId, deviceId, expiresAt, lastUsedAt);
    }

    /**
     * 检查是否已过期
     */
    public boolean isExpired() {
        return Instant.now().isAfter(expiresAt);
    }

    /**
     * 检查是否有效（未撤销且未过期）
     */
    public boolean isValid() {
        return !revoked && !isExpired();
    }

    /**
     * 撤销 Token
     */
    public void revoke(String reason, String replacedByTokenId) {
        this.revoked = true;
        this.revokedAt = Instant.now();
        this.revokedReason = reason;
        this.replacedBy = replacedByTokenId;
    }
}
