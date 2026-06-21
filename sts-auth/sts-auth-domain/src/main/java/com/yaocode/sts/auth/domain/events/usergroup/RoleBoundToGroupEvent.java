package com.yaocode.sts.auth.domain.events.usergroup;

import com.yaocode.sts.auth.domain.aggregate.UserGroupAggregate;
import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.RoleId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserGroupId;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import lombok.Getter;

/**
 * 角色绑定到用户组事件
 */
@Getter
public class RoleBoundToGroupEvent extends AggregateDomainEvent<UserGroupId> {

    private final UserGroupId userGroupId;
    private final RoleId roleId;

    public RoleBoundToGroupEvent(UserGroupAggregate userGroup, RoleId roleId) {
        super(userGroup, EventTypeEnums.ROLE_BOUND_TO_GROUP.getCode());
        this.userGroupId = userGroup.getId();
        this.roleId = roleId;
    }

    public RoleBoundToGroupEvent(UserGroupId userGroupId, RoleId roleId) {
        super(userGroupId.getValue(), AggregateTypeEnums.USER_GROUP.getCode(), EventTypeEnums.ROLE_BOUND_TO_GROUP.getCode());
        this.userGroupId = userGroupId;
        this.roleId = roleId;
    }
}
