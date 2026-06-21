package com.yaocode.sts.auth.domain.events.usergroup;

import com.yaocode.sts.auth.domain.aggregate.UserGroupAggregate;
import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserGroupId;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import lombok.Getter;

/**
 * 用户组禁用事件
 */
@Getter
public class UserGroupDisabledEvent extends AggregateDomainEvent<UserGroupId> {

    private final UserGroupId userGroupId;

    public UserGroupDisabledEvent(UserGroupAggregate userGroup) {
        super(userGroup, EventTypeEnums.USER_GROUP_DISABLED.getCode());
        this.userGroupId = userGroup.getId();
    }

    public UserGroupDisabledEvent(UserGroupId userGroupId) {
        super(userGroupId.getValue(), AggregateTypeEnums.USER_GROUP.getCode(), EventTypeEnums.USER_GROUP_DISABLED.getCode());
        this.userGroupId = userGroupId;
    }
}
