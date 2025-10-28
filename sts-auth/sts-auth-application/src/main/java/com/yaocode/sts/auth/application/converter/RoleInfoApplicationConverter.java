package com.yaocode.sts.auth.application.converter;

import com.yaocode.sts.auth.application.dto.RoleInfoDto;
import com.yaocode.sts.auth.domain.entity.RoleInfoEntity;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.RoleId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.TenantId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.RoleCode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年10月28日 21:15
 */
@Mapper(componentModel = "spring")
public interface RoleInfoApplicationConverter {

    RoleInfoApplicationConverter INSTANCE = Mappers.getMapper(RoleInfoApplicationConverter.class);

    /**
     * dto转Entity
     * @param roleDto dto
     * @return RoleInfoEntity
     */
    @Mapping(target = "roleId", source = "roleDto.roleId", qualifiedByName = "stringToRoleId")
    @Mapping(target = "roleCode", source = "roleDto.roleCode", qualifiedByName = "stringToRoleCode")
    @Mapping(target = "tenantId", source = "roleDto.tenantId", qualifiedByName = "stringToTenantId")
    RoleInfoEntity toEntity(RoleInfoDto roleDto);

    /**
     * 值对象与基本类型的转换方法
     * @param roleId RoleId
     * @return String roleId
     */
    @Named("roleIdToString")
    default String roleIdToString(RoleId roleId) {
        return roleId != null ? roleId.getValue() : null;
    }

    /**
     * 值对象与基本类型的转换方法
     * @param id String roleId
     * @return RoleId
     */
    @Named("stringToRoleId")
    default RoleId stringToRoleId(String id) {
        return id != null ? RoleId.of(id) : null;
    }

    /**
     * 值对象与基本类型的转换方法
     * @param roleCode RoleCode
     * @return String
     */
    @Named("roleCodeToString")
    default String roleCodeToString(RoleCode roleCode) {
        return roleCode != null ? roleCode.getValue() : null;
    }

    /**
     * 值对象与基本类型的转换方法
     * @param roleCode String
     * @return RoleCode
     */
    @Named("stringToRoleCode")
    default RoleCode stringToRoleCode(String roleCode) {
        return roleCode != null ? RoleCode.of(roleCode) : null;
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
