package com.yaocode.sts.auth.interfaces.assembler;

import com.yaocode.sts.auth.application.dto.UserGroupDto;
import com.yaocode.sts.auth.interfaces.model.params.CreateUserGroupParams;
import com.yaocode.sts.auth.interfaces.model.vo.UserGroupVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年10月28日 21:41
 */
@Mapper(componentModel = "spring")
public interface UserGroupAssembler {

    UserGroupAssembler INSTANCE = Mappers.getMapper(UserGroupAssembler.class);

    /**
     * 参数对象转dto
     * @param userGroupParams 参数对象
     * @return UserGroupDto
     */
    UserGroupDto toDto(CreateUserGroupParams userGroupParams);

    /**
     * dto转Vo
     * @param userGroupDto 数据传输对象
     * @return UserGroupVo
     */
    UserGroupVo toVo(UserGroupDto userGroupDto);

}
