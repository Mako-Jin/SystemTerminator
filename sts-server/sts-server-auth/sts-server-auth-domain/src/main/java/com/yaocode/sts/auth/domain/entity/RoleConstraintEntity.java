package com.yaocode.sts.auth.domain.entity;

import com.yaocode.sts.auth.domain.constants.AuthI18nKeyConstants;
import com.yaocode.sts.auth.domain.enums.ConstraintTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ConstraintId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.RoleId;
import com.yaocode.sts.common.basic.enums.EnableEnums;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import lombok.Getter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 角色约束实体（属于角色聚合）
 * 对应表：auth_tbl_role_constraint
 */
@Getter
public class RoleConstraintEntity {

    private final ConstraintId constraintId;
    private final TenantId tenantId;
    private String constraintName;
    private String constraintDesc;
    private ConstraintTypeEnums constraintType;
    private Set<RoleId> roleIds;        // 互斥角色列表
    private Integer maxAssign;           // 基数约束使用
    private EnableEnums enabled;

    private RoleConstraintEntity(ConstraintId constraintId, TenantId tenantId) {
        this.constraintId = constraintId;
        this.tenantId = tenantId;
        this.roleIds = new HashSet<>();
        this.enabled = EnableEnums.ENABLED;
    }

    // ========== 工厂方法 ==========

    /**
     * 创建互斥约束
     */
    public static RoleConstraintEntity createMutex(
            TenantId tenantId,
            String constraintName,
            String constraintDesc,
            Set<RoleId> roleIds
    ) {
        if (roleIds == null || roleIds.size() < 2) {
            throw new IllegalArgumentException(AuthI18nKeyConstants.MUTEX_CONSTRAINT_REQUIRES_AT_LEAST_TWO_ROLES);
        }
        RoleConstraintEntity entity = new RoleConstraintEntity(ConstraintId.nextId(), tenantId);
        entity.constraintName = constraintName;
        entity.constraintDesc = constraintDesc;
        entity.constraintType = ConstraintTypeEnums.MUTEX;
        entity.roleIds = new HashSet<>(roleIds);
        return entity;
    }

    /**
     * 创建基数约束
     */
    public static RoleConstraintEntity createCardinality(
            TenantId tenantId,
            String constraintName,
            String constraintDesc,
            Set<RoleId> roleIds,
            Integer maxAssign
    ) {
        if (roleIds == null || roleIds.isEmpty()) {
            throw new IllegalArgumentException(AuthI18nKeyConstants.CARDINALITY_CONSTRAINT_REQUIRES_AT_LEAST_ONE_ROLE);
        }
        if (maxAssign == null || maxAssign < 1) {
            throw new IllegalArgumentException(AuthI18nKeyConstants.MAX_ASSIGNMENT_COUNT_AT_LEAST_ONE);
        }
        RoleConstraintEntity entity = new RoleConstraintEntity(ConstraintId.nextId(), tenantId);
        entity.constraintName = constraintName;
        entity.constraintDesc = constraintDesc;
        entity.constraintType = ConstraintTypeEnums.CARDINALITY;
        entity.roleIds = new HashSet<>(roleIds);
        entity.maxAssign = maxAssign;
        return entity;
    }

    public static RoleConstraintEntity reconstruct(
            ConstraintId constraintId,
            TenantId tenantId,
            String constraintName,
            String constraintDesc,
            ConstraintTypeEnums constraintType,
            Set<RoleId> roleIds,
            Integer maxAssign,
            EnableEnums enabled
    ) {
        RoleConstraintEntity entity = new RoleConstraintEntity(constraintId, tenantId);
        entity.constraintName = constraintName;
        entity.constraintDesc = constraintDesc;
        entity.constraintType = constraintType;
        entity.roleIds = roleIds != null ? new HashSet<>(roleIds) : new HashSet<>();
        entity.maxAssign = maxAssign;
        entity.enabled = enabled != null ? enabled : EnableEnums.ENABLED;
        return entity;
    }

    // ========== 业务行为 ==========

    public void addRole(RoleId roleId) {
        if (roleIds.contains(roleId)) {
            throw new IllegalArgumentException(AuthI18nKeyConstants.ROLE_ALREADY_IN_CONSTRAINT);
        }
        roleIds.add(roleId);
    }

    public void removeRole(RoleId roleId) {
        if (constraintType == ConstraintTypeEnums.MUTEX && roleIds.size() <= 2) {
            throw new IllegalArgumentException(AuthI18nKeyConstants.MUTEX_CONSTRAINT_CANNOT_REMOVE_INSUFFICIENT_ROLES);
        }
        if (constraintType == ConstraintTypeEnums.CARDINALITY && roleIds.size() <= 1) {
            throw new IllegalArgumentException(AuthI18nKeyConstants.CARDINALITY_CONSTRAINT_CANNOT_REMOVE_INSUFFICIENT_ROLES);
        }
        roleIds.remove(roleId);
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

    public boolean isMutex() {
        return constraintType == ConstraintTypeEnums.MUTEX;
    }

    public boolean isCardinality() {
        return constraintType == ConstraintTypeEnums.CARDINALITY;
    }

    /**
     * 校验是否违反约束
     */
    public boolean validate(Set<RoleId> assignedRoles) {
        if (enabled == EnableEnums.DISABLED) {
            return false;
        }

        if (constraintType == ConstraintTypeEnums.MUTEX) {
            // 互斥约束：不能同时拥有两个互斥角色
            long count = assignedRoles.stream().filter(roleIds::contains).count();
            return count <= 1;
        }

        if (constraintType == ConstraintTypeEnums.CARDINALITY) {
            // 基数约束：拥有的角色数量不能超过最大分配数
            long count = assignedRoles.stream().filter(roleIds::contains).count();
            return count <= maxAssign;
        }

        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleConstraintEntity that = (RoleConstraintEntity) o;
        return Objects.equals(constraintId, that.constraintId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(constraintId);
    }
}