package com.yaocode.sts.auth.domain.events.tenant;

import com.yaocode.sts.auth.domain.aggregate.TenantInfoAggregate;
import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import lombok.Getter;

/**
 * 租户停用事件
 */
@Getter
public class TenantSuspendedEvent extends AggregateDomainEvent<TenantId> {

    private final TenantId tenantId;

    public TenantSuspendedEvent(TenantInfoAggregate tenant) {
        super(tenant, EventTypeEnums.TENANT_SUSPENDED.getCode());
        this.tenantId = tenant.getId();
    }

    public TenantSuspendedEvent(TenantId tenantId) {
        super(tenantId.getValue(), AggregateTypeEnums.TENANT.getCode(), EventTypeEnums.TENANT_SUSPENDED.getCode());
        this.tenantId = tenantId;
    }
}
