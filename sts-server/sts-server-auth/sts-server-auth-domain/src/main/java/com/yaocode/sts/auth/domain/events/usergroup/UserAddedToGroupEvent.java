package com.yaocode.sts.auth.domain.events.usergroup;

import com.yaocode.sts.auth.domain.aggregate.UserGroupAggregate;
import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserGroupId;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import com.yaocode.sts.common.domain.valueobject.UserId;
import lombok.Getter;

/**
 * 用户添加到用户组事件
 */
@Getter
public class UserAddedToGroupEvent extends AggregateDomainEvent<UserGroupId> {

    private final UserGroupId userGroupId;
    private final UserId userId;

    public UserAddedToGroupEvent(UserGroupAggregate userGroup, UserId userId) {
        super(userGroup, EventTypeEnums.USER_ADDED_TO_GROUP.getCode());
        this.userGroupId = userGroup.getId();
        this.userId = userId;
    }

    public UserAddedToGroupEvent(UserGroupId userGroupId, UserId userId) {
        super(userGroupId.getValue(), AggregateTypeEnums.USER_GROUP.getCode(), EventTypeEnums.USER_ADDED_TO_GROUP.getCode());
        this.userGroupId = userGroupId;
        this.userId = userId;
    }
}
