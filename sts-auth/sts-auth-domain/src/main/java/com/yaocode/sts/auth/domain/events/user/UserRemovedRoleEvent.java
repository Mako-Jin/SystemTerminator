package com.yaocode.sts.auth.domain.events.user;

import com.yaocode.sts.auth.domain.aggregate.UserInfoAggregate;
import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.RoleId;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import com.yaocode.sts.common.domain.valueobject.UserId;
import lombok.Getter;

/**
 * 用户移除角色事件
 */
@Getter
public class UserRemovedRoleEvent extends AggregateDomainEvent<UserId> {

    private final UserId userId;
    private final RoleId roleId;

    public UserRemovedRoleEvent(UserInfoAggregate user, RoleId roleId) {
        super(user, EventTypeEnums.USER_REMOVE_ROLE.getCode());
        this.userId = user.getId();
        this.roleId = roleId;
    }

    public UserRemovedRoleEvent(UserId userId, RoleId roleId) {
        super(userId.getValue(), AggregateTypeEnums.USER.getCode(), EventTypeEnums.USER_REMOVE_ROLE.getCode());
        this.userId = userId;
        this.roleId = roleId;
    }
}

