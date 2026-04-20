package com.yaocode.sts.auth.application.converter;

import com.yaocode.sts.auth.application.dto.UserInfoDto;
import com.yaocode.sts.auth.domain.command.CreateUserCommand;
import com.yaocode.sts.auth.domain.entity.UserInfoEntity;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.OrganizationId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.RoleId;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserGroupId;
import com.yaocode.sts.common.domain.valueobject.UserId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.Email;
import com.yaocode.sts.auth.domain.valueobjects.primitives.Password;
import com.yaocode.sts.auth.domain.valueobjects.primitives.PhoneNum;
import com.yaocode.sts.auth.domain.valueobjects.primitives.Username;
import com.yaocode.sts.auth.infrastructure.po.UserInfoPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 实体和DTO转换类
 * @author: Jin-LiangBo
 * @date: 2025年10月07日 23:09
 */
@Mapper(componentModel = "spring")
public interface UserInfoApplicationConverter {

    UserInfoApplicationConverter INSTANCE = Mappers.getMapper(UserInfoApplicationConverter.class);

    /**
     * entity转Dto
     * @param user entity
     * @return UserInfoDTO
     */
    @Mapping(target = "userId", source = "user.id", qualifiedByName = "userIdToString")
    @Mapping(target = "username", source = "user.username", qualifiedByName = "usernameToString")
    // @Mapping(target = "password", source = "user.password", qualifiedByName = "passwordToString")
    @Mapping(target = "email", source = "user.email", qualifiedByName = "emailToString")
    @Mapping(target = "phoneNum", source = "user.phoneNum", qualifiedByName = "phoneNumToString")
    @Mapping(target = "roleIdList", source = "user.roleIdList", qualifiedByName = "roleIdListToStringList")
    @Mapping(target = "tenantIdList", source = "user.tenantIdList", qualifiedByName = "tenantIdListToStringList")
    @Mapping(target = "organizationIdList", source = "user.organizationIdList", qualifiedByName = "organizationIdListToStringList")
    @Mapping(target = "userGroupIdList", source = "user.userGroupIdList", qualifiedByName = "userGroupIdListToStringList")
    UserInfoDto toDto(UserInfoEntity user);

    /**
     * Dto转entity
     * @param user dto
     * @return UserInfoEntity
     */
    default UserInfoEntity toEntity(UserInfoDto user) {
        return UserInfoEntity.build(
                stringToUsername(user.getUsername()),
                stringListToTenantIdList(user.getTenantIdList()),
                stringListToOrganizationIdList(user.getOrganizationIdList()),
                stringListToRoleIdList(user.getRoleIdList()),
                stringListToUserGroupIdList(user.getUserGroupIdList()),
                stringToEmail(user.getEmail()),
                stringToPhoneNum(user.getPhoneNum()),
                user.getIsEnabled()
        );
    }

    /**
     * DTO转Command
     * @param userInfoDto dto
     * @return CreateUserCommand
     */
    default CreateUserCommand toCommand(UserInfoDto userInfoDto) {
        return CreateUserCommand.builder()
                .username(stringToUsername(userInfoDto.getUsername()))
                .tenantIdList(stringListToTenantIdList(userInfoDto.getTenantIdList()))
                .email(stringToEmail(userInfoDto.getEmail()))
                .phoneNum(stringToPhoneNum(userInfoDto.getPhoneNum()))
                .organizationIdList(stringListToOrganizationIdList(userInfoDto.getOrganizationIdList()))
                .userGroupIdList(stringListToUserGroupIdList(userInfoDto.getUserGroupIdList()))
                .roleIdList(stringListToRoleIdList(userInfoDto.getRoleIdList()))
                .build();
    }

    /**
     * entity转Po
     * @param userPo UserInfoPo
     * @return UserInfoEntity
     */
    UserInfoDto toDto(UserInfoPo userPo);

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
    // @Named("stringToPassword")
    // default Password stringToPassword(String password) {
    //     return password != null ? Password.of(password) : null;
    // }

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
     * List<RoleId> 转 List<String>
     * @param roleIdList List<RoleId>
     * @return List<String>
     */
    @Named("roleIdListToStringList")
    default List<String> roleIdListToStringList(List<RoleId> roleIdList) {
        if (roleIdList == null) {
            return null;
        }
        return roleIdList.stream()
                .map(this::roleIdToString)
                .collect(Collectors.toList());
    }

    /**
     * List<String> 转 List<RoleId>
     * @param stringList List<String>
     * @return List<RoleId>
     */
    @Named("stringListToRoleIdList")
    default List<RoleId> stringListToRoleIdList(List<String> stringList) {
        if (stringList == null) {
            return null;
        }
        return stringList.stream()
                .map(this::stringToRoleId)
                .collect(Collectors.toList());
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
     * List<String> 转 List<TenantId>
     * @param stringList List<String>
     * @return List<TenantId>
     */
    @Named("stringListToTenantIdList")
    default List<TenantId> stringListToTenantIdList(List<String> stringList) {
        if (stringList == null) {
            return null;
        }
        return stringList.stream()
                .map(this::stringToTenantId)
                .collect(Collectors.toList());
    }

    /**
     * List<TenantId> 转 List<String>
     * @param tenantIdList List<TenantId>
     * @return List<String>
     */
    @Named("tenantIdListToStringList")
    default List<String> tenantIdListToStringList(List<TenantId> tenantIdList) {
        if (tenantIdList == null) {
            return null;
        }
        return tenantIdList.stream()
                .map(this::tenantIdToString)
                .collect(Collectors.toList());
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
     * @param organizationId OrganizationId
     * @return String organizationId
     */
    @Named("organizationIdToString")
    default String organizationIdToString(OrganizationId organizationId) {
        return organizationId != null ? organizationId.getValue() : null;
    }

    /**
     * List<String> 转 List<OrganizationId>
     * @param stringList List<String>
     * @return List<OrganizationId>
     */
    @Named("stringListToOrganizationIdList")
    default List<OrganizationId> stringListToOrganizationIdList(List<String> stringList) {
        if (stringList == null) {
            return null;
        }
        return stringList.stream()
                .map(this::stringToOrganizationId)
                .collect(Collectors.toList());
    }

    /**
     * List<OrganizationId> 转 List<String>
     * @param organizationIdList List<OrganizationId>
     * @return List<String>
     */
    @Named("organizationIdListToStringList")
    default List<String> organizationIdListToStringList(List<OrganizationId> organizationIdList) {
        if (organizationIdList == null) {
            return null;
        }
        return organizationIdList.stream()
                .map(this::organizationIdToString)
                .collect(Collectors.toList());
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
     * @param userGroupId UserGroupId
     * @return String userGroupId
     */
    @Named("userGroupIdToString")
    default String userGroupIdToString(UserGroupId userGroupId) {
        return userGroupId != null ? userGroupId.getValue() : null;
    }

    /**
     * List<String> 转 List<UserGroupId>
     * @param stringList List<String>
     * @return List<UserGroupId>
     */
    @Named("stringListToUserGroupIdList")
    default List<UserGroupId> stringListToUserGroupIdList(List<String> stringList) {
        if (stringList == null) {
            return null;
        }
        return stringList.stream()
                .map(this::stringToUserGroupId)
                .collect(Collectors.toList());
    }

    /**
     * List<UserGroupId> 转 List<String>
     * @param userGroupIdList List<UserGroupId>
     * @return List<String>
     */
    @Named("userGroupIdListToStringList")
    default List<String> userGroupIdListToStringList(List<UserGroupId> userGroupIdList) {
        if (userGroupIdList == null) {
            return null;
        }
        return userGroupIdList.stream()
                .map(this::userGroupIdToString)
                .collect(Collectors.toList());
    }

}
