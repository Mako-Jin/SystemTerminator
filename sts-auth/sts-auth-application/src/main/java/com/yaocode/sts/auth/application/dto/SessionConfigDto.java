package com.yaocode.sts.auth.application.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Objects;

/**
 * 会话配置（值对象）
 */
@Data
@Builder
public class SessionConfigDto {

    /**
     * 会话超时时间（秒）
     */
    @Builder.Default
    private final int sessionTimeoutSeconds = 28800; // 8小时

    /**
     * 是否启用单点登录（同一账号只能一个设备登录）
     */
    @Builder.Default
    private final boolean singleSessionEnabled = false;

    /**
     * 是否启用记住我
     */
    @Builder.Default
    private final boolean rememberMeEnabled = true;

    /**
     * 记住我最大天数
     */
    @Builder.Default
    private final int rememberMeMaxDays = 7;

    /**
     * 最大登录尝试次数
     */
    @Builder.Default
    private final int maxLoginAttempts = 5;

    /**
     * 是否启用验证码
     */
    @Builder.Default
    private final boolean captchaEnabled = true;

    /**
     * 锁定时间（分钟）
     */
    @Builder.Default
    private final int lockDurationMinutes = 30;

    public static SessionConfigDto defaultConfig() {
        return SessionConfigDto.builder().build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionConfigDto that = (SessionConfigDto) o;
        return sessionTimeoutSeconds == that.sessionTimeoutSeconds &&
                singleSessionEnabled == that.singleSessionEnabled &&
                rememberMeEnabled == that.rememberMeEnabled &&
                rememberMeMaxDays == that.rememberMeMaxDays &&
                maxLoginAttempts == that.maxLoginAttempts &&
                captchaEnabled == that.captchaEnabled &&
                lockDurationMinutes == that.lockDurationMinutes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionTimeoutSeconds, singleSessionEnabled,
                rememberMeEnabled, rememberMeMaxDays, maxLoginAttempts,
                captchaEnabled, lockDurationMinutes);
    }
}
