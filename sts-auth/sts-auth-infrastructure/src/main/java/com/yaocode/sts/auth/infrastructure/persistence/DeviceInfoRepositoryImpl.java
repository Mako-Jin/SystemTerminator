package com.yaocode.sts.auth.infrastructure.persistence;

import com.yaocode.sts.auth.domain.entity.DeviceInfoEntity;
import com.yaocode.sts.auth.domain.repository.DeviceInfoRepository;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.DeviceId;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class DeviceInfoRepositoryImpl implements DeviceInfoRepository {
    @Override
    public Optional<DeviceInfoEntity> findByDeviceIdAndTenantId(DeviceId deviceId, TenantId tenantId) {
        return Optional.empty();
    }

    @Override
    public Optional<DeviceInfoEntity> findById(DeviceId deviceId) {
        return Optional.empty();
    }

    @Override
    public DeviceId save(DeviceInfoEntity aggregate) {
        return null;
    }

    @Override
    public void delete(DeviceInfoEntity aggregate) {

    }
}
