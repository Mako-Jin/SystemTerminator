package com.yaocode.sts.auth.infrastructure.converter;

import com.yaocode.sts.auth.domain.entity.OrganizationInfoEntity;
import com.yaocode.sts.auth.domain.entity.RoleInfoEntity;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.OrganizationId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.RoleId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.TenantId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.OrganizationCode;
import com.yaocode.sts.auth.domain.valueobjects.primitives.RoleCode;
import com.yaocode.sts.auth.infrastructure.po.OrganizationInfoPo;
import com.yaocode.sts.auth.infrastructure.po.RoleInfoPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年10月27日 22:44
 */
@Mapper(componentModel = "spring")
public interface OrganizationConverter {

    OrganizationConverter INSTANCE = Mappers.getMapper(OrganizationConverter.class);

    /**
     * entity转Po
     * @param organizationInfoEntity OrganizationInfoEntity
     * @return OrganizationInfoPo
     */
    @Mapping(target = "organizationId", source = "organizationInfoEntity.id", qualifiedByName = "organizationIdToString")
    @Mapping(target = "organizationCode", source = "organizationInfoEntity.organizationCode", qualifiedByName = "organizationCodeToString")
    @Mapping(target = "tenantId", source = "organizationInfoEntity.tenantId", qualifiedByName = "tenantIdToString")
    OrganizationInfoPo toPo(OrganizationInfoEntity organizationInfoEntity);

    /**
     * Po转entity
     * @param organizationInfoPo OrganizationInfoPo
     * @return OrganizationInfoEntity
     */
    @Mapping(target = "id", source = "organizationInfoPo.organizationId", qualifiedByName = "stringToOrganizationId")
    @Mapping(target = "organizationCode", source = "organizationInfoPo.organizationCode", qualifiedByName = "stringToOrganizationCode")
    @Mapping(target = "tenantId", source = "organizationInfoPo.tenantId", qualifiedByName = "stringToTenantId")
    OrganizationInfoEntity toEntity(OrganizationInfoPo organizationInfoPo);

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
     * @param organizationId String organizationId
     * @return OrganizationId
     */
    @Named("stringToOrganizationId")
    default OrganizationId stringToOrganizationId(String organizationId) {
        return organizationId != null ? OrganizationId.of(organizationId) : null;
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
     * @param tenantId String tenantId
     * @return TenantId
     */
    @Named("stringToTenantId")
    default TenantId stringToTenantId(String tenantId) {
        return tenantId != null ? TenantId.of(tenantId) : null;
    }
}
