package com.yaocode.sts.auth.domain.entity;

import com.yaocode.sts.auth.domain.valueobjects.identifiers.ClientId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.DeviceId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.TokenId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.Username;
import com.yaocode.sts.common.domain.model.AbstractAggregate;
import com.yaocode.sts.common.domain.valueobject.UserId;
import lombok.Getter;

import java.time.Instant;

@Getter
public class RememberMeTokenEntity extends AbstractAggregate<TokenId> {

    /**
     * 关联的用户ID
     */
    private final UserId userId;
    /**
     * 关联的用户名
     */
    private final Username username;
    /**
     * 绑定的客户端ID
     */
    private final ClientId clientId;
    /**
     * 绑定的设备ID（可选）
     */
    private final DeviceId deviceId;
    /**
     * 创建时间
     */
    private final Instant createdAt;
    /**
     * 最后登录时间
     */
    private final Instant lastLoginTime;
    /**
     * 过期时间
     */
    private final Instant expirationTime;
    /**
     * 是否已被撤销
     */
    private boolean revoked;
    /**
     * 撤销时间
     */
    private Instant revokedAt;

    public RememberMeTokenEntity(
            TokenId tokenId,
            UserId userId,
            Username username,
            ClientId clientId,
            DeviceId deviceId,
            Instant lastLoginTime,
            Instant expirationTime
    ) {
        super(tokenId);
        this.userId = userId;
        this.username = username;
        this.clientId = clientId;
        this.deviceId = deviceId;
        this.createdAt = Instant.now();
        this.lastLoginTime = this.createdAt;
        this.expirationTime = expirationTime;
    }

    public RememberMeTokenEntity(
            UserId userId,
            Username username,
            ClientId clientId,
            DeviceId deviceId,
            Instant expirationTime
    ) {
        super(TokenId.nextId());
        this.userId = userId;
        this.username = username;
        this.clientId = clientId;
        this.deviceId = deviceId;
        this.createdAt = Instant.now();
        this.lastLoginTime = this.createdAt;
        this.expirationTime = expirationTime;
    }

    /**
     * 检查令牌是否有效
     */
    public boolean isValid() {
        return !revoked && expirationTime != null && Instant.now().isBefore(expirationTime);
    }

    /**
     * 更新最后登录时间和过期时间（续期）
     */
    public RememberMeTokenEntity renew(int validityDays) {
        return new RememberMeTokenEntity(
                this.getId(),
                this.userId,
                this.username,
                this.clientId,
                this.deviceId,
                Instant.now(),
                Instant.now().plusSeconds(validityDays * 86400L)
        );
    }

    /**
     * 撤销令牌
     */
    public void revoke() {
        this.revoked = true;
        this.revokedAt = Instant.now();
    }

}
