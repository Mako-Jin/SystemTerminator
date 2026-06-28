package com.yaocode.sts.auth.domain.events.usergroup;

import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserGroupId;
import com.yaocode.sts.common.domain.event.AbstractDomainEvent;
import com.yaocode.sts.common.domain.valueobject.UserId;
import lombok.Getter;

import java.util.Set;

@Getter
public class UsersAddedToGroupEvent extends AbstractDomainEvent {

    private final UserGroupId userGroupId;
    private final Set<UserId> userIds;

    public UsersAddedToGroupEvent(UserGroupId userGroupId, Set<UserId> userIds) {
        super(EventTypeEnums.USERS_ADDED_TO_GROUP.getCode());
        this.userGroupId = userGroupId;
        this.userIds = userIds;
    }

    @Override
    public String getAggregateId() {
        return userGroupId.getValue();
    }

    @Override
    public String getAggregateType() {
        return AggregateTypeEnums.USER_GROUP.getCode();
    }
}
