package com.yaocode.sts.auth.domain.events.role;

import com.yaocode.sts.auth.domain.aggregate.RoleInfoAggregate;
import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.RoleId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserGroupId;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import lombok.Getter;

/**
 * 用户组从角色移除事件
 */
@Getter
public class UserGroupRemovedFromRoleEvent extends AggregateDomainEvent<RoleId> {

    private final RoleId roleId;
    private final UserGroupId userGroupId;

    public UserGroupRemovedFromRoleEvent(RoleInfoAggregate role, UserGroupId userGroupId) {
        super(role, EventTypeEnums.USER_GROUP_REMOVED_FROM_ROLE.getCode());
        this.roleId = role.getId();
        this.userGroupId = userGroupId;
    }

    public UserGroupRemovedFromRoleEvent(RoleId roleId, UserGroupId userGroupId) {
        super(roleId.getValue(), AggregateTypeEnums.ROLE.getCode(), EventTypeEnums.USER_GROUP_REMOVED_FROM_ROLE.getCode());
        this.roleId = roleId;
        this.userGroupId = userGroupId;
    }
}