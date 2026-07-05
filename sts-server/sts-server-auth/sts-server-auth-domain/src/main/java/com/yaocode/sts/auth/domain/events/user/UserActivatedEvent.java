package com.yaocode.sts.auth.domain.events.user;

import com.yaocode.sts.auth.domain.aggregate.UserInfoAggregate;
import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import com.yaocode.sts.common.domain.valueobject.UserId;
import lombok.Getter;

/**
 * 用户激活事件
 */
@Getter
public class UserActivatedEvent extends AggregateDomainEvent<UserId> {

    private final UserId userId;

    public UserActivatedEvent(UserInfoAggregate user) {
        super(user, EventTypeEnums.USER_ACTIVATED.getCode());
        this.userId = user.getId();
    }

    public UserActivatedEvent(UserId userId) {
        super(userId.getValue(), AggregateTypeEnums.USER.getCode(), EventTypeEnums.USER_ACTIVATED.getCode());
        this.userId = userId;
    }
}
