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
import org.mapstruct.Mapping;
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
    @Mapping(target = "resourceId", source = "resourceDto.resourceId", qualifiedByName = "stringToResourceId")
    @Mapping(target = "resourceValue", source = "resourceDto.resourceValue", qualifiedByName = "stringToResourceValue")
    @Mapping(target = "isDeprecated", source = "resourceDto.isDeprecated", defaultValue = "0")
    @Mapping(target = "isWhiteList", source = "resourceDto.isWhiteList", defaultValue = "0")
    ResourceEntity toEntity(ResourceDto resourceDto);

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
            ResourceEntity entity = new ResourceEntity(ResourceId.nextId());
            entity.setResourceValue(ResourceValue.of(resourcesModel.getCode()));
            entity.setResourceName(resourcesModel.getName());
            entity.setResourceDesc(resourcesModel.getDesc());
            entity.setResourceType(resourcesModel.getType().getCode());
            entity.setVersion(resourcesModel.getVersion());
            entity.setIsEnabled(resourcesModel.getIsEnabled());
            entity.setIsDeprecated(resourcesModel.getIsDeprecated());
            if (resourcesModel instanceof SystemResourcesModel systemResourcesModel) {
                entity.setIcon(systemResourcesModel.getIcon());
            } else if (resourcesModel instanceof ServerResourcesModel serverResourcesModel) {
                entity.setIcon(serverResourcesModel.getIcon());
                entity.setParentCode(serverResourcesModel.getParentCode());
            } else if (resourcesModel instanceof ServiceResourcesModel serviceResourcesModel) {
                entity.setIcon(serviceResourcesModel.getIcon());
                entity.setParentCode(serviceResourcesModel.getParentCode());
                if (StringUtils.hasText(serviceResourcesModel.getPath())) {
                    entity.setRequestUrl(Collections.singletonList(serviceResourcesModel.getPath()));
                }
            } else if (resourcesModel instanceof ModuleResourcesModel moduleResourcesModel) {
                entity.setIcon(moduleResourcesModel.getIcon());
                entity.setParentCode(moduleResourcesModel.getParentCode());
                if (Objects.nonNull(moduleResourcesModel.getPath())) {
                    entity.setRequestUrl(moduleResourcesModel.getPath());
                }
            } else if (resourcesModel instanceof ApiResourcesModel apiResourcesModel) {
                entity.setParentCode(apiResourcesModel.getParentCode());
                entity.setRequestUrl(apiResourcesModel.getPath());
                entity.setIsWhiteList(apiResourcesModel.getIsWhiteList());
                List<String> requestMethods =
                        apiResourcesModel.getRequestMethod().stream().map(Enum::name).toList();
                entity.setRequestMethod(requestMethods);
            }
            result.add(entity);
        });
        return result;
    }

}
