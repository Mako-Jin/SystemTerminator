package com.yaocode.sts.auth.application.converter;

import com.yaocode.sts.auth.application.dto.ResourceDto;
import com.yaocode.sts.auth.domain.entity.ResourceEntity;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ResourceId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.ResourceValue;
import com.yaocode.sts.common.resources.model.ApiResourcesModel;
import com.yaocode.sts.common.resources.model.ModuleResourcesModel;
import com.yaocode.sts.common.resources.model.ResourcesModel;
import com.yaocode.sts.common.resources.model.ServerResourcesModel;
import com.yaocode.sts.common.resources.model.ServiceResourcesModel;
import com.yaocode.sts.common.resources.model.SystemResourcesModel;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
    default ResourceEntity toEntity(ResourceDto resourceDto) {
        return ResourceEntity.build(
                ResourceValue.of(resourceDto.getResourceValue()),
                resourceDto.getResourceName(),
                resourceDto.getResourceDesc(),
                resourceDto.getResourceType(),
                resourceDto.getRequestUrl(),
                resourceDto.getRequestMethod(),
                resourceDto.getParentCode(),
                resourceDto.getIcon(),
                resourceDto.getVersion()
        );
    }

    /**
     * 批量转换 to Entity
     * @param resourceDtoList 资源Dto列表
     * @return List<ResourceEntity> entity列表
     */
    List<ResourceEntity> toEntityList(List<ResourceDto> resourceDtoList);

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
     * 批量转换 to Entity
     * @param resourcesModelList 资源模型列表
     * @return List<ResourceEntity> entity列表
     */
    default List<ResourceEntity> batchToEntity(List<ResourcesModel> resourcesModelList) {
        if (CollectionUtils.isEmpty(resourcesModelList)) {
            return Collections.emptyList();
        }
        List<ResourceEntity> result = new ArrayList<>();
        resourcesModelList.forEach(resourcesModel -> {
            ResourceEntity entity;
            if (resourcesModel instanceof SystemResourcesModel systemResourcesModel) {
                entity = ResourceEntity.build(
                        ResourceValue.of(systemResourcesModel.getCode()),
                        systemResourcesModel.getName(),
                        systemResourcesModel.getDesc(),
                        systemResourcesModel.getType().getCode(),
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList(),
                        systemResourcesModel.getIcon(),
                        systemResourcesModel.getVersion()
                );
            } else if (resourcesModel instanceof ServerResourcesModel serverResourcesModel) {
                entity = ResourceEntity.build(
                        ResourceValue.of(serverResourcesModel.getCode()),
                        serverResourcesModel.getName(),
                        serverResourcesModel.getDesc(),
                        serverResourcesModel.getType().getCode(),
                        Collections.emptyList(),
                        Collections.emptyList(),
                        serverResourcesModel.getParentCode(),
                        serverResourcesModel.getIcon(),
                        serverResourcesModel.getVersion()
                );
            } else if (resourcesModel instanceof ServiceResourcesModel serviceResourcesModel) {
                entity = ResourceEntity.build(
                        ResourceValue.of(serviceResourcesModel.getCode()),
                        serviceResourcesModel.getName(),
                        serviceResourcesModel.getDesc(),
                        serviceResourcesModel.getType().getCode(),
                        StringUtils.hasText(serviceResourcesModel.getPath()) ?
                                Collections.singletonList(serviceResourcesModel.getPath()) :
                                Collections.emptyList(),
                        Collections.emptyList(),
                        serviceResourcesModel.getParentCode(),
                        serviceResourcesModel.getIcon(),
                        serviceResourcesModel.getVersion()
                );
            } else if (resourcesModel instanceof ModuleResourcesModel moduleResourcesModel) {
                entity = ResourceEntity.build(
                        ResourceValue.of(moduleResourcesModel.getCode()),
                        moduleResourcesModel.getName(),
                        moduleResourcesModel.getDesc(),
                        moduleResourcesModel.getType().getCode(),
                        Objects.nonNull(moduleResourcesModel.getPath()) ?
                                moduleResourcesModel.getPath() : Collections.emptyList(),
                        Collections.emptyList(),
                        moduleResourcesModel.getParentCode(),
                        moduleResourcesModel.getIcon(),
                        moduleResourcesModel.getVersion()
                );
            } else if (resourcesModel instanceof ApiResourcesModel apiResourcesModel) {
                List<String> requestMethods =
                        apiResourcesModel.getRequestMethod().stream().map(Enum::name).toList();
                entity = ResourceEntity.build(
                        ResourceValue.of(apiResourcesModel.getCode()),
                        apiResourcesModel.getName(),
                        apiResourcesModel.getDesc(),
                        apiResourcesModel.getType().getCode(),
                        apiResourcesModel.getPath(),
                        requestMethods,
                        apiResourcesModel.getParentCode(),
                        null,
                        apiResourcesModel.getVersion()
                );
                entity.reconstructionWhiteList(apiResourcesModel.getIsWhiteList());
            } else {
                throw new IllegalArgumentException("不支持的资源类型");
            }
            entity.reconstructionEnable(resourcesModel.getIsEnabled());
            entity.reconstructionDeprecated(resourcesModel.getIsDeprecated());
            result.add(entity);
        });
        return result;
    }

}
