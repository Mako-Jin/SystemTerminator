package com.yaocode.sts.auth.domain.events.role;

import com.yaocode.sts.auth.domain.aggregate.RoleInfoAggregate;
import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ResourceId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.RoleId;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import lombok.Getter;

/**
 * 资源分配给角色事件
 */
@Getter
public class ResourceAssignedToRoleEvent extends AggregateDomainEvent<RoleId> {

    private final RoleId roleId;
    private final ResourceId resourceId;

    public ResourceAssignedToRoleEvent(RoleInfoAggregate role, ResourceId resourceId) {
        super(role, EventTypeEnums.RESOURCE_ASSIGNED_TO_ROLE.getCode());
        this.roleId = role.getId();
        this.resourceId = resourceId;
    }

    public ResourceAssignedToRoleEvent(RoleId roleId, ResourceId resourceId) {
        super(roleId.getValue(), AggregateTypeEnums.ROLE.getCode(), EventTypeEnums.RESOURCE_ASSIGNED_TO_ROLE.getCode());
        this.roleId = roleId;
        this.resourceId = resourceId;
    }
}