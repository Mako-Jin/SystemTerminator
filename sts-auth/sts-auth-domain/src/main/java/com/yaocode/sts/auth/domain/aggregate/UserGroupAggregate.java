package com.yaocode.sts.auth.domain.aggregate;

import com.yaocode.sts.auth.domain.enums.UserGroupCategoryEnums;
import com.yaocode.sts.auth.domain.events.usergroup.RoleBoundToGroupEvent;
import com.yaocode.sts.auth.domain.events.usergroup.RoleUnboundFromGroupEvent;
import com.yaocode.sts.auth.domain.events.usergroup.RolesBoundToGroupEvent;
import com.yaocode.sts.auth.domain.events.usergroup.UserAddedToGroupEvent;
import com.yaocode.sts.auth.domain.events.usergroup.UserGroupCreatedEvent;
import com.yaocode.sts.auth.domain.events.usergroup.UserGroupDisabledEvent;
import com.yaocode.sts.auth.domain.events.usergroup.UserGroupEnabledEvent;
import com.yaocode.sts.auth.domain.events.usergroup.UserRemovedFromGroupEvent;
import com.yaocode.sts.auth.domain.events.usergroup.UsersAddedToGroupEvent;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.RoleId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserGroupId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.UserGroupCode;
import com.yaocode.sts.common.basic.enums.EnableEnums;
import com.yaocode.sts.common.basic.enums.YesNoEnums;
import com.yaocode.sts.common.domain.exception.DomainException;
import com.yaocode.sts.common.domain.model.AbstractAggregate;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import com.yaocode.sts.common.domain.valueobject.UserId;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

/**
 * 用户组聚合根
 * 管理用户组的定义、成员和绑定的角色
 * 对应表：auth_tbl_user_group
 */
@Getter
public class UserGroupAggregate extends AbstractAggregate<UserGroupId> {

    // ============ 核心属性 ============
    private TenantId tenantId;
    private UserGroupCode userGroupCode;
    private String userGroupName;
    private String userGroupDesc;
    private UserGroupId parentId;
    private UserGroupCategoryEnums category;
    private EnableEnums enabled;
    private String filters;           // 动态用户组过滤条件（JSON）
    private String orgIdsList;        // 组织ID列表
    private Integer sort;
    private YesNoEnums isDefault;

    // ============ 跨聚合引用 ============
    private Set<UserId> userIds = new HashSet<>();
    private Set<RoleId> roleIds = new HashSet<>();

    // ============ 子用户组 ============
    private Set<UserGroupId> childGroupIds = new HashSet<>();

    // ============ 构造函数 ============
    private UserGroupAggregate(UserGroupId userGroupId) {
        super(userGroupId);
        this.enabled = EnableEnums.ENABLED;
        this.isDefault = YesNoEnums.NO;
        this.category = UserGroupCategoryEnums.STATIC;
        this.sort = 0;
    }

    // ============ 工厂方法 ============

    /**
     * 创建用户组
     */
    public static UserGroupAggregate create(
            TenantId tenantId,
            UserGroupCode userGroupCode,
            String userGroupName,
            String userGroupDesc,
            UserGroupCategoryEnums category,
            UserGroupId parentId,
            String filters,
            String orgIdsList,
            Integer sort
    ) {
        UserGroupAggregate group = new UserGroupAggregate(UserGroupId.nextId());
        group.tenantId = tenantId;
        group.userGroupCode = userGroupCode;
        group.userGroupName = userGroupName;
        group.userGroupDesc = userGroupDesc != null ? userGroupDesc.trim() : "";
        group.category = category != null ? category : UserGroupCategoryEnums.STATIC;
        group.parentId = parentId;
        group.filters = filters;
        group.orgIdsList = orgIdsList;
        group.sort = sort != null ? sort : 0;

        group.registerEvent(new UserGroupCreatedEvent(group.getId(), userGroupName));

        return group;
    }

    /**
     * 创建动态用户组
     */
    public static UserGroupAggregate createDynamic(
            TenantId tenantId,
            UserGroupCode userGroupCode,
            String userGroupName,
            String userGroupDesc,
            String filters
    ) {
        return create(
                tenantId,
                userGroupCode,
                userGroupName,
                userGroupDesc,
                UserGroupCategoryEnums.DYNAMIC,
                null,
                filters,
                null,
                0
        );
    }

    /**
     * 从数据库重建用户组聚合
     */
    public static UserGroupAggregate reconstruct(
            UserGroupId userGroupId,
            TenantId tenantId,
            UserGroupCode userGroupCode,
            String userGroupName,
            String userGroupDesc,
            UserGroupCategoryEnums category,
            UserGroupId parentId,
            EnableEnums enabled,
            String filters,
            String orgIdsList,
            Integer sort,
            YesNoEnums isDefault,
            Set<UserId> userIds,
            Set<RoleId> roleIds,
            Set<UserGroupId> childGroupIds
    ) {
        UserGroupAggregate group = new UserGroupAggregate(userGroupId);
        group.tenantId = tenantId;
        group.userGroupCode = userGroupCode;
        group.userGroupName = userGroupName;
        group.userGroupDesc = userGroupDesc;
        group.category = category != null ? category : UserGroupCategoryEnums.STATIC;
        group.parentId = parentId;
        group.enabled = enabled != null ? enabled : EnableEnums.ENABLED;
        group.filters = filters;
        group.orgIdsList = orgIdsList;
        group.sort = sort != null ? sort : 0;
        group.isDefault = isDefault != null ? isDefault : YesNoEnums.NO;
        group.userIds = userIds != null ? new HashSet<>(userIds) : new HashSet<>();
        group.roleIds = roleIds != null ? new HashSet<>(roleIds) : new HashSet<>();
        group.childGroupIds = childGroupIds != null ? new HashSet<>(childGroupIds) : new HashSet<>();
        return group;
    }

    // ============ 业务行为 ============

    // ----- 基本信息管理 -----

    /**
     * 更新用户组名称
     */
    public void updateName(String userGroupName) {
        if (userGroupName == null || userGroupName.trim().isEmpty()) {
            throw new DomainException("用户组名称不能为空");
        }
        this.userGroupName = userGroupName.trim();
    }

    /**
     * 更新用户组描述
     */
    public void updateDescription(String userGroupDesc) {
        this.userGroupDesc = userGroupDesc;
    }

    /**
     * 更新父用户组
     */
    public void updateParent(UserGroupId parentId) {
        if (parentId != null && parentId.equals(this.getId())) {
            throw new DomainException("用户组不能继承自己");
        }
        this.parentId = parentId;
    }

    /**
     * 更新排序
     */
    public void updateSort(Integer sort) {
        this.sort = sort != null ? sort : 0;
    }

    // ----- 状态管理 -----

    /**
     * 启用用户组
     */
    public void enable() {
        if (this.enabled == EnableEnums.ENABLED) {
            throw new DomainException("用户组已启用");
        }
        this.enabled = EnableEnums.ENABLED;
        registerEvent(new UserGroupEnabledEvent(this.getId()));
    }

    /**
     * 禁用用户组
     */
    public void disable() {
        if (this.enabled == EnableEnums.DISABLED) {
            throw new DomainException("用户组已禁用");
        }
        this.enabled = EnableEnums.DISABLED;
        registerEvent(new UserGroupDisabledEvent(this.getId()));
    }

    /**
     * 设为默认用户组
     */
    public void setDefault() {
        this.isDefault = YesNoEnums.YES;
    }

    /**
     * 取消默认用户组
     */
    public void unsetDefault() {
        this.isDefault = YesNoEnums.NO;
    }

    // ----- 成员管理 -----

    /**
     * 添加用户
     */
    public void addUser(UserId userId) {
        if (this.enabled == EnableEnums.ENABLED) {
            throw new DomainException("用户组已禁用，不能添加用户");
        }
        if (userIds.contains(userId)) {
            throw new DomainException("用户已在该用户组中");
        }
        userIds.add(userId);
        registerEvent(new UserAddedToGroupEvent(this.getId(), userId));
    }

    /**
     * 移除用户
     */
    public void removeUser(UserId userId) {
        if (!userIds.contains(userId)) {
            throw new DomainException("用户不在该用户组中");
        }
        userIds.remove(userId);
        registerEvent(new UserRemovedFromGroupEvent(this.getId(), userId));
    }

    /**
     * 批量添加用户
     */
    public void addUsers(Set<UserId> userIds) {
        if (this.enabled == EnableEnums.DISABLED) {
            throw new DomainException("用户组已禁用，不能添加用户");
        }
        this.userIds.addAll(userIds);
        registerEvent(new UsersAddedToGroupEvent(this.getId(), userIds));
    }

    // ----- 角色管理 -----

    /**
     * 绑定角色
     */
    public void bindRole(RoleId roleId) {
        if (this.enabled == EnableEnums.DISABLED) {
            throw new DomainException("用户组已禁用，不能绑定角色");
        }
        if (roleIds.contains(roleId)) {
            throw new DomainException("角色已绑定到该用户组");
        }
        roleIds.add(roleId);
        registerEvent(new RoleBoundToGroupEvent(this.getId(), roleId));
    }

    /**
     * 解绑角色
     */
    public void unbindRole(RoleId roleId) {
        if (!roleIds.contains(roleId)) {
            throw new DomainException("角色未绑定到该用户组");
        }
        roleIds.remove(roleId);
        registerEvent(new RoleUnboundFromGroupEvent(this.getId(), roleId));
    }

    /**
     * 批量绑定角色
     */
    public void bindRoles(Set<RoleId> roleIds) {
        if (this.enabled == EnableEnums.DISABLED) {
            throw new DomainException("用户组已禁用，不能绑定角色");
        }
        this.roleIds.addAll(roleIds);
        registerEvent(new RolesBoundToGroupEvent(this.getId(), roleIds));
    }

    // ----- 查询方法 -----

    /**
     * 判断用户组是否启用
     */
    public boolean isEnabled() {
        return enabled == EnableEnums.ENABLED;
    }

    /**
     * 判断是否为动态用户组
     */
    public boolean isDynamic() {
        return category.isDynamic();
    }

    /**
     * 判断是否为默认用户组
     */
    public boolean isDefaultGroup() {
        return isDefault == YesNoEnums.YES;
    }

    /**
     * 获取成员数量
     */
    public int getMemberCount() {
        return userIds.size();
    }

    /**
     * 获取绑定的角色数量
     */
    public int getRoleCount() {
        return roleIds.size();
    }
}
