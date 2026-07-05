package com.yaocode.sts.auth.infrastructure.converter;

import com.yaocode.sts.auth.domain.entity.UserGroupEntity;
import com.yaocode.sts.auth.infrastructure.po.UserGroupPo;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-04T23:16:37+0800",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.9 (Oracle Corporation)"
)
@Component
public class UserGroupConverterImpl implements UserGroupConverter {

    @Override
    public UserGroupPo toPo(UserGroupEntity userGroupEntity) {
        if ( userGroupEntity == null ) {
            return null;
        }

        UserGroupPo userGroupPo = new UserGroupPo();

        userGroupPo.setUserGroupId( userGroupIdToString( userGroupEntity.getId() ) );
        userGroupPo.setUserGroupCode( userGroupCodeToString( userGroupEntity.getUserGroupCode() ) );
        userGroupPo.setTenantId( tenantIdToString( userGroupEntity.getTenantId() ) );
        userGroupPo.setParentId( userGroupIdToString( userGroupEntity.getParentId() ) );
        userGroupPo.setUserGroupName( userGroupEntity.getUserGroupName() );
        userGroupPo.setUserGroupDesc( userGroupEntity.getUserGroupDesc() );
        userGroupPo.setIsEnabled( userGroupEntity.getIsEnabled() );

        return userGroupPo;
    }
}
