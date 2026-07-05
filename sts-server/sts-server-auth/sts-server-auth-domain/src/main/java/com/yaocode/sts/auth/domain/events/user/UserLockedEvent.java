package com.yaocode.sts.auth.domain.events.user;

import com.yaocode.sts.auth.domain.aggregate.UserInfoAggregate;
import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import com.yaocode.sts.common.domain.valueobject.UserId;
import lombok.Getter;

/**
 * 用户锁定事件
 */
@Getter
public class UserLockedEvent extends AggregateDomainEvent<UserId> {

    private final UserId userId;
    private final String reason;

    public UserLockedEvent(UserInfoAggregate user, String reason) {
        super(user, EventTypeEnums.USER_LOCKED.getCode());
        this.userId = user.getId();
        this.reason = reason;
    }

    public UserLockedEvent(UserId userId, String reason) {
        super(userId.getValue(), AggregateTypeEnums.USER.getCode(), EventTypeEnums.USER_LOCKED.getCode());
        this.userId = userId;
        this.reason = reason;
    }
}
