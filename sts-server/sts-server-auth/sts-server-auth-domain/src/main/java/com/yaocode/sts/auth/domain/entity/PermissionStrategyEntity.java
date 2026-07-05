package com.yaocode.sts.auth.domain.entity;

import com.yaocode.sts.auth.domain.enums.StrategyTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ResourceId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.RoleId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.StrategyId;
import com.yaocode.sts.common.basic.enums.EnableEnums;
import com.yaocode.sts.common.basic.enums.YesNoEnums;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import lombok.Getter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 权限策略实体（独立实体）
 * 对应表：auth_tbl_permission_strategy
 */
@Getter
public class PermissionStrategyEntity {

    private final StrategyId strategyId;
    private final TenantId tenantId;
    private String strategyName;
    private String strategyCode;
    private String strategyDesc;
    private StrategyTypeEnums strategyType;
    private Set<RoleId> roleIds;
    private Set<ResourceId> resourceIds;
    private Integer priority;
    private YesNoEnums isDefault;
    private String conditions;        // JSON格式
    private EnableEnums enabled;

    private PermissionStrategyEntity(StrategyId strategyId, TenantId tenantId) {
        this.strategyId = strategyId;
        this.tenantId = tenantId;
        this.roleIds = new HashSet<>();
        this.resourceIds = new HashSet<>();
        this.isDefault = YesNoEnums.NO;
        this.enabled = EnableEnums.ENABLED;
    }

    // ========== 工厂方法 ==========

    public static PermissionStrategyEntity create(
            TenantId tenantId,
            String strategyName,
            String strategyCode,
            String strategyDesc,
            StrategyTypeEnums strategyType,
            Set<RoleId> roleIds,
            Set<ResourceId> resourceIds,
            Integer priority,
            String conditions
    ) {
        PermissionStrategyEntity entity = new PermissionStrategyEntity(StrategyId.nextId(), tenantId);
        entity.strategyName = strategyName;
        entity.strategyCode = strategyCode;
        entity.strategyDesc = strategyDesc;
        entity.strategyType = strategyType;
        entity.roleIds = roleIds != null ? new HashSet<>(roleIds) : new HashSet<>();
        entity.resourceIds = resourceIds != null ? new HashSet<>(resourceIds) : new HashSet<>();
        entity.priority = priority != null ? priority : 0;
        entity.conditions = conditions;
        return entity;
    }

    public static PermissionStrategyEntity reconstruct(
            StrategyId strategyId,
            TenantId tenantId,
            String strategyName,
            String strategyCode,
            String strategyDesc,
            StrategyTypeEnums strategyType,
            Set<RoleId> roleIds,
            Set<ResourceId> resourceIds,
            Integer priority,
            YesNoEnums isDefault,
            String conditions,
            EnableEnums enabled
    ) {
        PermissionStrategyEntity entity = new PermissionStrategyEntity(strategyId, tenantId);
        entity.strategyName = strategyName;
        entity.strategyCode = strategyCode;
        entity.strategyDesc = strategyDesc;
        entity.strategyType = strategyType;
        entity.roleIds = roleIds != null ? new HashSet<>(roleIds) : new HashSet<>();
        entity.resourceIds = resourceIds != null ? new HashSet<>(resourceIds) : new HashSet<>();
        entity.priority = priority != null ? priority : 0;
        entity.isDefault = isDefault != null ? isDefault : YesNoEnums.NO;
        entity.conditions = conditions;
        entity.enabled = enabled != null ? enabled : EnableEnums.ENABLED;
        return entity;
    }

    // ========== 业务行为 ==========

    public void addRole(RoleId roleId) {
        this.roleIds.add(roleId);
    }

    public void removeRole(RoleId roleId) {
        this.roleIds.remove(roleId);
    }

    public void addResource(ResourceId resourceId) {
        this.resourceIds.add(resourceId);
    }

    public void removeResource(ResourceId resourceId) {
        this.resourceIds.remove(resourceId);
    }

    public void markDefault() {
        this.isDefault = YesNoEnums.YES;
    }

    public void unmarkDefault() {
        this.isDefault = YesNoEnums.NO;
    }

    public void enable() {
        this.enabled = EnableEnums.ENABLED;
    }

    public void disable() {
        this.enabled = EnableEnums.DISABLED;
    }

    public boolean isEnabled() {
        return enabled == EnableEnums.ENABLED;
    }

    public boolean isDefaultStrategy() {
        return isDefault == YesNoEnums.YES;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PermissionStrategyEntity that = (PermissionStrategyEntity) o;
        return Objects.equals(strategyId, that.strategyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(strategyId);
    }
}
