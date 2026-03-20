package com.yaocode.sts.auth.domain.entity;

import com.yaocode.sts.auth.domain.valueobjects.identifiers.OrganizationId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.TenantId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.OrganizationCode;
import com.yaocode.sts.common.domain.model.AbstractAggregate;
import lombok.Getter;

/**
 * 组织机构实体类
 * @author: Jin-LiangBo
 * @date: 2025年10月12日 13:38
 */
@Getter
public class OrganizationInfoEntity extends AbstractAggregate<OrganizationId> {

    private OrganizationInfoEntity(OrganizationId organizationId) {
        super(organizationId);
    }

    /**
     * 租户id
     */
    private TenantId tenantId;
    /**
     * 组织机构名称
     */
    private String organizationName;
    /**
     * 组织机构编码
     */
    private OrganizationCode organizationCode;
    /**
     * 组织机构描述
     */
    private String organizationDesc;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 父id
     */
    private OrganizationId parentId;

    public static OrganizationInfoEntity build(
            OrganizationId organizationId,
            TenantId tenantId,
            String organizationName,
            OrganizationCode organizationCode,
            String organizationDesc,
            Integer sort,
            OrganizationId parentId
    ) {
        OrganizationInfoEntity entity = new OrganizationInfoEntity(organizationId);
        entity.tenantId = tenantId;
        entity.organizationName = organizationName;
        entity.organizationCode = organizationCode;
        entity.organizationDesc = organizationDesc;
        entity.sort = sort;
        entity.parentId = parentId;
        return entity;
    }
}
