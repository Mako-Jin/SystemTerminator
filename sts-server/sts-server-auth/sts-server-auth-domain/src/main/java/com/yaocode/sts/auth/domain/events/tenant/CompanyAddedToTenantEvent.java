package com.yaocode.sts.auth.domain.events.tenant;

import com.yaocode.sts.auth.domain.aggregate.TenantInfoAggregate;
import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.CompanyId;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import lombok.Getter;

/**
 * 公司添加到租户事件
 */
@Getter
public class CompanyAddedToTenantEvent extends AggregateDomainEvent<TenantId> {

    private final TenantId tenantId;
    private final CompanyId companyId;
    private final String companyName;

    public CompanyAddedToTenantEvent(TenantInfoAggregate tenant, CompanyId companyId, String companyName) {
        super(tenant, EventTypeEnums.COMPANY_ADDED_TO_TENANT.getCode());
        this.tenantId = tenant.getId();
        this.companyId = companyId;
        this.companyName = companyName;
    }

    public CompanyAddedToTenantEvent(TenantId tenantId, CompanyId companyId, String companyName) {
        super(tenantId.getValue(), AggregateTypeEnums.TENANT.getCode(), EventTypeEnums.COMPANY_ADDED_TO_TENANT.getCode());
        this.tenantId = tenantId;
        this.companyId = companyId;
        this.companyName = companyName;
    }
}
