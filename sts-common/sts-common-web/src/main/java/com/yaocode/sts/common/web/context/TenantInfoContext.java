package com.yaocode.sts.common.web.context;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 租户信息上下文
 * 包含：租户ID、名称、配置等
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TenantInfoContext extends BaseAbstractContext<TenantInfoContext> {

    // ========== 租户基础信息 ==========
    private String tenantId;
    private String tenantName;
    private String tenantCode;
    private String tenantDesc;
    private Integer tenantLevel;
    private String parentId;
    private String tenantCodePath;

    // ========== 租户配置 ==========
    private String brandId;
    private String brandName;
    private String logoUrl;
    private String loginTitle;
    private String primaryColor;
    private String loginBackgroundUrl;

    // ========== 租户策略 ==========
    private Boolean passwordLoginEnabled;
    private Boolean smsLoginEnabled;
    private Boolean emailLoginEnabled;
    private Boolean qrCodeLoginEnabled;
    private Boolean captchaEnabled;
    private Boolean mfaRequired;
    private Integer maxLoginAttempts;
    private Integer sessionTimeout;

    // ========== 状态 ==========
    private Boolean isEnabled;
    private Boolean allowRegister;
    private Boolean allowAdd;

    public static TenantInfoContext createDefault() {
        TenantInfoContext context = new TenantInfoContext();
        context.setIsEnabled(true);
        context.setAllowRegister(true);
        context.setAllowAdd(true);
        context.setPasswordLoginEnabled(true);
        context.setCaptchaEnabled(true);
        return context;
    }

    @Override
    protected TenantInfoContext getDefault() {
        return createDefault();
    }

    @Override
    protected String getContextName() {
        return "TenantInfo";
    }

    // ========== 便捷方法 ==========
    public boolean isRootTenant() {
        return parentId == null || parentId.isEmpty();
    }

    public boolean hasBrandConfig() {
        return brandId != null && !brandId.isEmpty();
    }
}

