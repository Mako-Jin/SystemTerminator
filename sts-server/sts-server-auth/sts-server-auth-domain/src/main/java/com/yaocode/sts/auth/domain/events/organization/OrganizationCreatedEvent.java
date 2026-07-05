package com.yaocode.sts.auth.domain.events.organization;

import com.yaocode.sts.auth.domain.aggregate.OrganizationInfoAggregate;
import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.OrganizationId;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import lombok.Getter;

/**
 * 组织创建事件
 */
@Getter
public class OrganizationCreatedEvent extends AggregateDomainEvent<OrganizationId> {

    private final OrganizationId organizationId;
    private final String organizationName;

    public OrganizationCreatedEvent(OrganizationInfoAggregate organization) {
        super(organization, EventTypeEnums.ORGANIZATION_CREATED.getCode());
        this.organizationId = organization.getId();
        this.organizationName = organization.getOrganizationName();
    }

    public OrganizationCreatedEvent(OrganizationId organizationId, String organizationName) {
        super(organizationId.getValue(), AggregateTypeEnums.ORGANIZATION.getCode(), EventTypeEnums.ORGANIZATION_CREATED.getCode());
        this.organizationId = organizationId;
        this.organizationName = organizationName;
    }
}
