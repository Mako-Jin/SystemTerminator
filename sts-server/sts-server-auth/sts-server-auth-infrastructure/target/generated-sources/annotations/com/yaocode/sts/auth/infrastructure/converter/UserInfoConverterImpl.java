package com.yaocode.sts.auth.infrastructure.converter;

import com.yaocode.sts.auth.domain.entity.UserInfoEntity;
import com.yaocode.sts.auth.infrastructure.po.UserInfoPo;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-04T23:16:37+0800",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.9 (Oracle Corporation)"
)
@Component
public class UserInfoConverterImpl implements UserInfoConverter {

    @Override
    public UserInfoPo toPo(UserInfoEntity user) {
        if ( user == null ) {
            return null;
        }

        UserInfoPo userInfoPo = new UserInfoPo();

        userInfoPo.setUserId( userIdToString( user.getId() ) );
        userInfoPo.setUsername( usernameToString( user.getUsername() ) );
        userInfoPo.setCreateTime( user.getCreateTime() );
        userInfoPo.setUpdateTime( user.getUpdateTime() );
        if ( user.getMfaBound() != null ) {
            userInfoPo.setMfaBound( user.getMfaBound().ordinal() );
        }
        if ( user.getMfaType() != null ) {
            userInfoPo.setMfaType( user.getMfaType().ordinal() );
        }

        return userInfoPo;
    }
}
