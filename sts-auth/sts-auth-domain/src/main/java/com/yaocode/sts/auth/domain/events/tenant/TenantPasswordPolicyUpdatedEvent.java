package com.yaocode.sts.auth.domain.events.tenant;

import com.yaocode.sts.auth.domain.aggregate.TenantInfoAggregate;
import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.composites.PasswordPolicy;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import lombok.Getter;

/**
 * 租户密码策略更新事件
 */
@Getter
public class TenantPasswordPolicyUpdatedEvent extends AggregateDomainEvent<TenantId> {

    private final TenantId tenantId;
    private final PasswordPolicy oldPolicy;
    private final PasswordPolicy newPolicy;

    public TenantPasswordPolicyUpdatedEvent(TenantInfoAggregate tenant, PasswordPolicy oldPolicy, PasswordPolicy newPolicy) {
        super(tenant, EventTypeEnums.TENANT_PASSWORD_POLICY_UPDATED.getCode());
        this.tenantId = tenant.getId();
        this.oldPolicy = oldPolicy;
        this.newPolicy = newPolicy;
    }

    public TenantPasswordPolicyUpdatedEvent(TenantId tenantId, PasswordPolicy oldPolicy, PasswordPolicy newPolicy) {
        super(tenantId.getValue(), AggregateTypeEnums.TENANT.getCode(), EventTypeEnums.TENANT_PASSWORD_POLICY_UPDATED.getCode());
        this.tenantId = tenantId;
        this.oldPolicy = oldPolicy;
        this.newPolicy = newPolicy;
    }
}
