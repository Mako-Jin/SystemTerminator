package com.yaocode.sts.auth.domain.events.role;

import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.RoleId;
import com.yaocode.sts.common.domain.event.AbstractDomainEvent;
import com.yaocode.sts.common.domain.valueobject.UserId;
import lombok.Getter;

import java.util.Set;

/**
 * 批量用户分配给角色事件
 */
@Getter
public class UsersAssignedToRoleEvent extends AbstractDomainEvent {

    private final RoleId roleId;
    private final Set<UserId> userIds;

    public UsersAssignedToRoleEvent(RoleId roleId, Set<UserId> userIds) {
        super(EventTypeEnums.USERS_ASSIGNED_TO_ROLE.getCode());
        this.roleId = roleId;
        this.userIds = userIds;
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
