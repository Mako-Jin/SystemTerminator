package com.yaocode.sts.auth.domain.service;

import com.yaocode.sts.auth.domain.entity.BlackWhiteListEntity;
import com.yaocode.sts.auth.domain.valueobjects.primitives.IpAddress;
import com.yaocode.sts.common.domain.valueobject.TenantId;

import java.util.List;

public interface BlackWhiteListDomainService {

    /**
     * 检查IP是否被阻止
     */
    boolean isIpBlocked(IpAddress ipAddress, TenantId tenantId);

    /**
     * 检查设备是否被阻止
     */
    boolean isDeviceBlocked(String deviceId, TenantId tenantId);

    /**
     * 检查用户是否被阻止
     */
    boolean isUserBlocked(String userId, TenantId tenantId);

    /**
     * 获取所有生效的黑白名单规则
     */
    List<BlackWhiteListEntity> getEffectiveRules(TenantId tenantId);

}
