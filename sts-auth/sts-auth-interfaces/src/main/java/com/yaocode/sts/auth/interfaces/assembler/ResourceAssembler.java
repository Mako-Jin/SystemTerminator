package com.yaocode.sts.auth.interfaces.assembler;

import com.yaocode.sts.auth.application.dto.ResourceDto;
import com.yaocode.sts.auth.interfaces.model.params.CreateResourceParams;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年11月13日 22:51
 */
@Mapper(componentModel = "spring")
public interface ResourceAssembler {

    ResourceAssembler INSTANCE = Mappers.getMapper(ResourceAssembler.class);

    /**
     * 参数对象转dto
     * @param resourceParams 参数对象
     * @return ResourceDto
     */
    ResourceDto toDto(CreateResourceParams resourceParams);

}
