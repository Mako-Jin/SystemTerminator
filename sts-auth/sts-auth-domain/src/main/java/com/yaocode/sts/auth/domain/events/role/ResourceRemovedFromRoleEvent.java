package com.yaocode.sts.auth.domain.events.role;

import com.yaocode.sts.auth.domain.aggregate.RoleInfoAggregate;
import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ResourceId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.RoleId;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import lombok.Getter;

/**
 * 资源从角色移除事件
 */
@Getter
public class ResourceRemovedFromRoleEvent extends AggregateDomainEvent<RoleId> {

    private final RoleId roleId;
    private final ResourceId resourceId;

    public ResourceRemovedFromRoleEvent(RoleInfoAggregate role, ResourceId resourceId) {
        super(role, EventTypeEnums.RESOURCE_REMOVED_FROM_ROLE.getCode());
        this.roleId = role.getId();
        this.resourceId = resourceId;
    }

    public ResourceRemovedFromRoleEvent(RoleId roleId, ResourceId resourceId) {
        super(roleId.getValue(), AggregateTypeEnums.ROLE.getCode(), EventTypeEnums.RESOURCE_REMOVED_FROM_ROLE.getCode());
        this.roleId = roleId;
        this.resourceId = resourceId;
    }
}