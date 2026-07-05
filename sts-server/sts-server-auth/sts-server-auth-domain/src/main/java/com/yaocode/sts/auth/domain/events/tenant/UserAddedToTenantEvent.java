package com.yaocode.sts.auth.domain.events.tenant;

import com.yaocode.sts.auth.domain.aggregate.TenantInfoAggregate;
import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import com.yaocode.sts.common.domain.valueobject.UserId;
import lombok.Getter;

/**
 * 用户添加到租户事件
 */
@Getter
public class UserAddedToTenantEvent extends AggregateDomainEvent<TenantId> {

    private final TenantId tenantId;
    private final UserId userId;

    public UserAddedToTenantEvent(TenantInfoAggregate tenant, UserId userId) {
        super(tenant, EventTypeEnums.USER_ADDED_TO_TENANT.getCode());
        this.tenantId = tenant.getId();
        this.userId = userId;
    }

    public UserAddedToTenantEvent(TenantId tenantId, UserId userId) {
        super(tenantId.getValue(), AggregateTypeEnums.TENANT.getCode(), EventTypeEnums.USER_ADDED_TO_TENANT.getCode());
        this.tenantId = tenantId;
        this.userId = userId;
    }
}
