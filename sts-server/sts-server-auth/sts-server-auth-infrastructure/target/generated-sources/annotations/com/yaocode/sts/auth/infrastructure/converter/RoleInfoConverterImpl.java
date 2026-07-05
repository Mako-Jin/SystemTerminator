package com.yaocode.sts.auth.infrastructure.converter;

import com.yaocode.sts.auth.domain.entity.RoleInfoEntity;
import com.yaocode.sts.auth.infrastructure.po.RoleInfoPo;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-04T23:16:37+0800",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.9 (Oracle Corporation)"
)
@Component
public class RoleInfoConverterImpl implements RoleInfoConverter {

    @Override
    public RoleInfoPo toPo(RoleInfoEntity roleInfoEntity) {
        if ( roleInfoEntity == null ) {
            return null;
        }

        RoleInfoPo roleInfoPo = new RoleInfoPo();

        roleInfoPo.setRoleId( roleIdToString( roleInfoEntity.getId() ) );
        roleInfoPo.setRoleCode( roleCodeToString( roleInfoEntity.getRoleCode() ) );
        roleInfoPo.setTenantId( tenantIdToString( roleInfoEntity.getTenantId() ) );
        roleInfoPo.setParentId( roleIdToString( roleInfoEntity.getParentId() ) );
        roleInfoPo.setRoleName( roleInfoEntity.getRoleName() );
        roleInfoPo.setRoleDesc( roleInfoEntity.getRoleDesc() );
        if ( roleInfoEntity.getIsDefault() != null ) {
            roleInfoPo.setIsDefault( roleInfoEntity.getIsDefault().ordinal() );
        }

        return roleInfoPo;
    }
}
