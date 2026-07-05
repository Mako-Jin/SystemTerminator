package com.yaocode.sts.auth.domain.events.user;

import com.yaocode.sts.auth.domain.aggregate.UserInfoAggregate;
import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.OrganizationId;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import com.yaocode.sts.common.domain.valueobject.UserId;
import lombok.Getter;

/**
 * 用户移除组织事件
 */
@Getter
public class UserRemoveOrganizationEvent extends AggregateDomainEvent<UserId> {

    private final UserId userId;
    private final OrganizationId organizationId;

    public UserRemoveOrganizationEvent(UserInfoAggregate user, OrganizationId organizationId) {
        super(user, EventTypeEnums.USER_REMOVE_ORGANIZATION.getCode());
        this.userId = user.getId();
        this.organizationId = organizationId;
    }

    public UserRemoveOrganizationEvent(UserId userId, OrganizationId organizationId) {
        super(userId.getValue(), AggregateTypeEnums.USER.getCode(), EventTypeEnums.USER_REMOVE_ORGANIZATION.getCode());
        this.userId = userId;
        this.organizationId = organizationId;
    }
}
