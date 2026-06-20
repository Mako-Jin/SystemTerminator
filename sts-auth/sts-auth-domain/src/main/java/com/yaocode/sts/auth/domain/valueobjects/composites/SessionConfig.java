package com.yaocode.sts.auth.domain.valueobjects.composites;

import lombok.Getter;

import java.util.Objects;

/**
 * 会话配置（值对象）
 */
@Getter
public class SessionConfig {

    private final int sessionTimeoutSeconds;
    private final boolean singleSessionEnabled;
    private final boolean rememberMeEnabled;
    private final int rememberMeMaxDays;

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
            if (seconds < 60) throw new IllegalArgumentException("会话超时时间至少为60秒");
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
            if (days < 1) throw new IllegalArgumentException("记住我最大天数至少为1天");
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
