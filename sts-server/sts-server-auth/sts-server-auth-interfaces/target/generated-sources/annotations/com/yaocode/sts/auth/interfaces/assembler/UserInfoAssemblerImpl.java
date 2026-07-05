package com.yaocode.sts.auth.interfaces.assembler;

import com.yaocode.sts.auth.application.dto.UserInfoDto;
import com.yaocode.sts.auth.interfaces.model.params.CreateUserParams;
import com.yaocode.sts.auth.interfaces.model.vo.UserInfoVo;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-04T23:16:44+0800",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.9 (Oracle Corporation)"
)
@Component
public class UserInfoAssemblerImpl implements UserInfoAssembler {

    @Override
    public UserInfoDto toDto(CreateUserParams user) {
        if ( user == null ) {
            return null;
        }

        UserInfoDto userInfoDto = new UserInfoDto();

        userInfoDto.setUsername( user.getUsername() );

        return userInfoDto;
    }

    @Override
    public UserInfoVo toVo(UserInfoDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        UserInfoVo.UserInfoVoBuilder userInfoVo = UserInfoVo.builder();

        userInfoVo.userId( userDto.getUserId() );
        userInfoVo.username( userDto.getUsername() );
        userInfoVo.createTime( userDto.getCreateTime() );
        userInfoVo.updateTime( userDto.getUpdateTime() );

        return userInfoVo.build();
    }
}
