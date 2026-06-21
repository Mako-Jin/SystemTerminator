package com.yaocode.sts.auth.domain.events.usergroup;

import com.yaocode.sts.auth.domain.aggregate.UserGroupAggregate;
import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserGroupId;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import com.yaocode.sts.common.domain.valueobject.UserId;
import lombok.Getter;

/**
 * 用户从用户组移除事件
 */
@Getter
public class UserRemovedFromGroupEvent extends AggregateDomainEvent<UserGroupId> {

    private final UserGroupId userGroupId;
    private final UserId userId;

    public UserRemovedFromGroupEvent(UserGroupAggregate userGroup, UserId userId) {
        super(userGroup, EventTypeEnums.USER_REMOVED_FROM_GROUP.getCode());
        this.userGroupId = userGroup.getId();
        this.userId = userId;
    }

    public UserRemovedFromGroupEvent(UserGroupId userGroupId, UserId userId) {
        super(userGroupId.getValue(), AggregateTypeEnums.USER_GROUP.getCode(), EventTypeEnums.USER_REMOVED_FROM_GROUP.getCode());
        this.userGroupId = userGroupId;
        this.userId = userId;
    }
}
