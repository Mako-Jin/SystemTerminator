package com.yaocode.sts.auth.domain.entity;

import com.yaocode.sts.auth.domain.valueobjects.identifiers.OrganizationId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.TenantId;
import com.yaocode.sts.common.domain.model.AbstractAggregate;
import lombok.Getter;
import lombok.Setter;

/**
 * 组织机构实体类
 * @author: Jin-LiangBo
 * @date: 2025年10月12日 13:38
 */
@Setter
@Getter
public class OrganizationInfoEntity extends AbstractAggregate<OrganizationId> {
    protected OrganizationInfoEntity(OrganizationId s) {
        super(s);
    }

    private TenantId tenantId;
    private String name;
    private String code;
    private OrganizationId parentId;
    // private OrganizationPath path;
    private Integer sortOrder;
}
