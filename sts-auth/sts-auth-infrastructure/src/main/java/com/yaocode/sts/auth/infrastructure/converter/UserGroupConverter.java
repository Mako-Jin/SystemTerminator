package com.yaocode.sts.auth.infrastructure.converter;

import com.yaocode.sts.auth.domain.entity.UserGroupEntity;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserGroupId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.UserGroupCode;
import com.yaocode.sts.auth.infrastructure.po.UserGroupPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Objects;

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
    @Mapping(target = "parentId", source = "userGroupEntity.parentId", qualifiedByName = "userGroupIdToString")
    UserGroupPo toPo(UserGroupEntity userGroupEntity);

    /**
     * Po转entity
     * @param userGroupPo UserGroupPo
     * @return UserGroupEntity
     */
    default UserGroupEntity toEntity(UserGroupPo userGroupPo) {
        if (Objects.isNull(userGroupPo)) {
            return null;
        }
        UserGroupId userGroupId = UserGroupId.of(userGroupPo.getUserGroupId());
        UserGroupCode code = UserGroupCode.of(userGroupPo.getUserGroupCode());
        UserGroupId parentId = userGroupPo.getParentId() != null ?
                UserGroupId.of(userGroupPo.getParentId()) : null;
        TenantId tenantId = TenantId.of(userGroupPo.getTenantId());
        return UserGroupEntity.build(
                userGroupId, code, userGroupPo.getUserGroupName(),
                userGroupPo.getUserGroupDesc(), parentId,
                tenantId, userGroupPo.getIsEnabled()
        );
    }

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
