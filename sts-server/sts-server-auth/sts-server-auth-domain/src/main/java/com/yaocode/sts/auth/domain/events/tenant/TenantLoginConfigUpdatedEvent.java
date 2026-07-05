package com.yaocode.sts.auth.domain.events.tenant;

import com.yaocode.sts.auth.domain.aggregate.TenantInfoAggregate;
import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.composites.LoginConfig;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import lombok.Getter;

/**
 * 租户登录配置更新事件
 */
@Getter
public class TenantLoginConfigUpdatedEvent extends AggregateDomainEvent<TenantId> {

    private final TenantId tenantId;
    private final LoginConfig oldConfig;
    private final LoginConfig newConfig;

    public TenantLoginConfigUpdatedEvent(TenantInfoAggregate tenant, LoginConfig oldConfig, LoginConfig newConfig) {
        super(tenant, EventTypeEnums.TENANT_LOGIN_CONFIG_UPDATED.getCode());
        this.tenantId = tenant.getId();
        this.oldConfig = oldConfig;
        this.newConfig = newConfig;
    }

    public TenantLoginConfigUpdatedEvent(TenantId tenantId, LoginConfig oldConfig, LoginConfig newConfig) {
        super(tenantId.getValue(), AggregateTypeEnums.TENANT.getCode(), EventTypeEnums.TENANT_LOGIN_CONFIG_UPDATED.getCode());
        this.tenantId = tenantId;
        this.oldConfig = oldConfig;
        this.newConfig = newConfig;
    }
}