package com.yaocode.sts.auth.domain.entity.relation;

import com.yaocode.sts.auth.domain.enums.OrgUserRelationTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.OrganizationId;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import com.yaocode.sts.common.domain.valueobject.UserId;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RelOrganizationUserEntity {

    private final Long relId;
    private final TenantId tenantId;
    private final OrganizationId organizationId;
    private final UserId userId;
    private OrgUserRelationTypeEnums relationType;
    private String positionName;
    private Boolean isPrimaryPosition;
    private LocalDateTime effectiveFrom;
    private LocalDateTime effectiveTo;

    public RelOrganizationUserEntity(Long relId, TenantId tenantId, OrganizationId organizationId, UserId userId) {
        this.relId = relId;
        this.tenantId = tenantId;
        this.organizationId = organizationId;
        this.userId = userId;
    }
}
