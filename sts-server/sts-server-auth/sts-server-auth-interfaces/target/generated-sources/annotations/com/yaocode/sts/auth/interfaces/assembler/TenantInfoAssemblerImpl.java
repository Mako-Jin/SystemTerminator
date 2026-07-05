package com.yaocode.sts.auth.interfaces.assembler;

import com.yaocode.sts.auth.application.dto.TenantInfoDto;
import com.yaocode.sts.auth.interfaces.model.params.CreateTenantParams;
import com.yaocode.sts.auth.interfaces.model.vo.TenantInfoVo;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-04T23:16:44+0800",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.9 (Oracle Corporation)"
)
@Component
public class TenantInfoAssemblerImpl implements TenantInfoAssembler {

    @Override
    public TenantInfoDto toDto(CreateTenantParams tenantParams) {
        if ( tenantParams == null ) {
            return null;
        }

        TenantInfoDto tenantInfoDto = new TenantInfoDto();

        tenantInfoDto.setTenantName( tenantParams.getTenantName() );
        tenantInfoDto.setTenantCode( tenantParams.getTenantCode() );
        tenantInfoDto.setTenantDesc( tenantParams.getTenantDesc() );
        tenantInfoDto.setAllowRegister( tenantParams.getAllowRegister() );
        tenantInfoDto.setAllowAdd( tenantParams.getAllowAdd() );
        tenantInfoDto.setParentId( tenantParams.getParentId() );

        return tenantInfoDto;
    }

    @Override
    public TenantInfoVo toVo(TenantInfoDto tenantInfoDto) {
        if ( tenantInfoDto == null ) {
            return null;
        }

        TenantInfoVo tenantInfoVo = new TenantInfoVo();

        return tenantInfoVo;
    }
}
