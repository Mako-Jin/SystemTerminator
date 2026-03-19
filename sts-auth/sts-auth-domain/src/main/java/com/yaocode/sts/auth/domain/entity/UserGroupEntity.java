package com.yaocode.sts.auth.domain.entity;

import com.yaocode.sts.auth.domain.service.RoleDomainService;
import com.yaocode.sts.auth.domain.service.TenantDomainService;
import com.yaocode.sts.auth.domain.service.UserGroupDomainService;
import com.yaocode.sts.auth.domain.service.UserInfoDomainService;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.RoleId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.TenantId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserGroupId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.UserGroupCode;
import com.yaocode.sts.common.domain.DomainException;
import com.yaocode.sts.common.domain.model.AbstractAggregate;
import lombok.Getter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 用户组领域对象
 * @author: Jin-LiangBo
 * @date: 2025年10月13日 22:57
 */
@Getter
public class UserGroupEntity extends AbstractAggregate<UserGroupId> {

    private UserGroupEntity(UserGroupId userGroupId) {
        super(userGroupId);
    }

    /**
     * 用户组编码
     */
    private UserGroupCode userGroupCode;

    /**
     * 用户组名
     */
    private String userGroupName;
    /**
     * 用户组描述
     */
    private String userGroupDesc;
    /**
     * 父id
     */
    private UserGroupId parentId;

    /**
     * 租户id
     */
    private TenantId tenantId;

    /**
     * 状态是否可用
     */
    private Integer isEnabled;

    /**
     * 角色id列表
     */
    private Set<RoleId> bindRoles;

    /**
     * 用户id列表
     */
    private Set<UserId> assignedUsers;

    public static UserGroupEntity build (
            TenantDomainService tenantDomainService,
            UserGroupDomainService userGroupDomainService,
            TenantId tenantId,
            UserGroupCode userGroupCode,
            String userGroupName,
            String userGroupDesc,
            UserGroupId parentUserGroupId
    ) {
        if (!tenantDomainService.validateTenantId(tenantId)) {
            throw new IllegalArgumentException("租户不存在");
        }
        if (userGroupDomainService.uniqueUserGroupCode(tenantId, userGroupCode)) {
            throw new IllegalArgumentException("用户组编码已存在");
        }
        if (userGroupDomainService.uniqueUserGroupName(tenantId, userGroupName)) {
            throw new IllegalArgumentException("用户组名称已存在");
        }
        if (Objects.nonNull(parentUserGroupId) && !userGroupDomainService.validateUserGroupId(tenantId, parentUserGroupId)) {
            throw new IllegalArgumentException("父用户组不存在");
        }
        UserGroupEntity entity = new UserGroupEntity(UserGroupId.nextId());
        entity.tenantId = tenantId;
        entity.userGroupCode = userGroupCode;
        entity.userGroupName = userGroupName;
        entity.userGroupDesc = userGroupDesc != null ? userGroupDesc.trim() : "";
        entity.parentId = parentUserGroupId;
        // 6. 发布领域事件
        // entity.registerEvent(new UserGroupCreatedEvent(
        //         entity.getId(),
        //         tenantId,
        //         entity.getUserGroupName()
        // ));
        return entity;
    }

    public static UserGroupEntity build (
            UserGroupId userGroupId,
            UserGroupCode userGroupCode,
            String userGroupName,
            String userGroupDesc,
            UserGroupId parentId,
            TenantId tenantId,
            Integer isEnabled
    ) {
        UserGroupEntity entity = new UserGroupEntity(userGroupId);
        entity.userGroupCode = userGroupCode;
        entity.userGroupName = userGroupName;
        entity.userGroupDesc = userGroupDesc;
        entity.parentId = parentId;
        entity.tenantId = tenantId;
        entity.isEnabled = isEnabled;
        return entity;
    }

    public void assignUser(UserInfoDomainService userInfoDomainService, UserId userId) {
        if (this.isEnabled == 0) {
            throw new DomainException("用户组未激活，不能分配用户！");
        }
        if (assignedUsers.contains(userId)) {
            throw new DomainException("用户组已包含当前用户，请勿重复分配！");
        }
        if (!userInfoDomainService.validateUser(tenantId, userId)) {
            throw new DomainException("当前租户不存在此用户！");
        }
        if (Objects.isNull(assignedUsers)) {
            assignedUsers = new HashSet<>();
        }
        assignedUsers.add(userId);
    }

    public void bindRoles(RoleDomainService roleDomainService, RoleId roleId) {
        if (this.isEnabled == 0) {
            throw new DomainException("用户组未激活，不能绑定角色！");
        }
        if (bindRoles.contains(roleId)) {
            throw new DomainException("用户组已包含当前角色，请勿重复绑定！");
        }
        if (!roleDomainService.validateRoleId(tenantId, roleId)) {
            throw new DomainException("当前租户不存在此角色！");
        }
        if (Objects.isNull(bindRoles)) {
            bindRoles = new HashSet<>();
        }
        bindRoles.add(roleId);
    }

}
