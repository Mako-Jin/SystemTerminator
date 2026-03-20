package com.yaocode.sts.auth.infrastructure.converter;

import com.yaocode.sts.auth.domain.entity.UserInfoEntity;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.OrganizationId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.RoleId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.TenantId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserGroupId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.Email;
import com.yaocode.sts.auth.domain.valueobjects.primitives.Password;
import com.yaocode.sts.auth.domain.valueobjects.primitives.PhoneNum;
import com.yaocode.sts.auth.domain.valueobjects.primitives.Username;
import com.yaocode.sts.auth.infrastructure.po.UserInfoPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * 用户信息类型转换
 * @author: Jin-LiangBo
 * @date: 2025年10月07日 23:42
 */
@Mapper(componentModel = "spring")
public interface UserInfoConverter {

    UserInfoConverter INSTANCE = Mappers.getMapper(UserInfoConverter.class);

    /**
     * entity转Po
     * @param user UserInfoEntity
     * @return UserInfoPo
     */
    @Mapping(target = "userId", source = "user.id", qualifiedByName = "userIdToString")
    @Mapping(target = "username", source = "user.username", qualifiedByName = "usernameToString")
    @Mapping(target = "email", source = "user.email", qualifiedByName = "emailToString")
    @Mapping(target = "phoneNum", source = "user.phoneNum", qualifiedByName = "phoneNumToString")
    UserInfoPo toPo(UserInfoEntity user);

    /**
     * entity转Po
     * @param userPo UserInfoPo
     * @param tenantIdList 租户id列表
     * @param organizationIdList 组织id列表
     * @param roleIdList 角色id列表
     * @param userGroupIdList 用户组id列表
     * @return UserInfoEntity
     */
    default UserInfoEntity toEntity(
            UserInfoPo userPo,
            List<String> tenantIdList,
            List<String> organizationIdList,
            List<String> roleIdList,
            List<String> userGroupIdList
    ) {
        return UserInfoEntity.build(
                stringToUserId(userPo.getUserId()),
                stringToUsername(userPo.getUsername()),
                stringToTenantId(tenantIdList),
                stringToOrganizationId(organizationIdList),
                stringToRoleId(roleIdList),
                stringToUserGroupId(userGroupIdList),
                stringToEmail(userPo.getEmail()),
                stringToPhoneNum(userPo.getPhoneNum()),
                userPo.getIsEnabled()
        );
    }

    /**
     * 值对象与基本类型的转换方法
     * @param userGroupIdList List<String>
     * @return List<UserGroupId> userGroupIdList
     */
    @Named("stringListToUserGroupIdList")
    default List<UserGroupId> stringToUserGroupId(List<String> userGroupIdList) {
        return CollectionUtils.isEmpty(userGroupIdList)
                ? Collections.emptyList()
                : userGroupIdList.stream().map(this::stringToUserGroupId).toList();
    }

    /**
     * 值对象与基本类型的转换方法
     * @param userGroupId String
     * @return UserGroupId userGroupId
     */
    @Named("stringToUserGroupId")
    default UserGroupId stringToUserGroupId(String userGroupId) {
        return userGroupId != null ? UserGroupId.of(userGroupId) : null;
    }

    /**
     * 值对象与基本类型的转换方法
     * @param roleIdList List<String>
     * @return List<RoleId> roleIdList
     */
    @Named("stringListToRoleIdList")
    default List<RoleId> stringToRoleId(List<String> roleIdList) {
        return CollectionUtils.isEmpty(roleIdList)
                ? Collections.emptyList()
                : roleIdList.stream().map(this::stringToRoleId).toList();
    }

    /**
     * 值对象与基本类型的转换方法
     * @param roleId String
     * @return RoleId roleId
     */
    @Named("stringToOrganizationIdId")
    default RoleId stringToRoleId(String roleId) {
        return roleId != null ? RoleId.of(roleId) : null;
    }

    /**
     * 值对象与基本类型的转换方法
     * @param organizationIdList List<String>
     * @return List<OrganizationId> organizationIdList
     */
    @Named("stringListToOrganizationIdList")
    default List<OrganizationId> stringToOrganizationId(List<String> organizationIdList) {
        return CollectionUtils.isEmpty(organizationIdList)
                ? Collections.emptyList()
                : organizationIdList.stream().map(this::stringToOrganizationId).toList();
    }

    /**
     * 值对象与基本类型的转换方法
     * @param organizationId String
     * @return OrganizationId organizationId
     */
    @Named("stringToOrganizationIdId")
    default OrganizationId stringToOrganizationId(String organizationId) {
        return organizationId != null ? OrganizationId.of(organizationId) : null;
    }

    /**
     * 值对象与基本类型的转换方法
     * @param tenantIdList List<String>
     * @return List<TenantId> tenantIdList
     */
    @Named("stringListToTenantIdList")
    default List<TenantId> stringToTenantId(List<String> tenantIdList) {
        return CollectionUtils.isEmpty(tenantIdList)
                ? Collections.emptyList()
                : tenantIdList.stream().map(this::stringToTenantId).toList();
    }

    /**
     * 值对象与基本类型的转换方法
     * @param tenantId String
     * @return TenantId tenantId
     */
    @Named("stringToTenantId")
    default TenantId stringToTenantId(String tenantId) {
        return tenantId != null ? TenantId.of(tenantId) : null;
    }

    /**
     * 值对象与基本类型的转换方法
     * @param userId UserId
     * @return String userId
     */
    @Named("userIdToString")
    default String userIdToString(UserId userId) {
        return userId != null ? userId.getValue() : null;
    }

    /**
     * 值对象与基本类型的转换方法
     * @param id String userId
     * @return UserId
     */
    @Named("stringToUserId")
    default UserId stringToUserId(String id) {
        return id != null ? UserId.of(id) : null;
    }

    /**
     * 值对象与基本类型的转换方法
     * @param username Username
     * @return String
     */
    @Named("usernameToString")
    default String usernameToString(Username username) {
        return username != null ? username.getValue() : null;
    }

    /**
     * 值对象与基本类型的转换方法
     * @param username String
     * @return Username
     */
    @Named("stringToUsername")
    default Username stringToUsername(String username) {
        return username != null ? Username.of(username) : null;
    }

    /**
     * 值对象与基本类型的转换方法
     * @param password Password
     * @return String
     */
    @Named("passwordToString")
    default String passwordToString(Password password) {
        return password != null ? password.getValue() : null;
    }

    /**
     * 值对象与基本类型的转换方法
     * @param password String
     * @return Password
     */
    @Named("stringToPassword")
    default Password stringToPassword(String password) {
        return password != null ? Password.fromPlainText(password) : null;
    }

    /**
     * 值对象与基本类型的转换方法
     * @param email Email
     * @return String
     */
    @Named("emailToString")
    default String emailToString(Email email) {
        return email != null ? email.getValue() : null;
    }

    /**
     * 值对象与基本类型的转换方法
     * @param email String
     * @return Email
     */
    @Named("stringToEmail")
    default Email stringToEmail(String email) {
        return email != null ? Email.of(email) : null;
    }

    /**
     * 值对象与基本类型的转换方法
     * @param phoneNum PhoneNum
     * @return String
     */
    @Named("phoneNumToString")
    default String phoneNumToString(PhoneNum phoneNum) {
        return phoneNum != null ? phoneNum.getValue() : null;
    }

    /**
     * 值对象与基本类型的转换方法
     * @param phoneNum String
     * @return PhoneNum
     */
    @Named("stringToPhoneNum")
    default PhoneNum stringToPhoneNum(String phoneNum) {
        return phoneNum != null ? PhoneNum.of(phoneNum) : null;
    }

}
