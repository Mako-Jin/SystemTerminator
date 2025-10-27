package com.yaocode.sts.auth.interfaces.assembler;

import com.yaocode.sts.auth.application.dto.OrganizationDto;
import com.yaocode.sts.auth.interfaces.model.params.CreateOrganizationParams;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年10月25日 17:28
 */
@Mapper(componentModel = "spring")
public interface OrganizationAssembler {

    OrganizationAssembler INSTANCE = Mappers.getMapper(OrganizationAssembler.class);

    /**
     * 参数对象转dto
     * @param organizationParams 参数对象
     * @return OrganizationDto
     */
    OrganizationDto toDto(CreateOrganizationParams organizationParams);

}
