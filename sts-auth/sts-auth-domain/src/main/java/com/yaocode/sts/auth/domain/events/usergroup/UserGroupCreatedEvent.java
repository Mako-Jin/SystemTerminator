package com.yaocode.sts.auth.domain.events.usergroup;

import com.yaocode.sts.auth.domain.aggregate.UserGroupAggregate;
import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserGroupId;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import lombok.Getter;

/**
 * 用户组创建事件
 */
@Getter
public class UserGroupCreatedEvent extends AggregateDomainEvent<UserGroupId> {

    private final UserGroupId userGroupId;
    private final String userGroupName;

    public UserGroupCreatedEvent(UserGroupAggregate userGroup) {
        super(userGroup, EventTypeEnums.USER_GROUP_CREATED.getCode());
        this.userGroupId = userGroup.getId();
        this.userGroupName = userGroup.getUserGroupName();
    }

    public UserGroupCreatedEvent(UserGroupId userGroupId, String userGroupName) {
        super(userGroupId.getValue(), AggregateTypeEnums.USER_GROUP.getCode(), EventTypeEnums.USER_GROUP_CREATED.getCode());
        this.userGroupId = userGroupId;
        this.userGroupName = userGroupName;
    }
}
