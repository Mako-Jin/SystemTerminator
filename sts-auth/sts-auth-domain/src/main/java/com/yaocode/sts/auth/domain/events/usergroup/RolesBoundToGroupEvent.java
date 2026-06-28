package com.yaocode.sts.auth.domain.events.usergroup;

import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.RoleId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserGroupId;
import com.yaocode.sts.common.domain.event.AbstractDomainEvent;
import lombok.Getter;

import java.util.Set;

@Getter
public class RolesBoundToGroupEvent extends AbstractDomainEvent {

    private final UserGroupId userGroupId;
    private final Set<RoleId> roleIds;

    public RolesBoundToGroupEvent(UserGroupId userGroupId, Set<RoleId> roleIds) {
        super(EventTypeEnums.ROLES_BOUND_TO_GROUP.getCode());
        this.userGroupId = userGroupId;
        this.roleIds = roleIds;
    }

    @Override
    public String getAggregateId() {
        return userGroupId.getValue();
    }

    @Override
    public String getAggregateType() {
        return AggregateTypeEnums.USER_GROUP.getCode();
    }
}
