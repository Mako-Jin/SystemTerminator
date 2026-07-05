package com.yaocode.sts.auth.infrastructure.converter;

import com.yaocode.sts.auth.domain.entity.TenantInfoEntity;
import com.yaocode.sts.auth.infrastructure.po.TenantInfoPo;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-04T23:16:37+0800",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.9 (Oracle Corporation)"
)
@Component
public class TenantInfoConverterImpl implements TenantInfoConverter {

    @Override
    public TenantInfoPo toPo(TenantInfoEntity tenantInfoEntity) {
        if ( tenantInfoEntity == null ) {
            return null;
        }

        TenantInfoPo tenantInfoPo = new TenantInfoPo();

        tenantInfoPo.setTenantId( tenantIdToString( tenantInfoEntity.getId() ) );
        tenantInfoPo.setTenantCode( tenantCodeToString( tenantInfoEntity.getTenantCode() ) );
        tenantInfoPo.setParentId( tenantIdToString( tenantInfoEntity.getParentId() ) );
        tenantInfoPo.setCreateUserId( tenantInfoEntity.getCreateUserId() );
        tenantInfoPo.setCreateUserName( tenantInfoEntity.getCreateUserName() );
        tenantInfoPo.setUpdateUserId( tenantInfoEntity.getUpdateUserId() );
        tenantInfoPo.setUpdateUserName( tenantInfoEntity.getUpdateUserName() );
        tenantInfoPo.setCreateTime( tenantInfoEntity.getCreateTime() );
        tenantInfoPo.setUpdateTime( tenantInfoEntity.getUpdateTime() );
        tenantInfoPo.setTenantName( tenantInfoEntity.getTenantName() );
        tenantInfoPo.setTenantDesc( tenantInfoEntity.getTenantDesc() );
        if ( tenantInfoEntity.getTenantStatus() != null ) {
            tenantInfoPo.setTenantStatus( tenantInfoEntity.getTenantStatus().ordinal() );
        }
        tenantInfoPo.setTenantLevel( tenantInfoEntity.getTenantLevel() );
        if ( tenantInfoEntity.getAllowRegister() != null ) {
            tenantInfoPo.setAllowRegister( tenantInfoEntity.getAllowRegister().ordinal() );
        }
        if ( tenantInfoEntity.getAllowAdd() != null ) {
            tenantInfoPo.setAllowAdd( tenantInfoEntity.getAllowAdd().ordinal() );
        }

        return tenantInfoPo;
    }
}
