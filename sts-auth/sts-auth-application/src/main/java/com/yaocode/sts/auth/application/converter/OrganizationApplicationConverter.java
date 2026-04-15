package com.yaocode.sts.auth.application.converter;

import com.yaocode.sts.auth.application.dto.OrganizationDto;
import com.yaocode.sts.auth.domain.entity.OrganizationInfoEntity;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.OrganizationId;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.OrganizationCode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年10月26日 21:49
 */
@Mapper(componentModel = "spring")
public interface OrganizationApplicationConverter {

    OrganizationApplicationConverter INSTANCE = Mappers.getMapper(OrganizationApplicationConverter.class);

    /**
     * dto转Entity
     * @param organizationDto dto
     * @return OrganizationInfoEntity
     */
    default OrganizationInfoEntity toEntity(OrganizationDto organizationDto) {
        return OrganizationInfoEntity.build(
                stringToTenantId(organizationDto.getTenantId()),
                organizationDto.getOrganizationName(),
                stringToOrganizationCode(organizationDto.getOrganizationCode()),
                organizationDto.getOrganizationDesc(),
                organizationDto.getSort(),
                stringToOrganizationId(organizationDto.getParentId())
        );
    }

    /**
     * entity转Dto
     * @param entity OrganizationInfoEntity
     * @return OrganizationDto
     */
    @Mapping(target = "organizationId", source = "entity.id", qualifiedByName = "organizationIdToString")
    @Mapping(target = "organizationCode", source = "entity.organizationCode", qualifiedByName = "organizationCodeToString")
    @Mapping(target = "tenantId", source = "entity.tenantId", qualifiedByName = "tenantIdToString")
    @Mapping(target = "parentId", source = "entity.parentId", qualifiedByName = "organizationIdToString")
    OrganizationDto toDto(OrganizationInfoEntity entity);

    /**
     * 值对象与基本类型的转换方法
     * @param organizationId OrganizationId
     * @return String organizationId
     */
    @Named("organizationIdToString")
    default String organizationIdToString(OrganizationId organizationId) {
        return organizationId != null ? organizationId.getValue() : null;
    }

    /**
     * 值对象与基本类型的转换方法
     * @param id String organizationId
     * @return OrganizationId
     */
    @Named("stringToOrganizationId")
    default OrganizationId stringToOrganizationId(String id) {
        return id != null ? OrganizationId.of(id) : null;
    }

    /**
     * 值对象与基本类型的转换方法
     * @param organizationCode OrganizationCode
     * @return String
     */
    @Named("organizationCodeToString")
    default String organizationCodeToString(OrganizationCode organizationCode) {
        return organizationCode != null ? organizationCode.getValue() : null;
    }

    /**
     * 值对象与基本类型的转换方法
     * @param organizationCode String
     * @return OrganizationCode
     */
    @Named("stringToOrganizationCode")
    default OrganizationCode stringToOrganizationCode(String organizationCode) {
        return organizationCode != null ? OrganizationCode.of(organizationCode) : null;
    }

    /**
     * 值对象与基本类型的转换方法
     * @param tenantId TenantId
     * @return String tenantId
     */
    @Named("tenantIdToString")
    default String tenantIdToString(TenantId tenantId) {
        return tenantId != null ? tenantId.getValue() : null;
    }

    /**
     * 值对象与基本类型的转换方法
     * @param id String tenantId
     * @return TenantId
     */
    @Named("stringToTenantId")
    default TenantId stringToTenantId(String id) {
        return id != null ? TenantId.of(id) : null;
    }

}
