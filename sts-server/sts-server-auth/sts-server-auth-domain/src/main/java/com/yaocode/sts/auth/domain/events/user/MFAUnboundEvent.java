package com.yaocode.sts.auth.domain.events.user;

import com.yaocode.sts.auth.domain.aggregate.UserInfoAggregate;
import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import com.yaocode.sts.common.domain.valueobject.UserId;
import lombok.Getter;

/**
 * MFA解绑事件
 */
@Getter
public class MFAUnboundEvent extends AggregateDomainEvent<UserId> {

    private final UserId userId;

    public MFAUnboundEvent(UserInfoAggregate user) {
        super(user, EventTypeEnums.MFA_UNBOUND.getCode());
        this.userId = user.getId();
    }

    public MFAUnboundEvent(UserId userId) {
        super(userId.getValue(), AggregateTypeEnums.USER.getCode(), EventTypeEnums.MFA_UNBOUND.getCode());
        this.userId = userId;
    }
}
