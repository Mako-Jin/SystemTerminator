package com.yaocode.sts.auth.domain.entity;

import com.yaocode.sts.auth.domain.constants.AuthI18nKeyConstants;
import com.yaocode.sts.auth.domain.enums.EnvironmentTypeEnums;
import com.yaocode.sts.auth.domain.enums.InstanceTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.InstanceId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.Version;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import lombok.Getter;

import java.util.Objects;

/**
 * 实例信息实体（属于租户聚合）
 * 对应表：auth_tbl_instance_info
 */
@Getter
public class InstanceInfoEntity {

    private final InstanceId instanceId;
    private final TenantId tenantId;
    private String instanceName;
    private String region;
    private String serverAddress;
    private Integer serverPort;
    private InstanceStatus status;
    private InstanceTypeEnums instanceType;
    private EnvironmentTypeEnums environment;
    private Version version;
    private String description;

    /**
     * 实例状态枚举（内部使用）
     */
    @Getter
    public enum InstanceStatus {
        STOPPED(0, "已停止"),
        RUNNING(1, "运行中"),
        ERROR(2, "异常");

        private final int code;
        private final String desc;

        InstanceStatus(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public static InstanceStatus fromCode(int code) {
            for (InstanceStatus status : values()) {
                if (status.code == code) {
                    return status;
                }
            }
            throw new IllegalArgumentException(AuthI18nKeyConstants.UNKNOWN_INSTANCE_STATUS);
        }
    }

    private InstanceInfoEntity(InstanceId instanceId, TenantId tenantId) {
        this.instanceId = instanceId;
        this.tenantId = tenantId;
        this.status = InstanceStatus.STOPPED;
        this.version = Version.initial();
    }

    // ========== 工厂方法 ==========

    public static InstanceInfoEntity create(
            TenantId tenantId,
            String instanceName,
            String region,
            String serverAddress,
            Integer serverPort,
            InstanceTypeEnums instanceType,
            EnvironmentTypeEnums environment,
            String description
    ) {
        InstanceInfoEntity entity = new InstanceInfoEntity(InstanceId.nextId(), tenantId);
        entity.instanceName = instanceName;
        entity.region = region;
        entity.serverAddress = serverAddress;
        entity.serverPort = serverPort;
        entity.instanceType = instanceType;
        entity.environment = environment;
        entity.description = description;
        return entity;
    }

    public static InstanceInfoEntity reconstruct(
            InstanceId instanceId,
            TenantId tenantId,
            String instanceName,
            String region,
            String serverAddress,
            Integer serverPort,
            InstanceStatus status,
            InstanceTypeEnums instanceType,
            EnvironmentTypeEnums environment,
            Version version,
            String description
    ) {
        InstanceInfoEntity entity = new InstanceInfoEntity(instanceId, tenantId);
        entity.instanceName = instanceName;
        entity.region = region;
        entity.serverAddress = serverAddress;
        entity.serverPort = serverPort;
        entity.status = status != null ? status : InstanceStatus.STOPPED;
        entity.instanceType = instanceType;
        entity.environment = environment;
        entity.version = version != null ? version : Version.initial();
        entity.description = description;
        return entity;
    }

    // ========== 业务行为 ==========

    public void start() {
        this.status = InstanceStatus.RUNNING;
        this.version = version.bumpBuild();
    }

    public void stop() {
        this.status = InstanceStatus.STOPPED;
        this.version = version.bumpBuild();
    }

    public void markError() {
        this.status = InstanceStatus.ERROR;
        this.version = version.bumpPatch();
    }

    public void updateConfiguration(
            String instanceName,
            String region,
            String serverAddress,
            Integer serverPort,
            String description
    ) {
        this.instanceName = instanceName;
        this.region = region;
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.description = description;
        this.version = version.bumpMinor();
    }

    public void upgrade(Version newVersion) {
        if (newVersion.compareTo(this.version) <= 0) {
            throw new IllegalArgumentException(AuthI18nKeyConstants.NEW_VERSION_MUST_BE_HIGHER);
        }
        this.version = newVersion;
    }

    public boolean isRunning() {
        return status == InstanceStatus.RUNNING;
    }

    public boolean isProduction() {
        return environment == EnvironmentTypeEnums.PRODUCTION;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstanceInfoEntity that = (InstanceInfoEntity) o;
        return Objects.equals(instanceId, that.instanceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instanceId);
    }
}