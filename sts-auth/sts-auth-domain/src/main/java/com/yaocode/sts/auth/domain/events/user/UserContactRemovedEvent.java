package com.yaocode.sts.auth.domain.events.user;

import com.yaocode.sts.auth.domain.aggregate.UserInfoAggregate;
import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ContactId;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import com.yaocode.sts.common.domain.valueobject.UserId;
import lombok.Getter;

/**
 * 用户联系方式移除事件
 */
@Getter
public class UserContactRemovedEvent extends AggregateDomainEvent<UserId> {

    private final UserId userId;
    private final ContactId contactId;

    public UserContactRemovedEvent(UserInfoAggregate user, ContactId contactId) {
        super(user, EventTypeEnums.USER_CONTACT_REMOVED.getCode());
        this.userId = user.getId();
        this.contactId = contactId;
    }

    public UserContactRemovedEvent(UserId userId, ContactId contactId) {
        super(userId.getValue(), AggregateTypeEnums.USER.getCode(), EventTypeEnums.USER_CONTACT_REMOVED.getCode());
        this.userId = userId;
        this.contactId = contactId;
    }
}