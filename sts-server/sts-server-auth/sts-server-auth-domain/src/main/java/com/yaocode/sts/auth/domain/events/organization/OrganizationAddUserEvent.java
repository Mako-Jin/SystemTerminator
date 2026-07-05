package com.yaocode.sts.auth.domain.events.organization;


import com.yaocode.sts.auth.domain.aggregate.OrganizationInfoAggregate;
import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.OrganizationId;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import com.yaocode.sts.common.domain.valueobject.UserId;
import lombok.Getter;

/**
 * 用户添加到组织事件
 */
@Getter
public class OrganizationAddUserEvent extends AggregateDomainEvent<OrganizationId> {

    private final OrganizationId organizationId;
    private final UserId userId;

    public OrganizationAddUserEvent(OrganizationInfoAggregate organization, UserId userId) {
        super(organization, EventTypeEnums.ORGANIZATION_ADDED_USER.getCode());
        this.organizationId = organization.getId();
        this.userId = userId;
    }

    public OrganizationAddUserEvent(OrganizationId organizationId, UserId userId) {
        super(organizationId.getValue(), AggregateTypeEnums.ORGANIZATION.getCode(), EventTypeEnums.ORGANIZATION_ADDED_USER.getCode());
        this.organizationId = organizationId;
        this.userId = userId;
    }
}
