package com.yaocode.sts.auth.domain.events.tenant;

import com.yaocode.sts.auth.domain.aggregate.TenantInfoAggregate;
import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.primitives.TenantCode;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import lombok.Getter;

/**
 * 租户创建事件
 */
@Getter
public class TenantCreatedEvent extends AggregateDomainEvent<TenantId> {

    private final TenantId tenantId;
    private final String tenantName;
    private final TenantCode tenantCode;

    public TenantCreatedEvent(TenantInfoAggregate tenant) {
        super(tenant, EventTypeEnums.TENANT_CREATED.getCode());
        this.tenantId = tenant.getId();
        this.tenantName = tenant.getTenantName();
        this.tenantCode = tenant.getTenantCode();
    }

    public TenantCreatedEvent(TenantId tenantId, String tenantName, TenantCode tenantCode) {
        super(tenantId.getValue(), AggregateTypeEnums.TENANT.getCode(), EventTypeEnums.TENANT_CREATED.getCode());
        this.tenantId = tenantId;
        this.tenantName = tenantName;
        this.tenantCode = tenantCode;
    }
}
