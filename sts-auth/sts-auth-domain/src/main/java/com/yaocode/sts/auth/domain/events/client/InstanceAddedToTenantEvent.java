package com.yaocode.sts.auth.domain.events.client;

import com.yaocode.sts.auth.domain.aggregate.TenantInfoAggregate;
import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.InstanceId;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import lombok.Getter;

/**
 * 实例添加到租户事件
 */
@Getter
public class InstanceAddedToTenantEvent extends AggregateDomainEvent<TenantId> {

    private final TenantId tenantId;
    private final InstanceId instanceId;

    public InstanceAddedToTenantEvent(TenantInfoAggregate tenant, InstanceId instanceId) {
        super(tenant, EventTypeEnums.INSTANCE_ADDED_TO_TENANT.getCode());
        this.tenantId = tenant.getId();
        this.instanceId = instanceId;
    }

    public InstanceAddedToTenantEvent(TenantId tenantId, InstanceId instanceId) {
        super(tenantId.getValue(), AggregateTypeEnums.TENANT.getCode(), EventTypeEnums.INSTANCE_ADDED_TO_TENANT.getCode());
        this.tenantId = tenantId;
        this.instanceId = instanceId;
    }
}
