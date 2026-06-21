package com.yaocode.sts.auth.domain.events.user;

import com.yaocode.sts.auth.domain.aggregate.UserInfoAggregate;
import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.ContactTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ContactId;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import com.yaocode.sts.common.domain.valueobject.UserId;
import lombok.Getter;

/**
 * 用户联系方式添加事件
 */
@Getter
public class UserContactAddedEvent extends AggregateDomainEvent<UserId> {

    private final UserId userId;
    private final ContactId contactId;
    private final ContactTypeEnums contactType;

    public UserContactAddedEvent(UserInfoAggregate user, ContactId contactId, ContactTypeEnums contactType) {
        super(user, EventTypeEnums.USER_CONTACT_ADDED.getCode());
        this.userId = user.getId();
        this.contactId = contactId;
        this.contactType = contactType;
    }

    public UserContactAddedEvent(UserId userId, ContactId contactId, ContactTypeEnums contactType) {
        super(userId.getValue(), AggregateTypeEnums.USER.getCode(), EventTypeEnums.USER_CONTACT_ADDED.getCode());
        this.userId = userId;
        this.contactId = contactId;
        this.contactType = contactType;
    }
}
