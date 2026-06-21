package com.yaocode.sts.auth.domain.events.organization;

import com.yaocode.sts.auth.domain.aggregate.OrganizationInfoAggregate;
import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.OrganizationId;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import com.yaocode.sts.common.domain.valueobject.UserId;
import lombok.Getter;

/**
 * 组织负责人变更事件
 */
@Getter
public class OrganizationManagerChangedEvent extends AggregateDomainEvent<OrganizationId> {

    private final OrganizationId organizationId;
    private final UserId oldManagerId;
    private final UserId newManagerId;

    public OrganizationManagerChangedEvent(OrganizationInfoAggregate organization, UserId oldManagerId, UserId newManagerId) {
        super(organization, EventTypeEnums.ORGANIZATION_MANAGER_CHANGED.getCode());
        this.organizationId = organization.getId();
        this.oldManagerId = oldManagerId;
        this.newManagerId = newManagerId;
    }

    public OrganizationManagerChangedEvent(OrganizationId organizationId, UserId oldManagerId, UserId newManagerId) {
        super(organizationId.getValue(), AggregateTypeEnums.ORGANIZATION.getCode(), EventTypeEnums.ORGANIZATION_MANAGER_CHANGED.getCode());
        this.organizationId = organizationId;
        this.oldManagerId = oldManagerId;
        this.newManagerId = newManagerId;
    }
}
