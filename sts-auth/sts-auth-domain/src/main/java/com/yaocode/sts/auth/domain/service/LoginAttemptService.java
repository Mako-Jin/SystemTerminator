package com.yaocode.sts.auth.domain.service;

import com.yaocode.sts.auth.domain.entity.LoginAttemptEntity;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import com.yaocode.sts.common.domain.valueobject.UserId;

public interface LoginAttemptService {

    /**
     * 检查用户是否被锁定
     */
    boolean isLocked(UserId userId, TenantId tenantId);

    /**
     * 记录登录失败
     */
    void recordFailedAttempt(UserId userId, TenantId tenantId, String ipAddress, String reason);

    /**
     * 记录登录成功，清除失败记录
     */
    void clearFailedAttempts(UserId userId, TenantId tenantId);

    /**
     * 获取剩余锁定时间（分钟）
     */
    long getRemainingLockMinutes(UserId userId, TenantId tenantId);

    /**
     * 获取剩余尝试次数
     */
    int getRemainingAttempts(UserId userId, TenantId tenantId);

    /**
     * 获取登录尝试聚合根
     */
    LoginAttemptEntity getLoginAttempt(UserId userId, TenantId tenantId);

}
