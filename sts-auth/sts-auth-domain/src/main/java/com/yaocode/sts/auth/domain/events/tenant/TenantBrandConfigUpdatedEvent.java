package com.yaocode.sts.auth.domain.events.tenant;

import com.yaocode.sts.auth.domain.aggregate.TenantInfoAggregate;
import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import lombok.Getter;

/**
 * 租户品牌配置更新事件
 */
@Getter
public class TenantBrandConfigUpdatedEvent extends AggregateDomainEvent<TenantId> {

    private final TenantId tenantId;

    public TenantBrandConfigUpdatedEvent(TenantInfoAggregate tenant) {
        super(tenant, EventTypeEnums.TENANT_BRAND_CONFIG_UPDATED.getCode());
        this.tenantId = tenant.getId();
    }

    public TenantBrandConfigUpdatedEvent(TenantId tenantId) {
        super(tenantId.getValue(), AggregateTypeEnums.TENANT.getCode(), EventTypeEnums.TENANT_BRAND_CONFIG_UPDATED.getCode());
        this.tenantId = tenantId;
    }
}
