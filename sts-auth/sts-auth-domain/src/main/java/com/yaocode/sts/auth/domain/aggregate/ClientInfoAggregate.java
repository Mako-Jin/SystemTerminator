package com.yaocode.sts.auth.domain.aggregate;

import com.yaocode.sts.auth.domain.constants.AuthI18nKeyConstants;
import com.yaocode.sts.auth.domain.enums.ClientTypeEnums;
import com.yaocode.sts.auth.domain.enums.GrantTypeEnums;
import com.yaocode.sts.auth.domain.events.client.ClientCreatedEvent;
import com.yaocode.sts.auth.domain.events.client.ClientDisabledEvent;
import com.yaocode.sts.auth.domain.events.client.ClientEnabledEvent;
import com.yaocode.sts.auth.domain.events.client.ClientSecretRotatedEvent;
import com.yaocode.sts.auth.domain.events.client.GrantTypeAddedEvent;
import com.yaocode.sts.auth.domain.events.client.GrantTypeRemovedEvent;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ClientId;
import com.yaocode.sts.common.basic.enums.EnableEnums;
import com.yaocode.sts.common.domain.exception.DomainException;
import com.yaocode.sts.common.domain.model.AbstractAggregate;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

/**
 * OAuth2客户端聚合根
 * 管理OAuth2客户端的配置、密钥和授权类型
 * 对应表：auth_tbl_client_info
 */
@Getter
public class ClientInfoAggregate extends AbstractAggregate<ClientId> {

    // ============ 核心属性 ============
    private String clientName;
    private ClientTypeEnums clientType;
    private String clientVersion;
    private String clientSecret;      // 加密存储
    private Set<GrantTypeEnums> grantTypes;
    private Set<String> redirectUris;
    private Set<String> scope;
    private String appId;
    private EnableEnums enabled;

    // ============ 构造函数 ============
    private ClientInfoAggregate(ClientId clientId) {
        super(clientId);
        this.grantTypes = new HashSet<>();
        this.redirectUris = new HashSet<>();
        this.scope = new HashSet<>();
        this.enabled = EnableEnums.ENABLED;
    }

    // ============ 工厂方法 ============

    /**
     * 创建OAuth2客户端
     */
    public static ClientInfoAggregate create(
            String clientName,
            ClientTypeEnums clientType,
            String clientVersion,
            String clientSecret,
            Set<GrantTypeEnums> grantTypes,
            Set<String> redirectUris,
            Set<String> scope,
            String appId
    ) {
        if (grantTypes == null || grantTypes.isEmpty()) {
            throw new DomainException(AuthI18nKeyConstants.AT_LEAST_ONE_GRANT_TYPE_REQUIRED);
        }

        ClientInfoAggregate client = new ClientInfoAggregate(ClientId.nextId());
        client.clientName = clientName;
        client.clientType = clientType != null ? clientType : ClientTypeEnums.WEB;
        client.clientVersion = clientVersion;
        client.clientSecret = clientSecret;
        client.grantTypes = new HashSet<>(grantTypes);
        client.redirectUris = redirectUris != null ? new HashSet<>(redirectUris) : new HashSet<>();
        client.scope = scope != null ? new HashSet<>(scope) : new HashSet<>();
        client.appId = appId;

        // 验证redirectUris
        if (grantTypes.contains(GrantTypeEnums.AUTHORIZATION_CODE) && client.redirectUris.isEmpty()) {
            throw new DomainException(AuthI18nKeyConstants.AUTHORIZATION_CODE_REQUIRES_REDIRECT_URI);
        }

        client.registerEvent(new ClientCreatedEvent(client.getId(), clientName));

        return client;
    }

    /**
     * 从数据库重建客户端聚合
     */
    public static ClientInfoAggregate reconstruct(
            ClientId clientId,
            String clientName,
            ClientTypeEnums clientType,
            String clientVersion,
            String clientSecret,
            Set<GrantTypeEnums> grantTypes,
            Set<String> redirectUris,
            Set<String> scope,
            String appId,
            EnableEnums enabled
    ) {
        ClientInfoAggregate client = new ClientInfoAggregate(clientId);
        client.clientName = clientName;
        client.clientType = clientType != null ? clientType : ClientTypeEnums.WEB;
        client.clientVersion = clientVersion;
        client.clientSecret = clientSecret;
        client.grantTypes = grantTypes != null ? new HashSet<>(grantTypes) : new HashSet<>();
        client.redirectUris = redirectUris != null ? new HashSet<>(redirectUris) : new HashSet<>();
        client.scope = scope != null ? new HashSet<>(scope) : new HashSet<>();
        client.appId = appId;
        client.enabled = enabled != null ? enabled : EnableEnums.ENABLED;
        return client;
    }

    // ============ 业务行为 ============

    // ----- 基本信息管理 -----

    /**
     * 更新客户端名称
     */
    public void updateName(String clientName) {
        if (clientName == null || clientName.trim().isEmpty()) {
            throw new DomainException(AuthI18nKeyConstants.CLIENT_NAME_CANNOT_BE_BLANK);
        }
        this.clientName = clientName.trim();
    }

    /**
     * 更新客户端版本
     */
    public void updateVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }

    /**
     * 更新应用ID
     */
    public void updateAppId(String appId) {
        this.appId = appId;
    }

    // ----- 密钥管理 -----

    /**
     * 轮换客户端密钥
     */
    public void rotateSecret(String newSecret) {
        if (newSecret == null || newSecret.trim().isEmpty()) {
            throw new DomainException(AuthI18nKeyConstants.CLIENT_SECRET_CANNOT_BE_BLANK);
        }
        this.clientSecret = newSecret.trim();
        registerEvent(new ClientSecretRotatedEvent(this.getId()));
    }

    // ----- 授权类型管理 -----

    /**
     * 添加授权类型
     */
    public void addGrantType(GrantTypeEnums grantType) {
        if (grantTypes.contains(grantType)) {
            throw new DomainException(AuthI18nKeyConstants.GRANT_TYPE_ALREADY_EXISTS);
        }
        // 如果是授权码模式，需要检查redirectUris
        if (grantType == GrantTypeEnums.AUTHORIZATION_CODE && redirectUris.isEmpty()) {
            throw new DomainException(AuthI18nKeyConstants.ADD_AUTH_CODE_BEFORE_CONFIG_REDIRECT_URI);
        }
        grantTypes.add(grantType);
        registerEvent(new GrantTypeAddedEvent(this.getId(), grantType));
    }

    /**
     * 移除授权类型
     */
    public void removeGrantType(GrantTypeEnums grantType) {
        if (!grantTypes.contains(grantType)) {
            throw new DomainException(AuthI18nKeyConstants.GRANT_TYPE_NOT_EXIST);
        }
        if (grantTypes.size() == 1) {
            throw new DomainException(AuthI18nKeyConstants.AT_LEAST_ONE_GRANT_TYPE_MUST_REMAIN);
        }
        grantTypes.remove(grantType);
        registerEvent(new GrantTypeRemovedEvent(this.getId(), grantType));
    }

    // ----- 重定向URI管理 -----

    /**
     * 添加重定向URI
     */
    public void addRedirectUri(String redirectUri) {
        if (redirectUri == null || redirectUri.trim().isEmpty()) {
            throw new DomainException(AuthI18nKeyConstants.REDIRECT_URI_CANNOT_BE_BLANK);
        }
        redirectUris.add(redirectUri.trim());
    }

    /**
     * 移除重定向URI
     */
    public void removeRedirectUri(String redirectUri) {
        if (!redirectUris.contains(redirectUri)) {
            throw new DomainException(AuthI18nKeyConstants.REDIRECT_URI_NOT_EXIST);
        }
        if (grantTypes.contains(GrantTypeEnums.AUTHORIZATION_CODE) && redirectUris.size() <= 1) {
            throw new DomainException(AuthI18nKeyConstants.AUTHORIZATION_CODE_REQUIRES_AT_LEAST_ONE_REDIRECT_URI);
        }
        redirectUris.remove(redirectUri);
    }

    // ----- Scope管理 -----

    /**
     * 添加Scope
     */
    public void addScope(String scope) {
        if (scope == null || scope.trim().isEmpty()) {
            throw new DomainException(AuthI18nKeyConstants.SCOPE_CANNOT_BE_BLANK);
        }
        this.scope.add(scope.trim());
    }

    /**
     * 移除Scope
     */
    public void removeScope(String scope) {
        this.scope.remove(scope);
    }

    // ----- 状态管理 -----

    /**
     * 启用客户端
     */
    public void enable() {
        if (this.enabled == EnableEnums.ENABLED) {
            throw new DomainException(AuthI18nKeyConstants.CLIENT_ALREADY_ENABLED);
        }
        this.enabled = EnableEnums.ENABLED;
        registerEvent(new ClientEnabledEvent(this.getId()));
    }

    /**
     * 禁用客户端
     */
    public void disable() {
        if (this.enabled == EnableEnums.DISABLED) {
            throw new DomainException(AuthI18nKeyConstants.CLIENT_ALREADY_DISABLED);
        }
        this.enabled = EnableEnums.DISABLED;
        registerEvent(new ClientDisabledEvent(this.getId()));
    }

    // ----- 查询方法 -----

    /**
     * 判断客户端是否启用
     */
    public boolean isEnabled() {
        return enabled == EnableEnums.ENABLED;
    }

    /**
     * 判断是否支持指定的授权类型
     */
    public boolean supportsGrantType(GrantTypeEnums grantType) {
        return grantTypes.contains(grantType);
    }

    /**
     * 判断是否支持指定的Scope
     */
    public boolean supportsScope(String scope) {
        return this.scope.isEmpty() || this.scope.contains(scope);
    }

    /**
     * 验证重定向URI
     */
    public boolean validateRedirectUri(String redirectUri) {
        if (redirectUris.isEmpty()) {
            return true;
        }
        return redirectUris.contains(redirectUri);
    }
}