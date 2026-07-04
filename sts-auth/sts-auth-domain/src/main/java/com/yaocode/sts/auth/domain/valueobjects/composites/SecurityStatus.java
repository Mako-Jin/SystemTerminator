package com.yaocode.sts.auth.domain.valueobjects.composites;

import com.yaocode.sts.auth.domain.constants.AuthI18nKeyConstants;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 安全状态值对象
 * 不可变，通过属性值判断相等
 */
@Value
@Builder
public class SecurityStatus {

    boolean locked;
    String lockReason;
    String unlockTime;
    int remainingAttempts;
    boolean captchaRequired;
    boolean trustedDevice;

    // 工厂方法：创建初始安全状态
    public static SecurityStatus initial(int maxAttempts, boolean trustedDevice) {
        return SecurityStatus.builder()
                .locked(false)
                .remainingAttempts(maxAttempts)
                .captchaRequired(false)
                .trustedDevice(trustedDevice)
                .build();
    }

    // 工厂方法：创建锁定状态
    public static SecurityStatus locked(String reason, String unlockTime) {
        return SecurityStatus.builder()
                .locked(true)
                .lockReason(reason)
                .unlockTime(unlockTime)
                .remainingAttempts(0)
                .captchaRequired(true)
                .trustedDevice(false)
                .build();
    }

    // 业务行为：是否可以登录
    public boolean canLogin() {
        return !locked && remainingAttempts > 0;
    }

    // 业务行为：是否需要验证码
    public boolean needsCaptcha() {
        return captchaRequired || remainingAttempts <= 2;
    }

    // 业务行为：记录登录失败后的新状态
    public SecurityStatus recordFailedAttempt(int maxAttempts) {
        if (locked) {
            return this; // 已锁定，状态不变
        }

        int newRemaining = remainingAttempts - 1;
        boolean newLocked = newRemaining <= 0;

        return SecurityStatus.builder()
                .locked(newLocked)
                .lockReason(newLocked ? AuthI18nKeyConstants.PASSWORD_ERROR_EXCEEDED_LIMIT : null)
                .unlockTime(newLocked ? LocalDateTime.now().plusMinutes(30).toString() : null)
                .remainingAttempts(Math.max(0, newRemaining))
                .captchaRequired(newRemaining <= 2)
                .trustedDevice(trustedDevice)
                .build();
    }

    // 业务行为：记录登录成功后的新状态
    public SecurityStatus recordSuccessfulLogin(int maxAttempts) {
        return SecurityStatus.builder()
                .locked(false)
                .lockReason(null)
                .unlockTime(null)
                .remainingAttempts(maxAttempts)
                .captchaRequired(false)
                .trustedDevice(trustedDevice)
                .build();
    }

    // 业务行为：更新设备信任状态
    public SecurityStatus withTrustedDevice(boolean trusted) {
        if (this.trustedDevice == trusted) {
            return this;
        }
        return SecurityStatus.builder()
                .locked(locked)
                .lockReason(lockReason)
                .unlockTime(unlockTime)
                .remainingAttempts(remainingAttempts)
                .captchaRequired(captchaRequired)
                .trustedDevice(trusted)
                .build();
    }

    // 值对象关键：equals 基于所有属性
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SecurityStatus that = (SecurityStatus) o;
        return locked == that.locked &&
                remainingAttempts == that.remainingAttempts &&
                captchaRequired == that.captchaRequired &&
                trustedDevice == that.trustedDevice &&
                Objects.equals(lockReason, that.lockReason) &&
                Objects.equals(unlockTime, that.unlockTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locked, lockReason, unlockTime,
                remainingAttempts, captchaRequired, trustedDevice);
    }

    @Override
    public String toString() {
        return "SecurityStatus{" +
                "locked=" + locked +
                ", remainingAttempts=" + remainingAttempts +
                ", captchaRequired=" + captchaRequired +
                '}';
    }
}