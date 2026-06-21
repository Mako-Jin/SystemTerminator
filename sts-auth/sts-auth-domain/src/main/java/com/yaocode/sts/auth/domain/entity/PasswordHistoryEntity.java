package com.yaocode.sts.auth.domain.entity;

import com.yaocode.sts.auth.domain.enums.PasswordChangeSourceEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.PasswordHistoryId;
import com.yaocode.sts.common.basic.enums.OppositeEnums;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import com.yaocode.sts.common.domain.valueobject.UserId;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 密码历史实体（属于用户聚合）
 * 对应表：auth_tbl_password_history
 */
@Getter
public class PasswordHistoryEntity {

    private final PasswordHistoryId passwordHistoryId;
    private final UserId userId;
    private final TenantId tenantId;
    private final String passwordHash;
    private final LocalDateTime changeTime;
    private final PasswordChangeSourceEnums changeSource;
    private final OppositeEnums isActive;
    private final LocalDateTime expiresAt;

    private PasswordHistoryEntity(Builder builder) {
        this.passwordHistoryId = builder.passwordHistoryId;
        this.userId = builder.userId;
        this.tenantId = builder.tenantId;
        this.passwordHash = builder.passwordHash;
        this.changeTime = builder.changeTime;
        this.changeSource = builder.changeSource;
        this.isActive = builder.isActive;
        this.expiresAt = builder.expiresAt;
    }

    // ========== 工厂方法 ==========

    public static PasswordHistoryEntity create(
            UserId userId,
            TenantId tenantId,
            String passwordHash,
            PasswordChangeSourceEnums changeSource,
            LocalDateTime expiresAt
    ) {
        return new Builder()
                .passwordHistoryId(PasswordHistoryId.nextId())
                .userId(userId)
                .tenantId(tenantId)
                .passwordHash(passwordHash)
                .changeTime(LocalDateTime.now())
                .changeSource(changeSource)
                .isActive(OppositeEnums.YES)
                .expiresAt(expiresAt)
                .build();
    }

    public static PasswordHistoryEntity reconstruct(
            PasswordHistoryId passwordHistoryId,
            UserId userId,
            TenantId tenantId,
            String passwordHash,
            LocalDateTime changeTime,
            PasswordChangeSourceEnums changeSource,
            OppositeEnums isActive,
            LocalDateTime expiresAt
    ) {
        return new Builder()
                .passwordHistoryId(passwordHistoryId)
                .userId(userId)
                .tenantId(tenantId)
                .passwordHash(passwordHash)
                .changeTime(changeTime)
                .changeSource(changeSource)
                .isActive(isActive)
                .expiresAt(expiresAt)
                .build();
    }

    // ========== 业务行为 ==========

    public boolean isExpired() {
        return expiresAt != null && LocalDateTime.now().isAfter(expiresAt);
    }

    public boolean isEffective() {
        return Objects.equals(isActive, OppositeEnums.YES) && !isExpired();
    }

    // ========== Builder ==========

    public static class Builder {
        private PasswordHistoryId passwordHistoryId;
        private UserId userId;
        private TenantId tenantId;
        private String passwordHash;
        private LocalDateTime changeTime;
        private PasswordChangeSourceEnums changeSource;
        private OppositeEnums isActive;
        private LocalDateTime expiresAt;

        public Builder passwordHistoryId(PasswordHistoryId passwordHistoryId) {
            this.passwordHistoryId = passwordHistoryId;
            return this;
        }

        public Builder userId(UserId userId) {
            this.userId = userId;
            return this;
        }

        public Builder tenantId(TenantId tenantId) {
            this.tenantId = tenantId;
            return this;
        }

        public Builder passwordHash(String passwordHash) {
            this.passwordHash = passwordHash;
            return this;
        }

        public Builder changeTime(LocalDateTime changeTime) {
            this.changeTime = changeTime;
            return this;
        }

        public Builder changeSource(PasswordChangeSourceEnums changeSource) {
            this.changeSource = changeSource;
            return this;
        }

        public Builder isActive(OppositeEnums isActive) {
            this.isActive = isActive;
            return this;
        }

        public Builder expiresAt(LocalDateTime expiresAt) {
            this.expiresAt = expiresAt;
            return this;
        }

        public PasswordHistoryEntity build() {
            return new PasswordHistoryEntity(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PasswordHistoryEntity that = (PasswordHistoryEntity) o;
        return Objects.equals(passwordHistoryId, that.passwordHistoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(passwordHistoryId);
    }
}
