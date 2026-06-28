package com.yaocode.sts.auth.domain.entity;

import com.yaocode.sts.auth.domain.enums.CaptchaTriggerEnums;
import com.yaocode.sts.auth.domain.enums.CaptchaTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.composites.LoginConfig;
import com.yaocode.sts.auth.domain.valueobjects.composites.MFAConfig;
import com.yaocode.sts.auth.domain.valueobjects.composites.PasswordPolicy;
import com.yaocode.sts.auth.domain.valueobjects.composites.SessionConfig;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.TenantConfigId;
import com.yaocode.sts.common.basic.enums.OppositeEnums;
import com.yaocode.sts.common.domain.model.AbstractAggregate;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import lombok.Getter;

import java.util.Objects;

/**
 * 租户配置实体（属于租户聚合）
 * 对应表：auth_tbl_tenant_config
 */
@Getter
public class TenantConfigEntity extends AbstractAggregate<TenantConfigId> {

    private final TenantConfigId configId;
    private final TenantId tenantId;
    private PasswordPolicy passwordPolicy;
    private SessionConfig sessionConfig;
    private MFAConfig mfaConfig;
    private LoginConfig loginConfig;
    private CaptchaTypeEnums captchaType;
    private CaptchaTriggerEnums captchaTrigger;
    private OppositeEnums isEnabled;

    private OppositeEnums passwordLoginEnabled;
    private OppositeEnums smsLoginEnabled;
    private OppositeEnums emailLoginEnabled;
    private OppositeEnums qrCodeLoginEnabled;

    private Integer maxLoginAttempts;

    private TenantConfigEntity(TenantConfigId configId, TenantId tenantId) {
        super(configId);
        this.configId = configId;
        this.tenantId = tenantId;
        this.isEnabled = OppositeEnums.ENABLED;
    }

    // ========== 工厂方法 ==========

    public static TenantConfigEntity create(
            TenantId tenantId,
            PasswordPolicy passwordPolicy,
            SessionConfig sessionConfig,
            MFAConfig mfaConfig,
            LoginConfig loginConfig,
            CaptchaTypeEnums captchaType,
            CaptchaTriggerEnums captchaTrigger
    ) {
        TenantConfigEntity entity = new TenantConfigEntity(TenantConfigId.nextId(), tenantId);
        entity.passwordPolicy = passwordPolicy != null ? passwordPolicy : PasswordPolicy.defaultPolicy();
        entity.sessionConfig = sessionConfig != null ? sessionConfig : SessionConfig.defaultConfig();
        entity.mfaConfig = mfaConfig != null ? mfaConfig : MFAConfig.disabled();
        entity.loginConfig = loginConfig != null ? loginConfig : LoginConfig.defaultConfig();
        entity.captchaType = captchaType != null ? captchaType : CaptchaTypeEnums.TEXT;
        entity.captchaTrigger = captchaTrigger != null ? captchaTrigger : CaptchaTriggerEnums.FAILED;
        return entity;
    }

    public static TenantConfigEntity reconstruct(
            TenantConfigId configId,
            TenantId tenantId,
            PasswordPolicy passwordPolicy,
            SessionConfig sessionConfig,
            MFAConfig mfaConfig,
            LoginConfig loginConfig,
            CaptchaTypeEnums captchaType,
            CaptchaTriggerEnums captchaTrigger,
            OppositeEnums isEnabled
    ) {
        TenantConfigEntity entity = new TenantConfigEntity(configId, tenantId);
        entity.passwordPolicy = passwordPolicy;
        entity.sessionConfig = sessionConfig;
        entity.mfaConfig = mfaConfig;
        entity.loginConfig = loginConfig;
        entity.captchaType = captchaType;
        entity.captchaTrigger = captchaTrigger;
        entity.isEnabled = isEnabled != null ? isEnabled : OppositeEnums.ENABLED;
        return entity;
    }

    // ========== 业务行为 ==========

    public void updatePasswordPolicy(PasswordPolicy passwordPolicy) {
        this.passwordPolicy = passwordPolicy;
    }

    public void updateSessionConfig(SessionConfig sessionConfig) {
        this.sessionConfig = sessionConfig;
    }

    public void updateMFAConfig(MFAConfig mfaConfig) {
        this.mfaConfig = mfaConfig;
    }

    public void updateLoginConfig(LoginConfig loginConfig) {
        this.loginConfig = loginConfig;
    }

    public void updateCaptcha(CaptchaTypeEnums captchaType, CaptchaTriggerEnums captchaTrigger) {
        this.captchaType = captchaType;
        this.captchaTrigger = captchaTrigger;
    }

    public void enable() {
        this.isEnabled = OppositeEnums.ENABLED;
    }

    public void disable() {
        this.isEnabled = OppositeEnums.DISABLED;
    }

    public boolean isActive() {
        return isEnabled == OppositeEnums.ENABLED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TenantConfigEntity that = (TenantConfigEntity) o;
        return Objects.equals(configId, that.configId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(configId);
    }
}
