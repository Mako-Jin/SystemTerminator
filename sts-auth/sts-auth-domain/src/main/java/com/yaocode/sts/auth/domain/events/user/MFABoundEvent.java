package com.yaocode.sts.auth.domain.events.user;

import com.yaocode.sts.auth.domain.aggregate.UserInfoAggregate;
import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.auth.domain.enums.MFATypeEnums;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import com.yaocode.sts.common.domain.valueobject.UserId;
import lombok.Getter;

/**
 * MFA绑定事件
 */
@Getter
public class MFABoundEvent extends AggregateDomainEvent<UserId> {

    private final UserId userId;
    private final MFATypeEnums mfaType;

    public MFABoundEvent(UserInfoAggregate user, MFATypeEnums mfaType) {
        super(user, EventTypeEnums.MFA_BOUND.getCode());
        this.userId = user.getId();
        this.mfaType = mfaType;
    }

    public MFABoundEvent(UserId userId, MFATypeEnums mfaType) {
        super(userId.getValue(), AggregateTypeEnums.USER.getCode(), EventTypeEnums.MFA_BOUND.getCode());
        this.userId = userId;
        this.mfaType = mfaType;
    }
}
