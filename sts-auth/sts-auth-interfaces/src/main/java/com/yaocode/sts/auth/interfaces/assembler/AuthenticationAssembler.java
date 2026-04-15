package com.yaocode.sts.auth.interfaces.assembler;

import com.yaocode.sts.auth.application.dto.AuthenticationDto;
import com.yaocode.sts.auth.interfaces.model.params.login.LoginRequestParams;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 参数转化器
 * @author: Jin-LiangBo
 * @date: 2026年04月14日 10:49
 */
@Mapper(componentModel = "spring")
public interface AuthenticationAssembler {

    AuthenticationAssembler INSTANCE = Mappers.getMapper(AuthenticationAssembler.class);

    /**
     * 参数对象转dto
     * @param loginRequestParams 参数对象
     * @return AuthenticationDto
     */
    AuthenticationDto toDto(LoginRequestParams loginRequestParams);

}
