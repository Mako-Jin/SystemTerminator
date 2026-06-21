package com.yaocode.sts.auth.domain.events.user;

import com.yaocode.sts.auth.domain.aggregate.UserInfoAggregate;
import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import com.yaocode.sts.common.domain.valueobject.UserId;
import lombok.Getter;

/**
 * 用户档案更新事件
 */
@Getter
public class UserProfileUpdatedEvent extends AggregateDomainEvent<UserId> {

    private final UserId userId;

    public UserProfileUpdatedEvent(UserInfoAggregate user) {
        super(user, EventTypeEnums.USER_PROFILE_UPDATED.getCode());
        this.userId = user.getId();
    }

    public UserProfileUpdatedEvent(UserId userId) {
        super(userId.getValue(), AggregateTypeEnums.USER.getCode(), EventTypeEnums.USER_PROFILE_UPDATED.getCode());
        this.userId = userId;
    }
}
