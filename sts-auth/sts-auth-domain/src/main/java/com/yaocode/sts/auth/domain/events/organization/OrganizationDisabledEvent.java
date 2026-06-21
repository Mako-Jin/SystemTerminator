package com.yaocode.sts.auth.domain.events.organization;

import com.yaocode.sts.auth.domain.aggregate.OrganizationInfoAggregate;
import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.OrganizationId;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import lombok.Getter;

/**
 * 组织禁用事件
 */
@Getter
public class OrganizationDisabledEvent extends AggregateDomainEvent<OrganizationId> {

    private final OrganizationId organizationId;

    public OrganizationDisabledEvent(OrganizationInfoAggregate organization) {
        super(organization, EventTypeEnums.ORGANIZATION_DISABLED.getCode());
        this.organizationId = organization.getId();
    }

    public OrganizationDisabledEvent(OrganizationId organizationId) {
        super(organizationId.getValue(), AggregateTypeEnums.ORGANIZATION.getCode(), EventTypeEnums.ORGANIZATION_DISABLED.getCode());
        this.organizationId = organizationId;
    }
}
