package com.yaocode.sts.auth.domain.events.role;

import com.yaocode.sts.auth.domain.aggregate.RoleInfoAggregate;
import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.RoleId;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import lombok.Getter;

/**
 * 角色启用事件
 */
@Getter
public class RoleEnabledEvent extends AggregateDomainEvent<RoleId> {

    private final RoleId roleId;

    public RoleEnabledEvent(RoleInfoAggregate role) {
        super(role, EventTypeEnums.ROLE_ENABLED.getCode());
        this.roleId = role.getId();
    }

    public RoleEnabledEvent(RoleId roleId) {
        super(roleId.getValue(), AggregateTypeEnums.ROLE.getCode(), EventTypeEnums.ROLE_ENABLED.getCode());
        this.roleId = roleId;
    }
}
