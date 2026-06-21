package com.yaocode.sts.auth.domain.aggregate;

import com.yaocode.sts.auth.domain.entity.RoleConstraintEntity;
import com.yaocode.sts.auth.domain.entity.RoleResourceEntity;
import com.yaocode.sts.auth.domain.enums.InheritStrategyEnums;
import com.yaocode.sts.auth.domain.enums.RoleCategoryEnums;
import com.yaocode.sts.auth.domain.events.role.ResourceAssignedToRoleEvent;
import com.yaocode.sts.auth.domain.events.role.ResourceRemovedFromRoleEvent;
import com.yaocode.sts.auth.domain.events.role.ResourcesAssignedToRoleEvent;
import com.yaocode.sts.auth.domain.events.role.RoleCreatedEvent;
import com.yaocode.sts.auth.domain.events.role.RoleDisabledEvent;
import com.yaocode.sts.auth.domain.events.role.RoleEnabledEvent;
import com.yaocode.sts.auth.domain.events.role.UserAssignedToRoleEvent;
import com.yaocode.sts.auth.domain.events.role.UserGroupAssignedToRoleEvent;
import com.yaocode.sts.auth.domain.events.role.UserRemovedFromRoleEvent;
import com.yaocode.sts.auth.domain.events.role.UsersAssignedToRoleEvent;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ConstraintId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ResourceId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.RoleId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserGroupId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.RoleCode;
import com.yaocode.sts.common.basic.enums.OppositeEnums;
import com.yaocode.sts.common.domain.exception.DomainException;
import com.yaocode.sts.common.domain.model.AbstractAggregate;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import com.yaocode.sts.common.domain.valueobject.UserId;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 角色聚合根
 * 管理角色的定义、权限资源绑定、成员分配、约束等
 * 对应表：auth_tbl_role_info
 */
@Getter
public class RoleInfoAggregate extends AbstractAggregate<RoleId> {

    // ============ 核心属性 ============
    private TenantId tenantId;
    private RoleCode roleCode;
    private String roleName;
    private String roleDesc;
    private RoleCategoryEnums category;
    private InheritStrategyEnums inheritStrategy;
    private RoleId parentId;
    private OppositeEnums isDefault;
    private OppositeEnums status;
    private Integer roleLevel;
    private String filters;      // 动态角色过滤条件（JSON）

    // ============ 子实体 ============
    private List<RoleResourceEntity> resources = new ArrayList<>();
    private List<RoleConstraintEntity> constraints = new ArrayList<>();

    // ============ 跨聚合引用 ============
    private Set<UserId> memberIds = new HashSet<>();
    private Set<UserGroupId> memberGroupIds = new HashSet<>();

    // ============ 构造函数 ============
    private RoleInfoAggregate(RoleId roleId) {
        super(roleId);
        this.status = OppositeEnums.YES;
        this.category = RoleCategoryEnums.STATIC;
        this.inheritStrategy = InheritStrategyEnums.NONE;
        this.isDefault = OppositeEnums.NO;
        this.roleLevel = 1;
    }

    // ============ 工厂方法 ============

    /**
     * 创建角色
     */
    public static RoleInfoAggregate create(
            TenantId tenantId,
            RoleCode roleCode,
            String roleName,
            String roleDesc,
            RoleCategoryEnums category,
            InheritStrategyEnums inheritStrategy,
            RoleId parentId,
            String filters
    ) {
        RoleInfoAggregate role = new RoleInfoAggregate(RoleId.nextId());
        role.tenantId = tenantId;
        role.roleCode = roleCode;
        role.roleName = roleName;
        role.roleDesc = roleDesc != null ? roleDesc.trim() : "";
        role.category = category != null ? category : RoleCategoryEnums.STATIC;
        role.inheritStrategy = inheritStrategy != null ? inheritStrategy : InheritStrategyEnums.NONE;
        role.parentId = parentId;
        role.filters = filters;

        // 如果有父角色，层级+1
        if (parentId != null) {
            role.roleLevel = 2;
        }

        role.registerEvent(new RoleCreatedEvent(role.getId(), roleName));

        return role;
    }

    /**
     * 创建动态角色
     */
    public static RoleInfoAggregate createDynamic(
            TenantId tenantId,
            RoleCode roleCode,
            String roleName,
            String roleDesc,
            String filters
    ) {
        return create(
                tenantId,
                roleCode,
                roleName,
                roleDesc,
                RoleCategoryEnums.DYNAMIC,
                InheritStrategyEnums.NONE,
                null,
                filters
        );
    }

    /**
     * 从数据库重建角色聚合
     */
    public static RoleInfoAggregate reconstruct(
            RoleId roleId,
            TenantId tenantId,
            RoleCode roleCode,
            String roleName,
            String roleDesc,
            RoleCategoryEnums category,
            InheritStrategyEnums inheritStrategy,
            RoleId parentId,
            OppositeEnums isDefault,
            OppositeEnums status,
            Integer roleLevel,
            String filters,
            List<RoleResourceEntity> resources,
            List<RoleConstraintEntity> constraints,
            Set<UserId> memberIds,
            Set<UserGroupId> memberGroupIds
    ) {
        RoleInfoAggregate role = new RoleInfoAggregate(roleId);
        role.tenantId = tenantId;
        role.roleCode = roleCode;
        role.roleName = roleName;
        role.roleDesc = roleDesc;
        role.category = category != null ? category : RoleCategoryEnums.STATIC;
        role.inheritStrategy = inheritStrategy != null ? inheritStrategy : InheritStrategyEnums.NONE;
        role.parentId = parentId;
        role.isDefault = isDefault != null ? isDefault : OppositeEnums.NO;
        role.status = status != null ? status : OppositeEnums.YES;
        role.roleLevel = roleLevel != null ? roleLevel : 1;
        role.filters = filters;
        role.resources = resources != null ? new ArrayList<>(resources) : new ArrayList<>();
        role.constraints = constraints != null ? new ArrayList<>(constraints) : new ArrayList<>();
        role.memberIds = memberIds != null ? new HashSet<>(memberIds) : new HashSet<>();
        role.memberGroupIds = memberGroupIds != null ? new HashSet<>(memberGroupIds) : new HashSet<>();
        return role;
    }

    // ============ 业务行为 ============

    // ----- 基本信息管理 -----

    /**
     * 更新角色名称
     */
    public void updateName(String roleName) {
        if (roleName == null || roleName.trim().isEmpty()) {
            throw new DomainException("角色名称不能为空");
        }
        this.roleName = roleName.trim();
    }

    /**
     * 更新角色描述
     */
    public void updateDescription(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    /**
     * 更新继承策略
     */
    public void updateInheritStrategy(InheritStrategyEnums inheritStrategy) {
        this.inheritStrategy = inheritStrategy;
    }

    /**
     * 更新父角色
     */
    public void updateParent(RoleId parentId) {
        // 校验不能形成循环继承
        if (parentId != null && parentId.equals(this.getId())) {
            throw new DomainException("角色不能继承自己");
        }
        this.parentId = parentId;
    }

    // ----- 状态管理 -----

    /**
     * 启用角色
     */
    public void enable() {
        this.status = OppositeEnums.YES;
        registerEvent(new RoleEnabledEvent(this.getId()));
    }

    /**
     * 禁用角色
     */
    public void disable() {
        this.status = OppositeEnums.NO;
        registerEvent(new RoleDisabledEvent(this.getId()));
    }

    /**
     * 设为默认角色
     */
    public void setDefault() {
        this.isDefault = OppositeEnums.YES;
    }

    /**
     * 取消默认角色
     */
    public void unsetDefault() {
        this.isDefault = OppositeEnums.NO;
    }

    // ----- 资源管理 -----

    /**
     * 分配资源
     */
    public void assignResource(ResourceId resourceId, OppositeEnums effect, Integer priority) {
        boolean exists = resources.stream()
                .anyMatch(r -> r.getResourceId().equals(resourceId));
        if (exists) {
            throw new DomainException("资源已分配给该角色");
        }

        resources.add(RoleResourceEntity.create(
                this.getId(),
                resourceId,
                effect != null ? effect : OppositeEnums.YES,
                priority != null ? priority : 0
        ));
        registerEvent(new ResourceAssignedToRoleEvent(this.getId(), resourceId));
    }

    /**
     * 移除资源
     */
    public void removeResource(ResourceId resourceId) {
        boolean removed = resources.removeIf(r -> r.getResourceId().equals(resourceId));
        if (!removed) {
            throw new DomainException("资源未分配给该角色");
        }
        registerEvent(new ResourceRemovedFromRoleEvent(this.getId(), resourceId));
    }

    /**
     * 批量分配资源
     */
    public void assignResources(Map<ResourceId, OppositeEnums> resourceEffects) {
        for (Map.Entry<ResourceId, OppositeEnums> entry : resourceEffects.entrySet()) {
            boolean exists = resources.stream()
                    .anyMatch(r -> r.getResourceId().equals(entry.getKey()));
            if (!exists) {
                resources.add(RoleResourceEntity.create(
                        this.getId(),
                        entry.getKey(),
                        entry.getValue(),
                        0
                ));
            }
        }
        registerEvent(new ResourcesAssignedToRoleEvent(this.getId(), resourceEffects.keySet()));
    }

    // ----- 约束管理 -----

    /**
     * 添加约束
     */
    public void addConstraint(RoleConstraintEntity constraint) {
        constraints.add(constraint);
    }

    /**
     * 移除约束
     */
    public void removeConstraint(ConstraintId constraintId) {
        constraints.removeIf(c -> c.getConstraintId().equals(constraintId));
    }

    /**
     * 验证约束
     */
    public boolean validateConstraints(Set<RoleId> assignedRoles) {
        for (RoleConstraintEntity constraint : constraints) {
            if (!constraint.isEnabled()) {
                continue;
            }
            if (!constraint.validate(assignedRoles)) {
                return false;
            }
        }
        return true;
    }

    // ----- 成员管理 -----

    /**
     * 添加用户成员
     */
    public void addMember(UserId userId) {
        if (memberIds.contains(userId)) {
            throw new DomainException("用户已在该角色中");
        }
        memberIds.add(userId);
        registerEvent(new UserAssignedToRoleEvent(this.getId(), userId));
    }

    /**
     * 移除用户成员
     */
    public void removeMember(UserId userId) {
        if (!memberIds.contains(userId)) {
            throw new DomainException("用户不在该角色中");
        }
        memberIds.remove(userId);
        registerEvent(new UserRemovedFromRoleEvent(this.getId(), userId));
    }

    /**
     * 批量添加用户成员
     */
    public void addMembers(Set<UserId> userIds) {
        this.memberIds.addAll(userIds);
        registerEvent(new UsersAssignedToRoleEvent(this.getId(), userIds));
    }

    /**
     * 添加用户组成员
     */
    public void addMemberGroup(UserGroupId userGroupId) {
        if (memberGroupIds.contains(userGroupId)) {
            throw new DomainException("用户组已在该角色中");
        }
        memberGroupIds.add(userGroupId);
        registerEvent(new UserGroupAssignedToRoleEvent(this.getId(), userGroupId));
    }

    /**
     * 移除用户组成员
     */
    public void removeMemberGroup(UserGroupId userGroupId) {
        memberGroupIds.remove(userGroupId);
    }

    // ----- 查询方法 -----

    /**
     * 判断角色是否启用
     */
    public boolean isEnabled() {
        return status == OppositeEnums.YES;
    }

    /**
     * 判断是否为默认角色
     */
    public boolean isDefaultRole() {
        return isDefault == OppositeEnums.YES;
    }

    /**
     * 判断是否为动态角色
     */
    public boolean isDynamic() {
        return category.isDynamic();
    }

    /**
     * 判断是否有权限
     */
    public boolean hasPermission(ResourceId resourceId) {
        return resources.stream()
                .anyMatch(r ->
                        r.getResourceId().equals(resourceId) && r.getEffect() == OppositeEnums.YES
                );
    }

    /**
     * 获取所有允许的资源
     */
    public List<ResourceId> getAllowedResources() {
        return resources.stream()
                .filter(r -> r.getEffect() == OppositeEnums.YES)
                .map(RoleResourceEntity::getResourceId)
                .toList();
    }

    /**
     * 获取成员数量
     */
    public int getMemberCount() {
        return memberIds.size() + memberGroupIds.size();
    }
}