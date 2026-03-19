package com.yaocode.sts.auth.infrastructure.converter;

import com.yaocode.sts.auth.domain.entity.ResourceEntity;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ResourceId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.ResourceValue;
import com.yaocode.sts.auth.domain.valueobjects.primitives.Version;
import com.yaocode.sts.auth.infrastructure.po.ResourcePo;
import com.yaocode.sts.common.tools.ListUtils;
import com.yaocode.sts.common.tools.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

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
    @Mapping(target = "resourceValue", source = "resourceEntity.identity.resourceValue", qualifiedByName = "resourceValueToString")
    @Mapping(target = "requestUrl", source = "resourceEntity.identity.requestUrl", qualifiedByName = "listToString")
    @Mapping(target = "requestMethod", source = "resourceEntity.identity.requestMethod", qualifiedByName = "listToString")
    @Mapping(target = "parentCode", source = "resourceEntity.parentCode", qualifiedByName = "listToString")
    @Mapping(target = "version", source = "resourceEntity.version", qualifiedByName = "versionToString")
    @Mapping(target = "resourceType", source = "resourceEntity.identity.resourceType")
    ResourcePo toPo(ResourceEntity resourceEntity);

    /**
     * 批量Entity转Po
     * @param resourceEntityList entity列表
     * @return List<ResourcePo>
     */
    List<ResourcePo> toPoList(List<ResourceEntity> resourceEntityList);

    /**
     * 值对象与基本类型的转换方法
     * @param version 版本对象
     * @return String 版本字符串
     */
    @Named("versionToString")
    default String versionToString(Version version) {
        return version != null ? version.getStr() : null;
    }

    /**
     * 值对象与基本类型的转换方法
     * @param version 版本对象
     * @return String 版本字符串
     */
    @Named("versionToString")
    default Version stringToVersion(String version) {
        return version != null ? Version.of(version) : null;
    }

    /**
     * 列表转换成逗号分割的字符串
     * @param list 数据列表
     * @return String 逗号分割的字符串
     */
    @Named("listToString")
    default String listToString(List<String> list) {
        return ListUtils.convertListToString(list);
    }

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

    /**
     * Po转Entity
     * @param resourcePo po数据
     * @return ResourceEntity
     */
    default ResourceEntity toEntity(ResourcePo resourcePo) {
        ResourceEntity entity = ResourceEntity.build(
                stringToResourceValue(resourcePo.getResourceValue()),
                resourcePo.getResourceName(),
                resourcePo.getResourceDesc(),
                resourcePo.getResourceType(),
                stringToList(resourcePo.getRequestUrl()),
                stringToList(resourcePo.getRequestMethod()),
                stringToList(resourcePo.getParentCode()),
                resourcePo.getIcon(),
                resourcePo.getVersion()
        );
        entity.setId(stringToResourceId(resourcePo.getResourceId()));
        entity.reconstructionWhiteList(resourcePo.getIsWhiteList());
        entity.reconstructionEnable(resourcePo.getIsEnabled());
        entity.reconstructionDeprecated(resourcePo.getIsDeprecated());
        return entity;
    }

    /**
     * 逗号分割的字符串转换成列表
     * @param value 逗号分割的字符串
     * @return List<String> 列表
     */
    @Named("stringToList")
    default List<String> stringToList(String value) {
        return StringUtils.convertStringToList(value);
    }

    /**
     * 批量Po转Entity
     * @param resourcePoList po列表
     * @return List<ResourceEntity>
     */
    List<ResourceEntity> toEntityList(List<ResourcePo> resourcePoList);

}
