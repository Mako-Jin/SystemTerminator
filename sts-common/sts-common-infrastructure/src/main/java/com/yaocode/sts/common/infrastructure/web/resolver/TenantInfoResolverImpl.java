package com.yaocode.sts.common.infrastructure.web.resolver;

import com.yaocode.sts.common.domain.constants.HeaderConstants;
import com.yaocode.sts.common.domain.constants.RequestConstants;
import com.yaocode.sts.common.domain.context.TenantInfoContext;
import com.yaocode.sts.common.domain.context.spi.TenantInfoResolver;
import com.yaocode.sts.common.domain.valueobject.TenantCode;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import com.yaocode.sts.common.domain.web.HttpRequestContext;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 基于 HTTP Header / Parameter 的租户信息解析器
 * 优先级：Parameter > Header
 */
@Component
public class TenantInfoResolverImpl implements TenantInfoResolver {

    @Override
    public Optional<TenantInfoContext> resolve(HttpRequestContext context) {
        TenantInfoContext tenantInfo = TenantInfoContext.createDefault();

        // ========== 租户基础信息 ==========
        context.getHeaderOrParameter(HeaderConstants.TENANT_ID)
                .or(() -> context.getHeaderOrParameter(RequestConstants.TENANT_ID))
                .ifPresent(v -> tenantInfo.setTenantId(TenantId.of(v)));
        context.getHeaderOrParameter(HeaderConstants.TENANT_NAME)
                .or(() -> context.getHeaderOrParameter(RequestConstants.TENANT_NAME))
                .ifPresent(tenantInfo::setTenantName);
        context.getHeaderOrParameter(HeaderConstants.TENANT_CODE)
                .or(() -> context.getHeaderOrParameter(RequestConstants.TENANT_CODE))
                .ifPresent(v -> tenantInfo.setTenantCode(TenantCode.of(v)));
        context.getParameter(RequestConstants.TENANT_DESC).ifPresent(tenantInfo::setTenantDesc);

        // ========== 租户层级 ==========
        context.getParameter(RequestConstants.TENANT_LEVEL).ifPresent(v -> {
            try { tenantInfo.setTenantLevel(Integer.parseInt(v)); } catch (NumberFormatException ignored) {}
        });
        context.getParameter(RequestConstants.PARENT_ID).ifPresent(tenantInfo::setParentId);
        context.getParameter(RequestConstants.TENANT_CODE_PATH).ifPresent(tenantInfo::setTenantCodePath);
        parseInt(context, RequestConstants.TENANT_LEVEL).ifPresent(tenantInfo::setTenantLevel);
        // ========== 租户配置 ==========
        context.getParameter(RequestConstants.BRAND_ID).ifPresent(tenantInfo::setBrandId);
        context.getParameter(RequestConstants.BRAND_NAME).ifPresent(tenantInfo::setBrandName);
        context.getParameter(RequestConstants.LOGO_URL).ifPresent(tenantInfo::setLogoUrl);
        context.getParameter(RequestConstants.LOGIN_TITLE).ifPresent(tenantInfo::setLoginTitle);
        context.getParameter(RequestConstants.PRIMARY_COLOR).ifPresent(tenantInfo::setPrimaryColor);
        context.getParameter(RequestConstants.LOGIN_BACKGROUND_URL).ifPresent(tenantInfo::setLoginBackgroundUrl);
        // ========== 登录策略 ==========
        parseBoolean(context, RequestConstants.PASSWORD_LOGIN_ENABLED).ifPresent(tenantInfo::setPasswordLoginEnabled);
        parseBoolean(context, RequestConstants.SMS_LOGIN_ENABLED).ifPresent(tenantInfo::setSmsLoginEnabled);
        parseBoolean(context, RequestConstants.EMAIL_LOGIN_ENABLED).ifPresent(tenantInfo::setEmailLoginEnabled);
        parseBoolean(context, RequestConstants.QR_CODE_LOGIN_ENABLED).ifPresent(tenantInfo::setQrCodeLoginEnabled);
        parseBoolean(context, RequestConstants.CAPTCHA_ENABLED).ifPresent(tenantInfo::setCaptchaEnabled);
        parseBoolean(context, RequestConstants.MFA_REQUIRED).ifPresent(tenantInfo::setMfaRequired);
        parseInt(context, RequestConstants.MAX_LOGIN_ATTEMPTS).ifPresent(tenantInfo::setMaxLoginAttempts);
        parseInt(context, RequestConstants.SESSION_TIMEOUT).ifPresent(tenantInfo::setSessionTimeout);
        // ========== 租户策略 ==========
        context.getParameter(RequestConstants.PASSWORD_LOGIN_ENABLED).ifPresent(v -> tenantInfo.setPasswordLoginEnabled(Boolean.parseBoolean(v)));
        context.getParameter(RequestConstants.SMS_LOGIN_ENABLED).ifPresent(v -> tenantInfo.setSmsLoginEnabled(Boolean.parseBoolean(v)));
        context.getParameter(RequestConstants.EMAIL_LOGIN_ENABLED).ifPresent(v -> tenantInfo.setEmailLoginEnabled(Boolean.parseBoolean(v)));
        context.getParameter(RequestConstants.QR_CODE_LOGIN_ENABLED).ifPresent(v -> tenantInfo.setQrCodeLoginEnabled(Boolean.parseBoolean(v)));
        context.getParameter(RequestConstants.CAPTCHA_ENABLED).ifPresent(v -> tenantInfo.setCaptchaEnabled(Boolean.parseBoolean(v)));
        context.getParameter(RequestConstants.MFA_REQUIRED).ifPresent(v -> tenantInfo.setMfaRequired(Boolean.parseBoolean(v)));

        context.getParameter(RequestConstants.MAX_LOGIN_ATTEMPTS).ifPresent(v -> {
            try { tenantInfo.setMaxLoginAttempts(Integer.parseInt(v)); } catch (NumberFormatException ignored) {}
        });
        context.getParameter(RequestConstants.SESSION_TIMEOUT).ifPresent(v -> {
            try { tenantInfo.setSessionTimeout(Integer.parseInt(v)); } catch (NumberFormatException ignored) {}
        });

        // ========== 状态控制 ==========
        parseBoolean(context, RequestConstants.IS_ENABLED).ifPresent(tenantInfo::setIsEnabled);
        parseBoolean(context, RequestConstants.ALLOW_REGISTER).ifPresent(tenantInfo::setAllowRegister);
        parseBoolean(context, RequestConstants.ALLOW_ADD).ifPresent(tenantInfo::setAllowAdd);

        return Optional.of(tenantInfo);
    }

    /**
     * 解析 Integer 参数
     */
    private Optional<Integer> parseInt(HttpRequestContext context, String name) {
        return context.getParameter(name)
                .flatMap(v -> {
                    try {
                        return Optional.of(Integer.parseInt(v));
                    } catch (NumberFormatException e) {
                        return Optional.empty();
                    }
                });
    }
    /**
     * 解析 Boolean 参数
     */
    private Optional<Boolean> parseBoolean(HttpRequestContext context, String name) {
        return context.getParameter(name)
                .flatMap(v -> {
                    if (v.isEmpty()) {
                        return Optional.empty();
                    }
                    return Optional.of(Boolean.parseBoolean(v));
                });
    }
}