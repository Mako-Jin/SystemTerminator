package com.yaocode.sts.auth.application.converter;

import com.yaocode.sts.auth.application.dto.ResourceDto;
import com.yaocode.sts.auth.domain.entity.ResourceEntity;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ResourceId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.ResourceValue;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年11月13日 23:01
 */
@Mapper(componentModel = "spring")
public interface ResourceApplicationConverter {

    ResourceApplicationConverter INSTANCE = Mappers.getMapper(ResourceApplicationConverter.class);

    /**
     * dto转Entity
     * @param resourceDto dto
     * @return ResourceEntity
     */
    @Mapping(target = "resourceId", source = "resourceDto.resourceId", qualifiedByName = "stringToResourceId")
    @Mapping(target = "resourceValue", source = "resourceDto.resourceValue", qualifiedByName = "stringToResourceValue")
    @Mapping(target = "isDeprecated", source = "resourceDto.isDeprecated", defaultValue = "0")
    @Mapping(target = "isWhiteList", source = "resourceDto.isWhiteList", defaultValue = "0")
    ResourceEntity toEntity(ResourceDto resourceDto);

    /**
     * 值对象与基本类型的转换方法
     * @param resourceId ResourceId
     * @return String resourceId
     */
    @Named("resourceIdToString")
    default String resourceIdToString(ResourceId resourceId) {
        return resourceId != null ? resourceId.getValue() : null;
    }

    /**
     * 值对象与基本类型的转换方法
     * @param id String resourceId
     * @return ResourceId
     */
    @Named("stringToResourceId")
    default ResourceId stringToResourceId(String id) {
        return id != null ? ResourceId.of(id) : null;
    }

    /**
     * 值对象与基本类型的转换方法
     * @param resourceValue ResourceValue
     * @return String
     */
    @Named("resourceValueToString")
    default String resourceValueToString(ResourceValue resourceValue) {
        return resourceValue != null ? resourceValue.getValue() : null;
    }

    /**
     * 值对象与基本类型的转换方法
     * @param resourceValue String
     * @return ResourceValue
     */
    @Named("stringToResourceValue")
    default ResourceValue stringToResourceValue(String resourceValue) {
        return resourceValue != null ? ResourceValue.of(resourceValue) : null;
    }

}
