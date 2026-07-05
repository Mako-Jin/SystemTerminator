package com.yaocode.sts.auth.interfaces.assembler;

import com.yaocode.sts.auth.application.dto.OrganizationDto;
import com.yaocode.sts.auth.interfaces.model.params.CreateOrganizationParams;
import com.yaocode.sts.auth.interfaces.model.vo.OrganizationInfoVo;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-04T23:16:44+0800",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.9 (Oracle Corporation)"
)
@Component
public class OrganizationAssemblerImpl implements OrganizationAssembler {

    @Override
    public OrganizationDto toDto(CreateOrganizationParams organizationParams) {
        if ( organizationParams == null ) {
            return null;
        }

        OrganizationDto organizationDto = new OrganizationDto();

        organizationDto.setOrganizationName( organizationParams.getOrganizationName() );
        organizationDto.setOrganizationCode( organizationParams.getOrganizationCode() );
        organizationDto.setOrganizationDesc( organizationParams.getOrganizationDesc() );
        organizationDto.setSort( organizationParams.getSort() );
        organizationDto.setParentId( organizationParams.getParentId() );
        organizationDto.setTenantId( organizationParams.getTenantId() );

        return organizationDto;
    }

    @Override
    public OrganizationInfoVo toVo(OrganizationDto organizationDto) {
        if ( organizationDto == null ) {
            return null;
        }

        OrganizationInfoVo organizationInfoVo = new OrganizationInfoVo();

        organizationInfoVo.setOrganizationId( organizationDto.getOrganizationId() );
        organizationInfoVo.setOrganizationName( organizationDto.getOrganizationName() );
        organizationInfoVo.setOrganizationCode( organizationDto.getOrganizationCode() );
        organizationInfoVo.setOrganizationDesc( organizationDto.getOrganizationDesc() );
        organizationInfoVo.setSort( organizationDto.getSort() );
        organizationInfoVo.setParentId( organizationDto.getParentId() );
        organizationInfoVo.setTenantId( organizationDto.getTenantId() );

        return organizationInfoVo;
    }
}
