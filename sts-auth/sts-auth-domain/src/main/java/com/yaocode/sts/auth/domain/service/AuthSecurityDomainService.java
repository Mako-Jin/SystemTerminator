package com.yaocode.sts.auth.domain.service;

import com.yaocode.sts.auth.domain.valueobjects.composites.SecurityStatus;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.DeviceId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.IpAddress;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import com.yaocode.sts.common.domain.valueobject.UserId;

public interface AuthSecurityDomainService {

    SecurityStatus checkSecurityStatus(UserId userId, TenantId tenantId, DeviceId deviceId, IpAddress ipAddress);

    void recordLoginFailure(UserId userId, TenantId tenantId, IpAddress ipAddress);

    void clearLoginFailures(UserId userId, TenantId tenantId);

}
