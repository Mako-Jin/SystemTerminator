package com.yaocode.sts.auth.domain.events.tenant;

import com.yaocode.sts.auth.domain.aggregate.TenantInfoAggregate;
import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import com.yaocode.sts.common.domain.valueobject.UserId;
import lombok.Getter;

/**
 * 用户从租户移除事件
 */
@Getter
public class TenantRemoveUserEvent extends AggregateDomainEvent<TenantId> {

    private final TenantId tenantId;
    private final UserId userId;

    public TenantRemoveUserEvent(TenantInfoAggregate tenant, UserId userId) {
        super(tenant, EventTypeEnums.USER_REMOVED_FROM_TENANT.getCode());
        this.tenantId = tenant.getId();
        this.userId = userId;
    }

    public TenantRemoveUserEvent(TenantId tenantId, UserId userId) {
        super(tenantId.getValue(), AggregateTypeEnums.TENANT.getCode(), EventTypeEnums.USER_REMOVED_FROM_TENANT.getCode());
        this.tenantId = tenantId;
        this.userId = userId;
    }
}
