package com.yaocode.sts.auth.domain.entity;

import com.yaocode.sts.auth.domain.enums.TokenRevokeReasonEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ClientId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.DeviceId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.TokenId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.IpAddress;
import com.yaocode.sts.auth.domain.valueobjects.primitives.Username;
import com.yaocode.sts.common.basic.enums.OppositeEnums;
import com.yaocode.sts.common.domain.valueobject.UserId;
import lombok.Getter;

import java.time.Instant;
import java.util.Objects;

/**
 * Remember Me Token 实体（独立实体，非聚合根）
 * 对应表：auth_tbl_remember_me_token
 */
@Getter
public class RememberMeTokenEntity {

    private final TokenId tokenId;
    private final UserId userId;
    private final Username username;
    private final ClientId clientId;
    private final DeviceId deviceId;
    private final String tokenHash;
    private final String series;
    private final Instant createdAt;
    private Instant lastUsedAt;
    private IpAddress lastUsedIp;
    private String lastUsedUserAgent;
    private Instant lastLoginTime;
    private Instant expiresAt;
    private OppositeEnums isRevoked;
    private TokenRevokeReasonEnums revokedReason;
    private Instant revokedAt;

    private RememberMeTokenEntity(Builder builder) {
        this.tokenId = builder.tokenId;
        this.userId = builder.userId;
        this.username = builder.username;
        this.clientId = builder.clientId;
        this.deviceId = builder.deviceId;
        this.tokenHash = builder.tokenHash;
        this.series = builder.series;
        this.createdAt = builder.createdAt;
        this.lastUsedAt = builder.lastUsedAt;
        this.lastUsedIp = builder.lastUsedIp;
        this.lastUsedUserAgent = builder.lastUsedUserAgent;
        this.lastLoginTime = builder.lastLoginTime;
        this.expiresAt = builder.expiresAt;
        this.isRevoked = builder.revoked;
        this.revokedReason = builder.revokedReason;
        this.revokedAt = builder.revokedAt;
    }

    // ========== 工厂方法 ==========

    public static RememberMeTokenEntity create(
            UserId userId,
            Username username,
            ClientId clientId,
            DeviceId deviceId,
            String tokenHash,
            String series,
            Instant expiresAt,
            IpAddress loginIp,
            String userAgent
    ) {
        Instant now = Instant.now();
        return new Builder()
                .tokenId(TokenId.nextId())
                .userId(userId)
                .username(username)
                .clientId(clientId)
                .deviceId(deviceId)
                .tokenHash(tokenHash)
                .series(series)
                .createdAt(now)
                .lastUsedAt(now)
                .lastUsedIp(loginIp)
                .lastUsedUserAgent(userAgent)
                .lastLoginTime(now)
                .expiresAt(expiresAt)
                .revoked(OppositeEnums.NO)
                .build();
    }

    public static RememberMeTokenEntity reconstruct(
            TokenId tokenId,
            UserId userId,
            Username username,
            ClientId clientId,
            DeviceId deviceId,
            String tokenHash,
            String series,
            Instant createdAt,
            Instant lastUsedAt,
            IpAddress lastUsedIp,
            String lastUsedUserAgent,
            Instant lastLoginTime,
            Instant expiresAt,
            OppositeEnums revoked,
            TokenRevokeReasonEnums revokedReason,
            Instant revokedAt
    ) {
        return new Builder()
                .tokenId(tokenId)
                .userId(userId)
                .username(username)
                .clientId(clientId)
                .deviceId(deviceId)
                .tokenHash(tokenHash)
                .series(series)
                .createdAt(createdAt)
                .lastUsedAt(lastUsedAt)
                .lastUsedIp(lastUsedIp)
                .lastUsedUserAgent(lastUsedUserAgent)
                .lastLoginTime(lastLoginTime)
                .expiresAt(expiresAt)
                .revoked(revoked)
                .revokedReason(revokedReason)
                .revokedAt(revokedAt)
                .build();
    }

    // ========== 业务行为 ==========

    public void use(IpAddress ipAddress, String userAgent) {
        Instant now = Instant.now();
        this.lastUsedAt = now;
        this.lastUsedIp = ipAddress;
        this.lastUsedUserAgent = userAgent;
        this.lastLoginTime = now;
    }

    public RememberMeTokenEntity renew(Instant newExpiresAt) {
        if (newExpiresAt.isBefore(Instant.now())) {
            throw new IllegalArgumentException("新的过期时间不能早于当前时间");
        }
        this.expiresAt = newExpiresAt;
        return this;
    }

    public void revoke(TokenRevokeReasonEnums reason) {
        this.isRevoked = OppositeEnums.YES;
        this.revokedAt = Instant.now();
        this.revokedReason = reason;
    }

    public boolean isValid() {
        return !Objects.equals(isRevoked, OppositeEnums.YES) && Instant.now().isBefore(expiresAt);
    }

    public boolean isExpired() {
        return Instant.now().isAfter(expiresAt);
    }

    // ========== Builder ==========

    public static class Builder {
        private TokenId tokenId;
        private UserId userId;
        private Username username;
        private ClientId clientId;
        private DeviceId deviceId;
        private String tokenHash;
        private String series;
        private Instant createdAt;
        private Instant lastUsedAt;
        private IpAddress lastUsedIp;
        private String lastUsedUserAgent;
        private Instant lastLoginTime;
        private Instant expiresAt;
        private OppositeEnums revoked;
        private TokenRevokeReasonEnums revokedReason;
        private Instant revokedAt;

        public Builder tokenId(TokenId tokenId) {
            this.tokenId = tokenId;
            return this;
        }

        public Builder userId(UserId userId) {
            this.userId = userId;
            return this;
        }

        public Builder username(Username username) {
            this.username = username;
            return this;
        }

        public Builder clientId(ClientId clientId) {
            this.clientId = clientId;
            return this;
        }

        public Builder deviceId(DeviceId deviceId) {
            this.deviceId = deviceId;
            return this;
        }

        public Builder tokenHash(String tokenHash) {
            this.tokenHash = tokenHash;
            return this;
        }

        public Builder series(String series) {
            this.series = series;
            return this;
        }

        public Builder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder lastUsedAt(Instant lastUsedAt) {
            this.lastUsedAt = lastUsedAt;
            return this;
        }

        public Builder lastUsedIp(IpAddress lastUsedIp) {
            this.lastUsedIp = lastUsedIp;
            return this;
        }

        public Builder lastUsedUserAgent(String lastUsedUserAgent) {
            this.lastUsedUserAgent = lastUsedUserAgent;
            return this;
        }

        public Builder lastLoginTime(Instant lastLoginTime) {
            this.lastLoginTime = lastLoginTime;
            return this;
        }

        public Builder expiresAt(Instant expiresAt) {
            this.expiresAt = expiresAt;
            return this;
        }

        public Builder revoked(OppositeEnums revoked) {
            this.revoked = revoked;
            return this;
        }

        public Builder revokedReason(TokenRevokeReasonEnums revokedReason) {
            this.revokedReason = revokedReason;
            return this;
        }

        public Builder revokedAt(Instant revokedAt) {
            this.revokedAt = revokedAt;
            return this;
        }

        public RememberMeTokenEntity build() {
            return new RememberMeTokenEntity(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RememberMeTokenEntity that = (RememberMeTokenEntity) o;
        return Objects.equals(tokenId, that.tokenId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tokenId);
    }
}