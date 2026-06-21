package com.yaocode.sts.auth.domain.events.user;

import com.yaocode.sts.auth.domain.aggregate.UserInfoAggregate;
import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import com.yaocode.sts.common.domain.valueobject.UserId;
import lombok.Getter;

/**
 * 用户分配租户事件
 */
@Getter
public class UserAssignedToTenantEvent extends AggregateDomainEvent<UserId> {

    private final UserId userId;
    private final TenantId tenantId;

    public UserAssignedToTenantEvent(UserInfoAggregate user, TenantId tenantId) {
        super(user, EventTypeEnums.USER_ASSIGNED_TO_TENANT.getCode());
        this.userId = user.getId();
        this.tenantId = tenantId;
    }

    public UserAssignedToTenantEvent(UserId userId, TenantId tenantId) {
        super(userId.getValue(), AggregateTypeEnums.USER.getCode(), EventTypeEnums.USER_ASSIGNED_TO_TENANT.getCode());
        this.userId = userId;
        this.tenantId = tenantId;
    }
}
