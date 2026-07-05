package com.yaocode.sts.auth.domain.events.user;

import com.yaocode.sts.auth.domain.aggregate.UserInfoAggregate;
import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.RoleId;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import com.yaocode.sts.common.domain.valueobject.UserId;
import lombok.Getter;

/**
 * 用户分配角色事件
 */
@Getter
public class UserAssignedRoleEvent extends AggregateDomainEvent<UserId> {

    private final UserId userId;
    private final RoleId roleId;

    public UserAssignedRoleEvent(UserInfoAggregate user, RoleId roleId) {
        super(user, EventTypeEnums.USER_ASSIGNED_ROLE.getCode());
        this.userId = user.getId();
        this.roleId = roleId;
    }

    public UserAssignedRoleEvent(UserId userId, RoleId roleId) {
        super(userId.getValue(), AggregateTypeEnums.USER.getCode(), EventTypeEnums.USER_ASSIGNED_ROLE.getCode());
        this.userId = userId;
        this.roleId = roleId;
    }
}
