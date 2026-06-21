package com.yaocode.sts.auth.domain.events.role;

import com.yaocode.sts.auth.domain.aggregate.RoleInfoAggregate;
import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.RoleId;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import com.yaocode.sts.common.domain.valueobject.UserId;
import lombok.Getter;

/**
 * 用户分配给角色事件
 */
@Getter
public class UserAssignedToRoleEvent extends AggregateDomainEvent<RoleId> {

    private final RoleId roleId;
    private final UserId userId;

    public UserAssignedToRoleEvent(RoleInfoAggregate role, UserId userId) {
        super(role, EventTypeEnums.USER_ASSIGNED_TO_ROLE.getCode());
        this.roleId = role.getId();
        this.userId = userId;
    }

    public UserAssignedToRoleEvent(RoleId roleId, UserId userId) {
        super(roleId.getValue(), AggregateTypeEnums.ROLE.getCode(), EventTypeEnums.USER_ASSIGNED_TO_ROLE.getCode());
        this.roleId = roleId;
        this.userId = userId;
    }
}
