package com.yaocode.sts.auth.domain.entity;

import com.yaocode.sts.auth.domain.enums.TokenRevokeReasonEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ClientId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.DeviceId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.TokenId;
import com.yaocode.sts.common.basic.enums.OppositeEnums;
import com.yaocode.sts.common.domain.valueobject.UserId;
import lombok.Getter;

import java.time.Instant;
import java.util.Objects;

/**
 * Refresh Token 实体（独立实体，非聚合根）
 * 对应表：auth_tbl_refresh_token
 */
@Getter
public class RefreshTokenEntity {

    private final TokenId tokenId;
    private final UserId userId;
    private final ClientId clientId;
    private final DeviceId deviceId;
    private final String jti;                // JWT ID
    private final String tokenHash;          // Token哈希值
    private final Instant createdAt;
    private final Instant expiresAt;
    private OppositeEnums revoked;
    private Instant revokedAt;
    private TokenRevokeReasonEnums revokedReason;
    private String replacedBy;               // 被哪个新Token替换
    private Instant lastUsedAt;
    private Integer useCount;

    private RefreshTokenEntity(Builder builder) {
        this.tokenId = builder.tokenId;
        this.userId = builder.userId;
        this.clientId = builder.clientId;
        this.deviceId = builder.deviceId;
        this.jti = builder.jti;
        this.tokenHash = builder.tokenHash;
        this.createdAt = builder.createdAt;
        this.expiresAt = builder.expiresAt;
        this.revoked = builder.revoked;
        this.revokedAt = builder.revokedAt;
        this.revokedReason = builder.revokedReason;
        this.replacedBy = builder.replacedBy;
        this.lastUsedAt = builder.lastUsedAt;
        this.useCount = builder.useCount;
    }

    // ========== 工厂方法 ==========

    public static RefreshTokenEntity create(
            UserId userId,
            ClientId clientId,
            DeviceId deviceId,
            String jti,
            String tokenHash,
            Instant expiresAt
    ) {
        return new Builder()
                .tokenId(TokenId.nextId())
                .userId(userId)
                .clientId(clientId)
                .deviceId(deviceId)
                .jti(jti)
                .tokenHash(tokenHash)
                .createdAt(Instant.now())
                .expiresAt(expiresAt)
                .revoked(OppositeEnums.NO)
                .lastUsedAt(Instant.now())
                .useCount(0)
                .build();
    }

    public static RefreshTokenEntity reconstruct(
            TokenId tokenId,
            UserId userId,
            ClientId clientId,
            DeviceId deviceId,
            String jti,
            String tokenHash,
            Instant createdAt,
            Instant expiresAt,
            OppositeEnums revoked,
            Instant revokedAt,
            TokenRevokeReasonEnums revokedReason,
            String replacedBy,
            Instant lastUsedAt,
            Integer useCount
    ) {
        return new Builder()
                .tokenId(tokenId)
                .userId(userId)
                .clientId(clientId)
                .deviceId(deviceId)
                .jti(jti)
                .tokenHash(tokenHash)
                .createdAt(createdAt)
                .expiresAt(expiresAt)
                .revoked(revoked)
                .revokedAt(revokedAt)
                .revokedReason(revokedReason)
                .replacedBy(replacedBy)
                .lastUsedAt(lastUsedAt)
                .useCount(useCount)
                .build();
    }

    // ========== 业务行为 ==========

    public void revoke(TokenRevokeReasonEnums reason) {
        this.revoked = OppositeEnums.YES;
        this.revokedAt = Instant.now();
        this.revokedReason = reason;
    }

    public void revoke(TokenRevokeReasonEnums reason, String replacedByTokenId) {
        this.revoked = OppositeEnums.YES;
        this.revokedAt = Instant.now();
        this.revokedReason = reason;
        this.replacedBy = replacedByTokenId;
    }

    public void use() {
        this.lastUsedAt = Instant.now();
        this.useCount = (this.useCount == null ? 0 : this.useCount) + 1;
    }

    public boolean isExpired() {
        return Instant.now().isAfter(expiresAt);
    }

    public boolean isValid() {
        return !Objects.equals(revoked, OppositeEnums.YES) && !isExpired();
    }

    // ========== Builder ==========

    public static class Builder {
        private TokenId tokenId;
        private UserId userId;
        private ClientId clientId;
        private DeviceId deviceId;
        private String jti;
        private String tokenHash;
        private Instant createdAt;
        private Instant expiresAt;
        private OppositeEnums revoked;
        private Instant revokedAt;
        private TokenRevokeReasonEnums revokedReason;
        private String replacedBy;
        private Instant lastUsedAt;
        private Integer useCount;

        public Builder tokenId(TokenId tokenId) {
            this.tokenId = tokenId;
            return this;
        }

        public Builder userId(UserId userId) {
            this.userId = userId;
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

        public Builder jti(String jti) {
            this.jti = jti;
            return this;
        }

        public Builder tokenHash(String tokenHash) {
            this.tokenHash = tokenHash;
            return this;
        }

        public Builder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
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

        public Builder revokedAt(Instant revokedAt) {
            this.revokedAt = revokedAt;
            return this;
        }

        public Builder revokedReason(TokenRevokeReasonEnums revokedReason) {
            this.revokedReason = revokedReason;
            return this;
        }

        public Builder replacedBy(String replacedBy) {
            this.replacedBy = replacedBy;
            return this;
        }

        public Builder lastUsedAt(Instant lastUsedAt) {
            this.lastUsedAt = lastUsedAt;
            return this;
        }

        public Builder useCount(Integer useCount) {
            this.useCount = useCount;
            return this;
        }

        public RefreshTokenEntity build() {
            return new RefreshTokenEntity(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RefreshTokenEntity that = (RefreshTokenEntity) o;
        return Objects.equals(tokenId, that.tokenId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tokenId);
    }
}