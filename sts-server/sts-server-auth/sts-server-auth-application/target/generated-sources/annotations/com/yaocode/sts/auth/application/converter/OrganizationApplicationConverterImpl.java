package com.yaocode.sts.auth.application.converter;

import com.yaocode.sts.auth.application.dto.OrganizationDto;
import com.yaocode.sts.auth.domain.entity.OrganizationInfoEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-04T23:16:40+0800",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.9 (Oracle Corporation)"
)
@Component
public class OrganizationApplicationConverterImpl implements OrganizationApplicationConverter {

    @Override
    public OrganizationDto toDto(OrganizationInfoEntity entity) {
        if ( entity == null ) {
            return null;
        }

        OrganizationDto organizationDto = new OrganizationDto();

        organizationDto.setOrganizationId( organizationIdToString( entity.getId() ) );
        organizationDto.setOrganizationCode( organizationCodeToString( entity.getOrganizationCode() ) );
        organizationDto.setTenantId( tenantIdToString( entity.getTenantId() ) );
        organizationDto.setParentId( organizationIdToString( entity.getParentId() ) );
        organizationDto.setOrganizationName( entity.getOrganizationName() );
        organizationDto.setOrganizationDesc( entity.getOrganizationDesc() );
        organizationDto.setSort( entity.getSort() );

        return organizationDto;
    }
}
