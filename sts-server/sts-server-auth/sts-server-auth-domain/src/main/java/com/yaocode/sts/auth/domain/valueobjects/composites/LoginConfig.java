package com.yaocode.sts.auth.domain.valueobjects.composites;

import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.Objects;

/**
 * 登录配置（值对象）
 */
@Value
@Builder
public class LoginConfig {

    boolean passwordLoginEnabled;
    boolean smsLoginEnabled;
    boolean emailLoginEnabled;
    boolean qrCodeLoginEnabled;
    boolean selfRegisterEnabled;
    boolean forgotPasswordEnabled;
    int maxLoginAttempts;
    int lockoutDurationSeconds;
    boolean captchaEnabled;
    String captchaType;
    boolean mfaEnabled;

    List<String> socialProviders;

    /**
     * 获取默认配置
     */
    public static LoginConfig defaultConfig() {
        return builder().build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginConfig that = (LoginConfig) o;
        return passwordLoginEnabled == that.passwordLoginEnabled &&
                smsLoginEnabled == that.smsLoginEnabled &&
                emailLoginEnabled == that.emailLoginEnabled &&
                qrCodeLoginEnabled == that.qrCodeLoginEnabled &&
                selfRegisterEnabled == that.selfRegisterEnabled &&
                forgotPasswordEnabled == that.forgotPasswordEnabled &&
                maxLoginAttempts == that.maxLoginAttempts &&
                lockoutDurationSeconds == that.lockoutDurationSeconds &&
                captchaEnabled == that.captchaEnabled;
    }

    @Override
    public int hashCode() {
        return Objects.hash(passwordLoginEnabled, smsLoginEnabled, emailLoginEnabled,
                qrCodeLoginEnabled, selfRegisterEnabled, forgotPasswordEnabled,
                maxLoginAttempts, lockoutDurationSeconds, captchaEnabled);
    }
}
