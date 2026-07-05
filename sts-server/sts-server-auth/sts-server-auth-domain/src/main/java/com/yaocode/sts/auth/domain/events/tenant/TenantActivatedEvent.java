package com.yaocode.sts.auth.domain.events.tenant;

import com.yaocode.sts.auth.domain.aggregate.TenantInfoAggregate;
import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import lombok.Getter;

/**
 * 租户激活事件
 */
@Getter
public class TenantActivatedEvent extends AggregateDomainEvent<TenantId> {

    private final TenantId tenantId;

    public TenantActivatedEvent(TenantInfoAggregate tenant) {
        super(tenant, EventTypeEnums.TENANT_ACTIVATED.getCode());
        this.tenantId = tenant.getId();
    }

    public TenantActivatedEvent(TenantId tenantId) {
        super(tenantId.getValue(), AggregateTypeEnums.TENANT.getCode(), EventTypeEnums.TENANT_ACTIVATED.getCode());
        this.tenantId = tenantId;
    }
}
