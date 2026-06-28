package com.yaocode.sts.auth.domain.repository;

import com.yaocode.sts.auth.domain.entity.DeviceInfoEntity;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.DeviceId;
import com.yaocode.sts.common.domain.Repository;
import com.yaocode.sts.common.domain.valueobject.TenantId;

import java.util.Optional;

public interface DeviceInfoRepository extends Repository<DeviceInfoEntity, DeviceId> {

    Optional<DeviceInfoEntity> findByDeviceIdAndTenantId(DeviceId deviceId, TenantId tenantId);

}
