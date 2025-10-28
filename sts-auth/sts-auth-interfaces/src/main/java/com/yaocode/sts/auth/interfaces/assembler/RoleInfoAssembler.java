package com.yaocode.sts.auth.interfaces.assembler;

import com.yaocode.sts.auth.application.dto.RoleInfoDto;
import com.yaocode.sts.auth.interfaces.model.params.CreateRoleParams;
import com.yaocode.sts.auth.interfaces.model.vo.RoleInfoVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年10月28日 20:53
 */
@Mapper(componentModel = "spring")
public interface RoleInfoAssembler {

    RoleInfoAssembler INSTANCE = Mappers.getMapper(RoleInfoAssembler.class);

    /**
     * 参数对象转dto
     * @param roleParams 参数对象
     * @return RoleInfoDto
     */
    RoleInfoDto toDto(CreateRoleParams roleParams);

    /**
     * dto转Vo
     * @param roleInfoDto 数据传输对象
     * @return RoleInfoVo
     */
    RoleInfoVo toVo(RoleInfoDto roleInfoDto);

}
