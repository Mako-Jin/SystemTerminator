package com.yaocode.sts.auth.domain.entity;

import com.yaocode.sts.auth.domain.constants.AuthI18nKeyConstants;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.RoleId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserGroupId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.UserGroupCode;
import com.yaocode.sts.common.basic.constants.SymbolConstants;
import com.yaocode.sts.common.domain.exception.DomainException;
import com.yaocode.sts.common.domain.model.AbstractAggregate;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import com.yaocode.sts.common.domain.valueobject.UserId;
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
            TenantId tenantId,
            UserGroupCode userGroupCode,
            String userGroupName,
            String userGroupDesc,
            UserGroupId parentUserGroupId
    ) {
        UserGroupEntity entity = new UserGroupEntity(UserGroupId.nextId());
        entity.tenantId = tenantId;
        entity.userGroupCode = userGroupCode;
        entity.userGroupName = userGroupName;
        entity.userGroupDesc = userGroupDesc != null ? userGroupDesc.trim() : SymbolConstants.EMPTY_STR;
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

    public void assignUser(UserId userId) {
        if (this.isEnabled == 0) {
            throw new DomainException(AuthI18nKeyConstants.USER_GROUP_NOT_ACTIVATED_CANNOT_ASSIGN_USER);
        }
        if (Objects.isNull(assignedUsers)) {
            assignedUsers = new HashSet<>();
        }
        if (assignedUsers.contains(userId)) {
            throw new DomainException(AuthI18nKeyConstants.USER_GROUP_ALREADY_CONTAINS_USER);
        }
        assignedUsers.add(userId);
    }

    public void bindRoles(RoleId roleId) {
        if (this.isEnabled == 0) {
            throw new DomainException(AuthI18nKeyConstants.USER_GROUP_NOT_ACTIVATED_CANNOT_BIND_ROLE);
        }
        if (Objects.isNull(bindRoles)) {
            bindRoles = new HashSet<>();
        }
        if (bindRoles.contains(roleId)) {
            throw new DomainException(AuthI18nKeyConstants.USER_GROUP_ALREADY_CONTAINS_ROLE);
        }
        bindRoles.add(roleId);
    }

}