package com.yaocode.sts.auth.domain.events.tenant;

import com.yaocode.sts.auth.domain.aggregate.TenantInfoAggregate;
import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.composites.SessionConfig;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import lombok.Getter;

/**
 * 租户会话配置更新事件
 */
@Getter
public class TenantSessionConfigUpdatedEvent extends AggregateDomainEvent<TenantId> {

    private final TenantId tenantId;
    private final SessionConfig oldConfig;
    private final SessionConfig newConfig;

    public TenantSessionConfigUpdatedEvent(TenantInfoAggregate tenant, SessionConfig oldConfig, SessionConfig newConfig) {
        super(tenant, EventTypeEnums.TENANT_SESSION_CONFIG_UPDATED.getCode());
        this.tenantId = tenant.getId();
        this.oldConfig = oldConfig;
        this.newConfig = newConfig;
    }

    public TenantSessionConfigUpdatedEvent(TenantId tenantId, SessionConfig oldConfig, SessionConfig newConfig) {
        super(tenantId.getValue(), AggregateTypeEnums.TENANT.getCode(), EventTypeEnums.TENANT_SESSION_CONFIG_UPDATED.getCode());
        this.tenantId = tenantId;
        this.oldConfig = oldConfig;
        this.newConfig = newConfig;
    }
}
