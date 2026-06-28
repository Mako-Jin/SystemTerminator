package com.yaocode.sts.auth.domain.repository;

import com.yaocode.sts.auth.domain.entity.LoginAttemptEntity;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.LoginAttemptId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.IpAddress;
import com.yaocode.sts.common.domain.Repository;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import com.yaocode.sts.common.domain.valueobject.UserId;

public interface LoginAttemptRepository extends Repository<LoginAttemptEntity, LoginAttemptId> {

    /**
     * 根据用户ID和租户ID查询登录尝试记录
     */
    LoginAttemptEntity findByUserIdAndTenantId(UserId userId, TenantId tenantId);

    /**
     * 记录登录失败
     */
    void recordFailedAttempt(UserId userId, TenantId tenantId, IpAddress ipAddress, String failReason);

    /**
     * 清除失败记录（登录成功后）
     */
    void clearFailedAttempts(UserId userId, TenantId tenantId);

    /**
     * 重置尝试次数（解锁后）
     */
    void resetAttempts(UserId userId, TenantId tenantId);

}
