package com.yaocode.sts.auth.domain.events.user;

import com.yaocode.sts.auth.domain.aggregate.UserInfoAggregate;
import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserGroupId;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import com.yaocode.sts.common.domain.valueobject.UserId;
import lombok.Getter;

/**
 * 用户分配用户组事件
 */
@Getter
public class UserAssignedToUserGroupEvent extends AggregateDomainEvent<UserId> {

    private final UserId userId;
    private final UserGroupId userGroupId;

    public UserAssignedToUserGroupEvent(UserInfoAggregate user, UserGroupId userGroupId) {
        super(user, EventTypeEnums.USER_ASSIGNED_TO_USER_GROUP.getCode());
        this.userId = user.getId();
        this.userGroupId = userGroupId;
    }

    public UserAssignedToUserGroupEvent(UserId userId, UserGroupId userGroupId) {
        super(userId.getValue(), AggregateTypeEnums.USER.getCode(), EventTypeEnums.USER_ASSIGNED_TO_USER_GROUP.getCode());
        this.userId = userId;
        this.userGroupId = userGroupId;
    }
}
