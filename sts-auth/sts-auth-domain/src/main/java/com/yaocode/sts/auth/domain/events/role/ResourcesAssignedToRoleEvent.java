package com.yaocode.sts.auth.domain.events.role;

import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ResourceId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.RoleId;
import com.yaocode.sts.common.domain.event.AbstractDomainEvent;
import lombok.Getter;

import java.util.Set;

/**
 * 批量资源分配给角色事件
 */
@Getter
public class ResourcesAssignedToRoleEvent extends AbstractDomainEvent {

    private final RoleId roleId;
    private final Set<ResourceId> resourceIds;

    public ResourcesAssignedToRoleEvent(RoleId roleId, Set<ResourceId> resourceIds) {
        super(EventTypeEnums.RESOURCES_ASSIGNED_TO_ROLE.getCode());
        this.roleId = roleId;
        this.resourceIds = resourceIds;
    }

    @Override
    public String getAggregateId() {
        return roleId.getValue();
    }

    @Override
    public String getAggregateType() {
        return AggregateTypeEnums.ROLE.getCode();
    }
}
