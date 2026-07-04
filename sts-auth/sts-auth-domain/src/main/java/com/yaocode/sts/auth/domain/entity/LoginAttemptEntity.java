package com.yaocode.sts.auth.domain.entity;

import com.yaocode.sts.auth.domain.constants.AuthI18nKeyConstants;
import com.yaocode.sts.auth.domain.constants.CommonConstants;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.LoginAttemptId;
import com.yaocode.sts.common.basic.enums.YesNoEnums;
import com.yaocode.sts.common.domain.model.AbstractAggregate;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import com.yaocode.sts.common.domain.valueobject.UserId;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;


@Getter
@EqualsAndHashCode(callSuper = true)
public class LoginAttemptEntity extends AbstractAggregate<LoginAttemptId> {

    /**
     * 基础锁定时间（分钟）
     */
    private static final int BASE_LOCK_MINUTES = 5;

    /**
     * 指数增长因子
     */
    private static final double GROWTH_FACTOR = 2.0;

    /**
     * 最大锁定时间（分钟）
     */
    private static final int MAX_LOCK_MINUTES = 1440; // 24小时

    private String attemptId;
    private final UserId userId;
    private final TenantId tenantId;
    private Integer failedAttempts;
    private LocalDateTime firstFailedTime;
    private LocalDateTime lastFailedTime;
    private LocalDateTime lockedUntil;
    private String lastFailedIp;
    private String lastFailReason;
    private YesNoEnums isLocked;
    private LocalDateTime lockTime;
    private LocalDateTime unlockTime;
    private String lockReason;

    /**
     * 租户配置的锁定策略（冗余存储）
     */
    private String lockStrategy; // FIXED, EXPONENTIAL, CUSTOM
    /**
     * 锁定级别（用于计算下次锁定时间）
     */
    private int lockLevel;
    /**
     * 租户配置的最大允许失败次数（冗余存储，避免每次查询配置）
     */
    private Integer maxAllowedFailures;

    private LoginAttemptEntity(LoginAttemptId loginAttemptId, UserId userId, TenantId tenantId) {
        super(loginAttemptId);
        this.userId = userId;
        this.tenantId = tenantId;
    }

    public static LoginAttemptEntity create(UserId userId, TenantId tenantId) {
        return new LoginAttemptEntity(LoginAttemptId.nextId(), userId, tenantId);
    }

    /**
     * 记录失败尝试
     */
    public void recordFailedAttempt() {
        this.failedAttempts++;
        this.lastFailedTime = LocalDateTime.now();

        // 检查是否需要锁定
        if (this.failedAttempts >= maxAllowedFailures) {
            this.lock(AuthI18nKeyConstants.PASSWORD_ERROR_EXCEEDED_LIMIT);
        }
    }

    /**
     * 重置尝试次数（登录成功后）
     */
    public void reset() {
        this.failedAttempts = 0;
        this.lastFailedTime = null;
        this.unlock();
    }

    /**
     * 锁定用户
     */
    public void lock(String reason) {
        this.isLocked = YesNoEnums.YES;
        this.lockReason = reason;
        long lockMinutes = calculateLockDuration();
        this.lockedUntil = LocalDateTime.now().plusMinutes(lockMinutes);
    }

    /**
     * 解锁用户
     */
    public void unlock() {
        this.isLocked = YesNoEnums.NO;
        this.lockReason = null;
        this.lockedUntil = null;
    }

    /**
     * 检查是否应该锁定
     */
    public boolean shouldLock() {
        return this.failedAttempts >= this.maxAllowedFailures;
    }

    /**
     * 检查是否已锁定且未解锁
     */
    public boolean isLocked() {
        if (Objects.equals(this.isLocked, YesNoEnums.NO)) {
            return false;
        }

        // 如果锁定时间已到，自动解锁
        if (lockedUntil != null && LocalDateTime.now().isAfter(lockedUntil)) {
            this.unlock();
            return false;
        }

        return true;
    }

    /**
     * 获取剩余锁定时间（分钟）
     */
    public long getRemainingLockMinutes() {
        if (Objects.equals(this.isLocked, YesNoEnums.NO) || lockedUntil == null) {
            return 0;
        }

        long minutes = java.time.Duration.between(LocalDateTime.now(), lockedUntil).toMinutes();
        return Math.max(0, minutes);
    }

    /**
     * 获取剩余尝试次数
     */
    public int getRemainingAttempts() {
        if (this.isLocked()) {
            return 0;
        }
        return Math.max(0, this.maxAllowedFailures - this.failedAttempts);
    }

    /**
     * 计算锁定持续时间（分钟）
     * <p>
     * 算法：基于 lockLevel 指数增长
     * lockLevel 0 -> 5 分钟
     * lockLevel 1 -> 15 分钟
     * lockLevel 2 -> 30 分钟
     * lockLevel 3 -> 60 分钟
     * lockLevel 4 -> 120 分钟
     * lockLevel 5 -> 240 分钟
     * lockLevel 6+ -> 1440 分钟 (24小时)
     *
     * @return 锁定分钟数
     */
    public long calculateLockDuration() {
        if (CommonConstants.LOCK_STRATEGY_FIXED.equalsIgnoreCase(this.lockStrategy)) {
            // 固定时长策略
            return Math.min(BASE_LOCK_MINUTES * 6, MAX_LOCK_MINUTES);
        }

        // 指数退避策略（默认）
        // lockLevel 从 0 开始
        // 公式：BASE_LOCK_MINUTES * (growthFactor ^ lockLevel)
        long duration = (long) (BASE_LOCK_MINUTES * Math.pow(GROWTH_FACTOR, this.lockLevel));

        // 应用一些调整，使增长更平滑
        // level 0: 5, level 1: 10, level 2: 20, level 3: 40, level 4: 80, level 5: 160, level 6+: 300
        duration = Math.min(duration, MAX_LOCK_MINUTES);
        duration = Math.max(duration, BASE_LOCK_MINUTES);

        return duration;
    }

    /**
     * 计算基于失败次数的锁定时间（备选方案）
     * 适用于没有 lockLevel 的场景
     */
    public static long calculateLockDurationByFailCount(int failedCount, int maxAllowedFailures) {
        if (failedCount < maxAllowedFailures) {
            return 0;
        }

        int excess = failedCount - maxAllowedFailures + 1;
        long duration = BASE_LOCK_MINUTES * (long) Math.pow(GROWTH_FACTOR, excess);
        return Math.min(duration, MAX_LOCK_MINUTES);
    }

}