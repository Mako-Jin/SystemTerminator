package com.yaocode.sts.auth.infrastructure.converter;

import com.yaocode.sts.auth.domain.entity.UserGroupEntity;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.TenantId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserGroupId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.UserGroupCode;
import com.yaocode.sts.auth.infrastructure.po.UserGroupPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年10月29日 21:47
 */
@Mapper(componentModel = "spring")
public interface UserGroupConverter {

    UserGroupConverter INSTANCE = Mappers.getMapper(UserGroupConverter.class);

    /**
     * entity转Po
     * @param userGroupEntity UserGroupEntity
     * @return UserGroupPo
     */
    @Mapping(target = "userGroupId", source = "userGroupEntity.id", qualifiedByName = "userGroupIdToString")
    @Mapping(target = "userGroupCode", source = "userGroupEntity.userGroupCode", qualifiedByName = "userGroupCodeToString")
    @Mapping(target = "tenantId", source = "userGroupEntity.tenantId", qualifiedByName = "tenantIdToString")
    UserGroupPo toPo(UserGroupEntity userGroupEntity);

    /**
     * Po转entity
     * @param userGroupPo UserGroupPo
     * @return UserGroupEntity
     */
    @Mapping(target = "userGroupId", source = "userGroupPo.userGroupId", qualifiedByName = "stringToUserGroupId")
    @Mapping(target = "userGroupCode", source = "userGroupPo.userGroupCode", qualifiedByName = "stringToUserGroupCode")
    @Mapping(target = "tenantId", source = "userGroupPo.tenantId", qualifiedByName = "stringToTenantId")
    UserGroupEntity toEntity(UserGroupPo userGroupPo);

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
     * @param userGroupId String userGroupId
     * @return UserGroupId
     */
    @Named("stringToUserGroupId")
    default UserGroupId stringToUserGroupId(String userGroupId) {
        return userGroupId != null ? UserGroupId.of(userGroupId) : null;
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
     * @param tenantId String tenantId
     * @return TenantId
     */
    @Named("stringToTenantId")
    default TenantId stringToTenantId(String tenantId) {
        return tenantId != null ? TenantId.of(tenantId) : null;
    }

}
