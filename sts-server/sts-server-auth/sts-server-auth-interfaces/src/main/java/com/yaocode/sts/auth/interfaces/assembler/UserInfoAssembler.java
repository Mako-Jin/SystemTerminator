package com.yaocode.sts.auth.interfaces.assembler;


import com.yaocode.sts.auth.application.dto.UserInfoDto;
import com.yaocode.sts.auth.interfaces.model.params.CreateUserParams;
import com.yaocode.sts.auth.interfaces.model.vo.UserInfoVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * view or param 转dto
 * @author: Jin-LiangBo
 * @date: 2025年10月07日 21:49
 */
@Mapper(componentModel = "spring")
public interface UserInfoAssembler {

    UserInfoAssembler INSTANCE = Mappers.getMapper(UserInfoAssembler.class);

    /**
     * 参数对象转dto
     * @param user 参数对象
     * @return UserInfoDto
     */
    UserInfoDto toDto(CreateUserParams user);

    /**
     * dto转Vo
     * @param userDto 数据传输对象
     * @return UserInfoDto
     */
    UserInfoVo toVo(UserInfoDto userDto);

}
