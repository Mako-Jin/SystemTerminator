package com.yaocode.sts.auth.infrastructure.converter;

import com.yaocode.sts.auth.domain.entity.RoleInfoEntity;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.RoleId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.TenantId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.RoleCode;
import com.yaocode.sts.auth.infrastructure.po.RoleInfoPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年10月25日 11:52
 */
@Mapper(componentModel = "spring")
public interface RoleInfoConverter {

    RoleInfoConverter INSTANCE = Mappers.getMapper(RoleInfoConverter.class);

    /**
     * entity转Po
     * @param roleInfoEntity RoleInfoEntity
     * @return RoleInfoPo
     */
    @Mapping(target = "roleId", source = "roleInfoEntity.id", qualifiedByName = "roleIdToString")
    @Mapping(target = "roleCode", source = "roleInfoEntity.roleCode", qualifiedByName = "roleCodeToString")
    @Mapping(target = "tenantId", source = "roleInfoEntity.tenantId", qualifiedByName = "tenantIdToString")
    RoleInfoPo toPo(RoleInfoEntity roleInfoEntity);

    /**
     * Po转entity
     * @param roleInfoPo RoleInfoPo
     * @return RoleInfoPo
     */
    @Mapping(target = "roleId", source = "roleInfoPo.roleId", qualifiedByName = "stringToRoleId")
    @Mapping(target = "roleCode", source = "roleInfoPo.roleCode", qualifiedByName = "stringToRoleCode")
    @Mapping(target = "tenantId", source = "roleInfoPo.tenantId", qualifiedByName = "stringToTenantId")
    RoleInfoEntity toEntity(RoleInfoPo roleInfoPo);

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
     * @param roleId String roleId
     * @return RoleId
     */
    @Named("stringToRoleId")
    default RoleId stringToRoleId(String roleId) {
        return roleId != null ? RoleId.of(roleId) : null;
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
     * @param tenantId String tenantId
     * @return TenantId
     */
    @Named("stringToTenantId")
    default TenantId stringToTenantId(String tenantId) {
        return tenantId != null ? TenantId.of(tenantId) : null;
    }

}
