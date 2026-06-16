package com.yaocode.sts.auth.application.converter;

import com.yaocode.sts.auth.application.dto.response.PreLoginResponseDto;
import com.yaocode.sts.auth.domain.entity.LoginSuccessEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AuthApplicationConverter {

    AuthApplicationConverter INSTANCE = Mappers.getMapper(AuthApplicationConverter.class);

    default PreLoginResponseDto toLoginSuccessDto(LoginSuccessEntity loginSuccessEntity) {
        PreLoginResponseDto preLoginResponseDto = new PreLoginResponseDto();

        return preLoginResponseDto;
    }

}
