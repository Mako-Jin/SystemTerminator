package com.yaocode.sts.auth.application.converter;

import com.yaocode.sts.auth.application.dto.UserGroupDto;
import com.yaocode.sts.auth.domain.entity.UserGroupEntity;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.TenantId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserGroupId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.UserGroupCode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年10月28日 21:55
 */
@Mapper(componentModel = "spring")
public interface UserGroupApplicationConverter {

    UserGroupApplicationConverter INSTANCE = Mappers.getMapper(UserGroupApplicationConverter.class);

    /**
     * dto转Entity
     * @param userGroupDto dto
     * @return UserGroupEntity
     */
    @Mapping(target = "userGroupId", source = "userGroupDto.userGroupId", qualifiedByName = "stringToUserGroupId")
    @Mapping(target = "userGroupCode", source = "userGroupDto.userGroupCode", qualifiedByName = "stringToUserGroupCode")
    @Mapping(target = "tenantId", source = "userGroupDto.tenantId", qualifiedByName = "stringToTenantId")
    UserGroupEntity toEntity(UserGroupDto userGroupDto);

    /**
     * 值对象与基本类型的转换方法
     * @param userGroupId UserGroupId
     * @return String userGroupId
     */
    @Named("userGroupIdToString")
    default String userGroupIdToString(UserGroupId userGroupId) {
        return userGroupId != null ? userGroupId.getValue() : null;
    }

    /**
     * 值对象与基本类型的转换方法
     * @param id String userGroupId
     * @return UserGroupId
     */
    @Named("stringToUserGroupId")
    default UserGroupId stringToUserGroupId(String id) {
        return id != null ? UserGroupId.of(id) : null;
    }

    /**
     * 值对象与基本类型的转换方法
     * @param userGroupCode UserGroupCode
     * @return String
     */
    @Named("userGroupCodeToString")
    default String userGroupCodeToString(UserGroupCode userGroupCode) {
        return userGroupCode != null ? userGroupCode.getValue() : null;
    }

    /**
     * 值对象与基本类型的转换方法
     * @param userGroupCode String
     * @return UserGroupCode
     */
    @Named("stringToUserGroupCode")
    default UserGroupCode stringToUserGroupCode(String userGroupCode) {
        return userGroupCode != null ? UserGroupCode.of(userGroupCode) : null;
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
