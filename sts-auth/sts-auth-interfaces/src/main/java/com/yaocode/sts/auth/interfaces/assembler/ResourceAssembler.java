package com.yaocode.sts.auth.interfaces.assembler;

import com.yaocode.sts.auth.application.dto.ResourceDto;
import com.yaocode.sts.auth.interfaces.model.params.BatchCreateResourceParams;
import com.yaocode.sts.auth.interfaces.model.params.CreateResourceParams;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    /**
     * 对象列表转DtoList
     * @param paramsList 参数列表
     * @return java.util.List<ResourceDto>
     */
    default List<ResourceDto> toDtoList(List<BatchCreateResourceParams> paramsList) {
        if (CollectionUtils.isEmpty(paramsList)) {
            return Collections.emptyList();
        }
        List<ResourceDto> resourceDtoList = new ArrayList<>();
        for (BatchCreateResourceParams batchCreateResourceParams : paramsList) {
            ResourceDto resourceDto = new ResourceDto();
            resourceDto.setResourceName(batchCreateResourceParams.getName());
            resourceDto.setResourceValue(batchCreateResourceParams.getCode());
            resourceDto.setResourceDesc(batchCreateResourceParams.getDesc());
            resourceDto.setResourceType(batchCreateResourceParams.getType());
            resourceDto.setRequestUrl(batchCreateResourceParams.getPath());
            resourceDto.setRequestMethod(batchCreateResourceParams.getRequestMethod());
            resourceDto.setIsDeprecated(batchCreateResourceParams.getIsDeprecated());
            resourceDto.setIsWhiteList(batchCreateResourceParams.getIsWhiteList());
            resourceDto.setIsEnabled(batchCreateResourceParams.getIsEnabled());
            resourceDto.setIcon(batchCreateResourceParams.getIcon());
            resourceDto.setVersion(batchCreateResourceParams.getVersion());
            resourceDto.setParentCode(batchCreateResourceParams.getParentCode());
            resourceDto.setContactInfoModelList(batchCreateResourceParams.getContactInfoModelList());
            resourceDtoList.add(resourceDto);
        }
        return resourceDtoList;
    }

}
