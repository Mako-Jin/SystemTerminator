package com.yaocode.sts.auth.domain.events.user;

import com.yaocode.sts.auth.domain.aggregate.UserInfoAggregate;
import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import com.yaocode.sts.common.domain.valueobject.UserId;
import lombok.Getter;

/**
 * 用户停用事件
 */
@Getter
public class UserDeactivatedEvent extends AggregateDomainEvent<UserId> {

    private final UserId userId;

    public UserDeactivatedEvent(UserInfoAggregate user) {
        super(user, EventTypeEnums.USER_DEACTIVATED.getCode());
        this.userId = user.getId();
    }

    public UserDeactivatedEvent(UserId userId) {
        super(userId.getValue(), AggregateTypeEnums.USER.getCode(), EventTypeEnums.USER_DEACTIVATED.getCode());
        this.userId = userId;
    }
}
