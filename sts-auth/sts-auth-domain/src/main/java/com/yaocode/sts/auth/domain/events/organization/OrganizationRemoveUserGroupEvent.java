package com.yaocode.sts.auth.domain.events.organization;

import com.yaocode.sts.auth.domain.aggregate.OrganizationInfoAggregate;
import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.OrganizationId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserGroupId;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import lombok.Getter;

/**
 * 用户组从组织移除事件
 */
@Getter
public class OrganizationRemoveUserGroupEvent extends AggregateDomainEvent<OrganizationId> {

    private final OrganizationId organizationId;
    private final UserGroupId userGroupId;

    public OrganizationRemoveUserGroupEvent(OrganizationInfoAggregate organization, UserGroupId userGroupId) {
        super(organization, EventTypeEnums.ORGANIZATION_REMOVE_USER_GROUP.getCode());
        this.organizationId = organization.getId();
        this.userGroupId = userGroupId;
    }

    public OrganizationRemoveUserGroupEvent(OrganizationId organizationId, UserGroupId userGroupId) {
        super(organizationId.getValue(), AggregateTypeEnums.ORGANIZATION.getCode(), EventTypeEnums.ORGANIZATION_REMOVE_USER_GROUP.getCode());
        this.organizationId = organizationId;
        this.userGroupId = userGroupId;
    }
}
