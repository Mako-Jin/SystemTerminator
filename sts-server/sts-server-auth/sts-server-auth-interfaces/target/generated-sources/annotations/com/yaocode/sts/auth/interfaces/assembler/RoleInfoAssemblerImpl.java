package com.yaocode.sts.auth.interfaces.assembler;

import com.yaocode.sts.auth.application.dto.RoleInfoDto;
import com.yaocode.sts.auth.interfaces.model.params.CreateRoleParams;
import com.yaocode.sts.auth.interfaces.model.vo.RoleInfoVo;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-04T23:16:44+0800",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.9 (Oracle Corporation)"
)
@Component
public class RoleInfoAssemblerImpl implements RoleInfoAssembler {

    @Override
    public RoleInfoDto toDto(CreateRoleParams roleParams) {
        if ( roleParams == null ) {
            return null;
        }

        RoleInfoDto roleInfoDto = new RoleInfoDto();

        roleInfoDto.setRoleCode( roleParams.getRoleCode() );
        roleInfoDto.setRoleName( roleParams.getRoleName() );
        roleInfoDto.setRoleDesc( roleParams.getRoleDesc() );
        roleInfoDto.setParentId( roleParams.getParentId() );
        roleInfoDto.setTenantId( roleParams.getTenantId() );

        return roleInfoDto;
    }

    @Override
    public RoleInfoVo toVo(RoleInfoDto roleInfoDto) {
        if ( roleInfoDto == null ) {
            return null;
        }

        RoleInfoVo.RoleInfoVoBuilder roleInfoVo = RoleInfoVo.builder();

        roleInfoVo.roleId( roleInfoDto.getRoleId() );
        roleInfoVo.roleCode( roleInfoDto.getRoleCode() );
        roleInfoVo.roleName( roleInfoDto.getRoleName() );
        roleInfoVo.roleDesc( roleInfoDto.getRoleDesc() );
        roleInfoVo.parentId( roleInfoDto.getParentId() );
        roleInfoVo.tenantId( roleInfoDto.getTenantId() );

        return roleInfoVo.build();
    }
}
