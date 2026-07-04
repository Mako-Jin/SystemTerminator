package com.yaocode.sts.auth.domain.valueobjects.composites;

import com.yaocode.sts.auth.domain.constants.AuthI18nKeyConstants;
import lombok.Value;

import java.util.Objects;

/**
 * 会话配置（值对象）
 */
@Value
public class SessionConfig {

    int sessionTimeoutSeconds;
    boolean singleSessionEnabled;
    boolean rememberMeEnabled;
    int rememberMeMaxDays;

    private SessionConfig(Builder builder) {
        this.sessionTimeoutSeconds = builder.sessionTimeoutSeconds;
        this.singleSessionEnabled = builder.singleSessionEnabled;
        this.rememberMeEnabled = builder.rememberMeEnabled;
        this.rememberMeMaxDays = builder.rememberMeMaxDays;
    }

    public static class Builder {
        private int sessionTimeoutSeconds = 3600 * 8; // 8小时
        private boolean singleSessionEnabled = false;
        private boolean rememberMeEnabled = true;
        private int rememberMeMaxDays = 7;

        public Builder sessionTimeoutSeconds(int seconds) {
            if (seconds < 60) throw new IllegalArgumentException(AuthI18nKeyConstants.SESSION_TIMEOUT_MINIMUM_60_SECONDS);
            this.sessionTimeoutSeconds = seconds;
            return this;
        }

        public Builder singleSessionEnabled(boolean enabled) {
            this.singleSessionEnabled = enabled;
            return this;
        }

        public Builder rememberMeEnabled(boolean enabled) {
            this.rememberMeEnabled = enabled;
            return this;
        }

        public Builder rememberMeMaxDays(int days) {
            if (days < 1) throw new IllegalArgumentException(AuthI18nKeyConstants.REMEMBER_ME_MAX_DAYS_MINIMUM_ONE);
            this.rememberMeMaxDays = days;
            return this;
        }

        public SessionConfig build() {
            return new SessionConfig(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static SessionConfig defaultConfig() {
        return builder().build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionConfig that = (SessionConfig) o;
        return sessionTimeoutSeconds == that.sessionTimeoutSeconds &&
                singleSessionEnabled == that.singleSessionEnabled &&
                rememberMeEnabled == that.rememberMeEnabled &&
                rememberMeMaxDays == that.rememberMeMaxDays;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionTimeoutSeconds, singleSessionEnabled, rememberMeEnabled, rememberMeMaxDays);
    }
}