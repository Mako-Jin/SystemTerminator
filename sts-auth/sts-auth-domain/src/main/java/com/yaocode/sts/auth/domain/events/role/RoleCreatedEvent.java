package com.yaocode.sts.auth.domain.events.role;

import com.yaocode.sts.auth.domain.aggregate.RoleInfoAggregate;
import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.RoleId;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import lombok.Getter;

/**
 * 角色创建事件
 */
@Getter
public class RoleCreatedEvent extends AggregateDomainEvent<RoleId> {

    private final RoleId roleId;
    private final String roleName;

    public RoleCreatedEvent(RoleInfoAggregate role) {
        super(role, EventTypeEnums.ROLE_CREATED.getCode());
        this.roleId = role.getId();
        this.roleName = role.getRoleName();
    }

    public RoleCreatedEvent(RoleId roleId, String roleName) {
        super(roleId.getValue(), AggregateTypeEnums.ROLE.getCode(), EventTypeEnums.ROLE_CREATED.getCode());
        this.roleId = roleId;
        this.roleName = roleName;
    }
}
