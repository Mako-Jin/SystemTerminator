package com.yaocode.sts.auth.domain.events.organization;

import com.yaocode.sts.auth.domain.aggregate.OrganizationInfoAggregate;
import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.OrganizationId;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import lombok.Getter;

/**
 * 组织启用事件
 */
@Getter
public class OrganizationEnabledEvent extends AggregateDomainEvent<OrganizationId> {

    private final OrganizationId organizationId;

    public OrganizationEnabledEvent(OrganizationInfoAggregate organization) {
        super(organization, EventTypeEnums.ORGANIZATION_ENABLED.getCode());
        this.organizationId = organization.getId();
    }

    public OrganizationEnabledEvent(OrganizationId organizationId) {
        super(organizationId.getValue(), AggregateTypeEnums.ORGANIZATION.getCode(), EventTypeEnums.ORGANIZATION_ENABLED.getCode());
        this.organizationId = organizationId;
    }
}
