package com.yaocode.sts.auth.domain.events.usergroup;

import com.yaocode.sts.auth.domain.aggregate.UserGroupAggregate;
import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserGroupId;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import lombok.Getter;

/**
 * 用户组启用事件
 */
@Getter
public class UserGroupEnabledEvent extends AggregateDomainEvent<UserGroupId> {

    private final UserGroupId userGroupId;

    public UserGroupEnabledEvent(UserGroupAggregate userGroup) {
        super(userGroup, EventTypeEnums.USER_GROUP_ENABLED.getCode());
        this.userGroupId = userGroup.getId();
    }

    public UserGroupEnabledEvent(UserGroupId userGroupId) {
        super(userGroupId.getValue(), AggregateTypeEnums.USER_GROUP.getCode(), EventTypeEnums.USER_GROUP_ENABLED.getCode());
        this.userGroupId = userGroupId;
    }
}
