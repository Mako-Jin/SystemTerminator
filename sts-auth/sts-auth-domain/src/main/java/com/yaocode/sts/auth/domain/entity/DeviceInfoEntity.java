package com.yaocode.sts.auth.domain.entity;

import com.yaocode.sts.auth.domain.enums.DeviceStatusEnums;
import com.yaocode.sts.auth.domain.enums.DeviceTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.DeviceId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.DeviceFingerprint;
import com.yaocode.sts.auth.domain.valueobjects.primitives.IpAddress;
import com.yaocode.sts.common.basic.enums.OppositeEnums;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import com.yaocode.sts.common.domain.valueobject.UserId;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 设备实体（独立实体，非聚合根）
 * 对应表：auth_tbl_device_info
 */
@Getter
public class DeviceInfoEntity {

    private final DeviceId deviceId;
    private final UserId userId;
    private final TenantId tenantId;
    private final DeviceFingerprint deviceFingerprint;
    private DeviceTypeEnums deviceType;
    private String deviceName;
    private String osName;
    private String osVersion;
    private String appVersion;
    private IpAddress lastIpAddress;
    private String userAgent;
    private OppositeEnums isTrusted;
    private OppositeEnums jailBroken;
    private DeviceStatusEnums status;
    private LocalDateTime lastActiveTime;
    private String extras;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    private DeviceInfoEntity(DeviceId deviceId, UserId userId, TenantId tenantId, DeviceFingerprint deviceFingerprint) {
        this.deviceId = deviceId;
        this.userId = userId;
        this.tenantId = tenantId;
        this.deviceFingerprint = deviceFingerprint;
        this.isTrusted = OppositeEnums.NO;
        this.jailBroken = OppositeEnums.NO;
        this.status = DeviceStatusEnums.ACTIVE;
        this.lastActiveTime = LocalDateTime.now();
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }

    // ========== 工厂方法 ==========

    public static DeviceInfoEntity create(
            UserId userId,
            TenantId tenantId,
            DeviceFingerprint deviceFingerprint,
            DeviceTypeEnums deviceType,
            String deviceName,
            String osName,
            String osVersion,
            String appVersion,
            IpAddress lastIpAddress,
            String userAgent,
            String extras
    ) {
        DeviceInfoEntity entity = new DeviceInfoEntity(DeviceId.nextId(), userId, tenantId, deviceFingerprint);
        entity.deviceType = deviceType;
        entity.deviceName = deviceName;
        entity.osName = osName;
        entity.osVersion = osVersion;
        entity.appVersion = appVersion;
        entity.lastIpAddress = lastIpAddress;
        entity.userAgent = userAgent;
        entity.extras = extras;
        return entity;
    }

    public static DeviceInfoEntity reconstruct(
            DeviceId deviceId,
            UserId userId,
            TenantId tenantId,
            DeviceFingerprint deviceFingerprint,
            DeviceTypeEnums deviceType,
            String deviceName,
            String osName,
            String osVersion,
            String appVersion,
            IpAddress lastIpAddress,
            String userAgent,
            OppositeEnums isTrusted,
            OppositeEnums jailBroken,
            DeviceStatusEnums status,
            LocalDateTime lastActiveTime,
            String extras,
            LocalDateTime createTime,
            LocalDateTime updateTime
    ) {
        DeviceInfoEntity entity = new DeviceInfoEntity(deviceId, userId, tenantId, deviceFingerprint);
        entity.deviceType = deviceType;
        entity.deviceName = deviceName;
        entity.osName = osName;
        entity.osVersion = osVersion;
        entity.appVersion = appVersion;
        entity.lastIpAddress = lastIpAddress;
        entity.userAgent = userAgent;
        entity.isTrusted = isTrusted != null ? isTrusted : OppositeEnums.NO;
        entity.jailBroken = jailBroken != null ? jailBroken : OppositeEnums.NO;
        entity.status = status != null ? status : DeviceStatusEnums.ACTIVE;
        entity.lastActiveTime = lastActiveTime;
        entity.extras = extras;
        entity.createTime = createTime;
        entity.updateTime = updateTime;
        return entity;
    }

    // ========== 业务行为 ==========

    public void updateActivity(IpAddress ipAddress, String userAgent) {
        this.lastIpAddress = ipAddress;
        this.userAgent = userAgent;
        this.lastActiveTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
        if (this.status == DeviceStatusEnums.INACTIVE) {
            this.status = DeviceStatusEnums.ACTIVE;
        }
    }

    public void trust() {
        this.isTrusted = OppositeEnums.YES;
        this.updateTime = LocalDateTime.now();
    }

    public void unTrust() {
        this.isTrusted = OppositeEnums.NO;
        this.updateTime = LocalDateTime.now();
    }

    public void block() {
        this.status = DeviceStatusEnums.BLOCKED;
        this.updateTime = LocalDateTime.now();
    }

    public void unblock() {
        if (this.status == DeviceStatusEnums.BLOCKED) {
            this.status = DeviceStatusEnums.ACTIVE;
            this.updateTime = LocalDateTime.now();
        }
    }

    public void updateInfo(
            String deviceName,
            String osName,
            String osVersion,
            String appVersion,
            String extras
    ) {
        this.deviceName = deviceName;
        this.osName = osName;
        this.osVersion = osVersion;
        this.appVersion = appVersion;
        this.extras = extras;
        this.updateTime = LocalDateTime.now();
    }

    public boolean isValid() {
        return status == DeviceStatusEnums.ACTIVE || status == DeviceStatusEnums.INACTIVE;
    }

    public boolean isBlocked() {
        return status == DeviceStatusEnums.BLOCKED;
    }

    public boolean isTrustedDevice() {
        return isTrusted == OppositeEnums.YES;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceInfoEntity that = (DeviceInfoEntity) o;
        return Objects.equals(deviceId, that.deviceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deviceId);
    }
}
