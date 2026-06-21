package com.yaocode.sts.auth.domain.events.tenant;

import com.yaocode.sts.auth.domain.aggregate.TenantInfoAggregate;
import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.CompanyId;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import lombok.Getter;

/**
 * 公司从租户移除事件
 */
@Getter
public class CompanyRemovedFromTenantEvent extends AggregateDomainEvent<TenantId> {

    private final TenantId tenantId;
    private final CompanyId companyId;

    public CompanyRemovedFromTenantEvent(TenantInfoAggregate tenant, CompanyId companyId) {
        super(tenant, EventTypeEnums.COMPANY_REMOVED_FROM_TENANT.getCode());
        this.tenantId = tenant.getId();
        this.companyId = companyId;
    }

    public CompanyRemovedFromTenantEvent(TenantId tenantId, CompanyId companyId) {
        super(tenantId.getValue(), AggregateTypeEnums.TENANT.getCode(), EventTypeEnums.COMPANY_REMOVED_FROM_TENANT.getCode());
        this.tenantId = tenantId;
        this.companyId = companyId;
    }
}
