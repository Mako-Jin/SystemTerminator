package com.yaocode.sts.auth.infrastructure.converter;

import com.yaocode.sts.auth.domain.entity.OrganizationInfoEntity;
import com.yaocode.sts.auth.infrastructure.po.OrganizationInfoPo;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-04T23:16:37+0800",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.9 (Oracle Corporation)"
)
@Component
public class OrganizationConverterImpl implements OrganizationConverter {

    @Override
    public OrganizationInfoPo toPo(OrganizationInfoEntity organizationInfoEntity) {
        if ( organizationInfoEntity == null ) {
            return null;
        }

        OrganizationInfoPo organizationInfoPo = new OrganizationInfoPo();

        organizationInfoPo.setOrganizationId( organizationIdToString( organizationInfoEntity.getId() ) );
        organizationInfoPo.setOrganizationCode( organizationCodeToString( organizationInfoEntity.getOrganizationCode() ) );
        organizationInfoPo.setTenantId( tenantIdToString( organizationInfoEntity.getTenantId() ) );
        organizationInfoPo.setParentId( organizationIdToString( organizationInfoEntity.getParentId() ) );
        organizationInfoPo.setOrganizationName( organizationInfoEntity.getOrganizationName() );
        organizationInfoPo.setOrganizationDesc( organizationInfoEntity.getOrganizationDesc() );
        organizationInfoPo.setSort( organizationInfoEntity.getSort() );

        return organizationInfoPo;
    }
}
