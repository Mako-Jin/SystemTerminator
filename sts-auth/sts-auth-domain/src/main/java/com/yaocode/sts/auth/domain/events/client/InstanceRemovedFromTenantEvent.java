package com.yaocode.sts.auth.domain.events.client;

import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.InstanceId;
import com.yaocode.sts.common.domain.event.AbstractDomainEvent;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import lombok.Getter;

/**
 * 实例从租户移除事件
 */
@Getter
public class InstanceRemovedFromTenantEvent extends AbstractDomainEvent {

    private final TenantId tenantId;
    private final InstanceId instanceId;

    public InstanceRemovedFromTenantEvent(TenantId tenantId, InstanceId instanceId) {
        super(EventTypeEnums.INSTANCE_REMOVED_FROM_TENANT.getCode());
        this.tenantId = tenantId;
        this.instanceId = instanceId;
    }

    @Override
    public String getAggregateId() {
        return tenantId.getValue();
    }

    @Override
    public String getAggregateType() {
        return AggregateTypeEnums.TENANT.getCode();
    }
}
