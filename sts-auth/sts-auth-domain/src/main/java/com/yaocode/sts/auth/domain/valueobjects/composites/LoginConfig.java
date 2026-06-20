package com.yaocode.sts.auth.domain.valueobjects.composites;

import lombok.Getter;

import java.util.Objects;

/**
 * 登录配置（值对象）
 */
@Getter
public class LoginConfig {

    private final boolean passwordLoginEnabled;
    private final boolean smsLoginEnabled;
    private final boolean emailLoginEnabled;
    private final boolean qrCodeLoginEnabled;
    private final boolean selfRegisterEnabled;
    private final boolean forgotPasswordEnabled;
    private final int maxLoginAttempts;
    private final int lockoutDurationSeconds;
    private final boolean captchaEnabled;

    private LoginConfig(Builder builder) {
        this.passwordLoginEnabled = builder.passwordLoginEnabled;
        this.smsLoginEnabled = builder.smsLoginEnabled;
        this.emailLoginEnabled = builder.emailLoginEnabled;
        this.qrCodeLoginEnabled = builder.qrCodeLoginEnabled;
        this.selfRegisterEnabled = builder.selfRegisterEnabled;
        this.forgotPasswordEnabled = builder.forgotPasswordEnabled;
        this.maxLoginAttempts = builder.maxLoginAttempts;
        this.lockoutDurationSeconds = builder.lockoutDurationSeconds;
        this.captchaEnabled = builder.captchaEnabled;
    }

    public static class Builder {
        private boolean passwordLoginEnabled = true;
        private boolean smsLoginEnabled = false;
        private boolean emailLoginEnabled = false;
        private boolean qrCodeLoginEnabled = false;
        private boolean selfRegisterEnabled = true;
        private boolean forgotPasswordEnabled = true;
        private int maxLoginAttempts = 5;
        private int lockoutDurationSeconds = 3600;
        private boolean captchaEnabled = true;

        public Builder passwordLoginEnabled(boolean enabled) {
            this.passwordLoginEnabled = enabled;
            return this;
        }

        public Builder smsLoginEnabled(boolean enabled) {
            this.smsLoginEnabled = enabled;
            return this;
        }

        public Builder emailLoginEnabled(boolean enabled) {
            this.emailLoginEnabled = enabled;
            return this;
        }

        public Builder qrCodeLoginEnabled(boolean enabled) {
            this.qrCodeLoginEnabled = enabled;
            return this;
        }

        public Builder selfRegisterEnabled(boolean enabled) {
            this.selfRegisterEnabled = enabled;
            return this;
        }

        public Builder forgotPasswordEnabled(boolean enabled) {
            this.forgotPasswordEnabled = enabled;
            return this;
        }

        public Builder maxLoginAttempts(int maxLoginAttempts) {
            if (maxLoginAttempts < 1) throw new IllegalArgumentException("最大登录尝试次数至少为1");
            this.maxLoginAttempts = maxLoginAttempts;
            return this;
        }

        public Builder lockoutDurationSeconds(int lockoutDurationSeconds) {
            if (lockoutDurationSeconds < 0) throw new IllegalArgumentException("锁定时间不能为负数");
            this.lockoutDurationSeconds = lockoutDurationSeconds;
            return this;
        }

        public Builder captchaEnabled(boolean enabled) {
            this.captchaEnabled = enabled;
            return this;
        }

        public LoginConfig build() {
            return new LoginConfig(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

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
