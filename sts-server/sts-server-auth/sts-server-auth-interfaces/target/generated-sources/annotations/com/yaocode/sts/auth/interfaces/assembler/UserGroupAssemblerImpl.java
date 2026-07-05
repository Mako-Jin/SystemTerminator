package com.yaocode.sts.auth.interfaces.assembler;

import com.yaocode.sts.auth.application.dto.UserGroupDto;
import com.yaocode.sts.auth.interfaces.model.params.CreateUserGroupParams;
import com.yaocode.sts.auth.interfaces.model.vo.UserGroupVo;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-04T23:16:44+0800",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.9 (Oracle Corporation)"
)
@Component
public class UserGroupAssemblerImpl implements UserGroupAssembler {

    @Override
    public UserGroupDto toDto(CreateUserGroupParams userGroupParams) {
        if ( userGroupParams == null ) {
            return null;
        }

        UserGroupDto userGroupDto = new UserGroupDto();

        userGroupDto.setUserGroupCode( userGroupParams.getUserGroupCode() );
        userGroupDto.setUserGroupName( userGroupParams.getUserGroupName() );
        userGroupDto.setUserGroupDesc( userGroupParams.getUserGroupDesc() );
        userGroupDto.setParentId( userGroupParams.getParentId() );
        userGroupDto.setTenantId( userGroupParams.getTenantId() );

        return userGroupDto;
    }

    @Override
    public UserGroupVo toVo(UserGroupDto userGroupDto) {
        if ( userGroupDto == null ) {
            return null;
        }

        UserGroupVo userGroupVo = new UserGroupVo();

        return userGroupVo;
    }
}
