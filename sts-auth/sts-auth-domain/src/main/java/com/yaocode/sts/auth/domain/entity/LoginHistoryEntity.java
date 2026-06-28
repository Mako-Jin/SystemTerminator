package com.yaocode.sts.auth.domain.entity;

import com.yaocode.sts.auth.domain.enums.AuthMethodEnums;
import com.yaocode.sts.auth.domain.enums.LoginSourceEnums;
import com.yaocode.sts.auth.domain.enums.LogoutReasonEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ClientId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.DeviceId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.LoginHistoryId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.SessionId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.IpAddress;
import com.yaocode.sts.common.basic.enums.OppositeEnums;
import com.yaocode.sts.common.domain.model.AbstractAggregate;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import com.yaocode.sts.common.domain.valueobject.UserId;
import lombok.Getter;

import java.time.Instant;

/**
 * 登录历史实体（独立实体）
 * 对应表：auth_tbl_login_history
 */
@Getter
public class LoginHistoryEntity extends AbstractAggregate<LoginHistoryId> {

    private final UserId userId;
    private final TenantId tenantId;
    private final String username;
    private final LoginSourceEnums loginSource;
    private final AuthMethodEnums authMethod;
    private final IpAddress loginIp;
    private final DeviceId deviceId;
    private final ClientId clientId;
    private final String userAgent;
    private final Instant loginTime;
    private final OppositeEnums status;
    private final String failReason;
    private final SessionId sessionId;
    private Instant logoutTime;
    private LogoutReasonEnums logoutReason;

    private LoginHistoryEntity(Builder builder) {
        super(builder.loginHistoryId);
        this.userId = builder.userId;
        this.tenantId = builder.tenantId;
        this.username = builder.username;
        this.loginSource = builder.loginSource;
        this.authMethod = builder.authMethod;
        this.loginIp = builder.loginIp;
        this.deviceId = builder.deviceId;
        this.clientId = builder.clientId;
        this.userAgent = builder.userAgent;
        this.loginTime = builder.loginTime;
        this.status = builder.status;
        this.failReason = builder.failReason;
        this.sessionId = builder.sessionId;
        this.logoutTime = builder.logoutTime;
        this.logoutReason = builder.logoutReason;
    }

    // ========== 工厂方法 ==========

    public static LoginHistoryEntity createSuccess(
            UserId userId,
            TenantId tenantId,
            String username,
            LoginSourceEnums loginSource,
            AuthMethodEnums authMethod,
            IpAddress loginIp,
            DeviceId deviceId,
            ClientId clientId,
            String userAgent,
            SessionId sessionId
    ) {
        return new Builder()
                .loginHistoryId(LoginHistoryId.nextId())
                .userId(userId)
                .tenantId(tenantId)
                .username(username)
                .loginSource(loginSource)
                .authMethod(authMethod)
                .loginIp(loginIp)
                .deviceId(deviceId)
                .clientId(clientId)
                .userAgent(userAgent)
                .loginTime(Instant.now())
                .status(OppositeEnums.SUCCESS)
                .sessionId(sessionId)
                .build();
    }

    public static LoginHistoryEntity createFailure(
            UserId userId,
            TenantId tenantId,
            String username,
            LoginSourceEnums loginSource,
            AuthMethodEnums authMethod,
            IpAddress loginIp,
            DeviceId deviceId,
            ClientId clientId,
            String userAgent,
            String failReason
    ) {
        return new Builder()
                .loginHistoryId(LoginHistoryId.nextId())
                .userId(userId)
                .tenantId(tenantId)
                .username(username)
                .loginSource(loginSource)
                .authMethod(authMethod)
                .loginIp(loginIp)
                .deviceId(deviceId)
                .clientId(clientId)
                .userAgent(userAgent)
                .loginTime(Instant.now())
                .status(OppositeEnums.FAILED)
                .failReason(failReason)
                .build();
    }

    public static LoginHistoryEntity reconstruct(
            LoginHistoryId loginHistoryId,
            UserId userId,
            TenantId tenantId,
            String username,
            LoginSourceEnums loginSource,
            AuthMethodEnums authMethod,
            IpAddress loginIp,
            DeviceId deviceId,
            ClientId clientId,
            String userAgent,
            Instant loginTime,
            OppositeEnums status,
            String failReason,
            SessionId sessionId,
            Instant logoutTime,
            LogoutReasonEnums logoutReason
    ) {
        return new Builder()
                .loginHistoryId(loginHistoryId)
                .userId(userId)
                .tenantId(tenantId)
                .username(username)
                .loginSource(loginSource)
                .authMethod(authMethod)
                .loginIp(loginIp)
                .deviceId(deviceId)
                .clientId(clientId)
                .userAgent(userAgent)
                .loginTime(loginTime)
                .status(status)
                .failReason(failReason)
                .sessionId(sessionId)
                .logoutTime(logoutTime)
                .logoutReason(logoutReason)
                .build();
    }

    // ========== 业务行为 ==========

    public void recordLogout(LogoutReasonEnums reason) {
        this.logoutTime = Instant.now();
        this.logoutReason = reason;
    }

    public boolean isSuccess() {
        return status == OppositeEnums.SUCCESS;
    }

    public boolean isFailed() {
        return status == OppositeEnums.FAILED;
    }

    public boolean isLoggedOut() {
        return logoutTime != null;
    }

    // ========== Builder ==========

    public static class Builder {
        private LoginHistoryId loginHistoryId;
        private UserId userId;
        private TenantId tenantId;
        private String username;
        private LoginSourceEnums loginSource;
        private AuthMethodEnums authMethod;
        private IpAddress loginIp;
        private DeviceId deviceId;
        private ClientId clientId;
        private String userAgent;
        private Instant loginTime;
        private OppositeEnums status;
        private String failReason;
        private SessionId sessionId;
        private Instant logoutTime;
        private LogoutReasonEnums logoutReason;

        public Builder loginHistoryId(LoginHistoryId loginHistoryId) {
            this.loginHistoryId = loginHistoryId;
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

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder loginSource(LoginSourceEnums loginSource) {
            this.loginSource = loginSource;
            return this;
        }

        public Builder authMethod(AuthMethodEnums authMethod) {
            this.authMethod = authMethod;
            return this;
        }

        public Builder loginIp(IpAddress loginIp) {
            this.loginIp = loginIp;
            return this;
        }

        public Builder deviceId(DeviceId deviceId) {
            this.deviceId = deviceId;
            return this;
        }

        public Builder clientId(ClientId clientId) {
            this.clientId = clientId;
            return this;
        }

        public Builder userAgent(String userAgent) {
            this.userAgent = userAgent;
            return this;
        }

        public Builder loginTime(Instant loginTime) {
            this.loginTime = loginTime;
            return this;
        }

        public Builder status(OppositeEnums status) {
            this.status = status;
            return this;
        }

        public Builder failReason(String failReason) {
            this.failReason = failReason;
            return this;
        }

        public Builder sessionId(SessionId sessionId) {
            this.sessionId = sessionId;
            return this;
        }

        public Builder logoutTime(Instant logoutTime) {
            this.logoutTime = logoutTime;
            return this;
        }

        public Builder logoutReason(LogoutReasonEnums logoutReason) {
            this.logoutReason = logoutReason;
            return this;
        }

        public LoginHistoryEntity build() {
            return new LoginHistoryEntity(this);
        }
    }

}
