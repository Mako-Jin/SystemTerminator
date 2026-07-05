package com.yaocode.sts.auth.application.converter;

import com.yaocode.sts.auth.application.dto.UserInfoDto;
import com.yaocode.sts.auth.domain.entity.UserInfoEntity;
import com.yaocode.sts.auth.infrastructure.po.UserInfoPo;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-04T23:16:41+0800",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.9 (Oracle Corporation)"
)
@Component
public class UserInfoApplicationConverterImpl implements UserInfoApplicationConverter {

    @Override
    public UserInfoDto toDto(UserInfoEntity user) {
        if ( user == null ) {
            return null;
        }

        UserInfoDto userInfoDto = new UserInfoDto();

        userInfoDto.setUserId( userIdToString( user.getId() ) );
        userInfoDto.setUsername( usernameToString( user.getUsername() ) );
        userInfoDto.setCreateTime( user.getCreateTime() );
        userInfoDto.setUpdateTime( user.getUpdateTime() );

        return userInfoDto;
    }

    @Override
    public UserInfoDto toDto(UserInfoPo userPo) {
        if ( userPo == null ) {
            return null;
        }

        UserInfoDto userInfoDto = new UserInfoDto();

        userInfoDto.setUserId( userPo.getUserId() );
        userInfoDto.setUsername( userPo.getUsername() );
        userInfoDto.setIsEnabled( userPo.getIsEnabled() );
        userInfoDto.setCreateTime( userPo.getCreateTime() );
        userInfoDto.setUpdateTime( userPo.getUpdateTime() );

        return userInfoDto;
    }
}
