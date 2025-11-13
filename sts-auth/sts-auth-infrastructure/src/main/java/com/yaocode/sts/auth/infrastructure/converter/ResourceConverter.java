package com.yaocode.sts.auth.infrastructure.converter;

import com.yaocode.sts.auth.domain.entity.ResourceEntity;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ResourceId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.ResourceValue;
import com.yaocode.sts.auth.infrastructure.po.ResourcePo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年11月13日 23:27
 */
@Mapper(componentModel = "spring")
public interface ResourceConverter {

    ResourceConverter INSTANCE = Mappers.getMapper(ResourceConverter.class);

    /**
     * Entity转Po
     * @param resourceEntity entity
     * @return ResourcePo
     */
    @Mapping(target = "resourceId", source = "resourceEntity.id", qualifiedByName = "resourceIdToString")
    @Mapping(target = "resourceValue", source = "resourceEntity.resourceValue", qualifiedByName = "resourceValueToString")
    ResourcePo toPo(ResourceEntity resourceEntity);

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
