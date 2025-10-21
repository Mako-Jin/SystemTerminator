package com.yaocode.sts.auth.application.converter;

import com.yaocode.sts.auth.application.dto.UserInfoDto;
import com.yaocode.sts.auth.domain.entity.UserInfoEntity;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.RoleId;
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
    UserInfoDto toDto(UserInfoEntity user);

    /**
     * Dto转entity
     * @param user dto
     * @return UserInfoEntity
     */
    @Mapping(target = "userId", source = "user.userId", qualifiedByName = "stringToUserId")
    @Mapping(target = "username", source = "user.username", qualifiedByName = "stringToUsername")
    // @Mapping(target = "password", source = "user.password", qualifiedByName = "stringToPassword")
    @Mapping(target = "email", source = "user.email", qualifiedByName = "stringToEmail")
    @Mapping(target = "phoneNum", source = "user.phoneNum", qualifiedByName = "stringToPhoneNum")
    @Mapping(target = "roleIdList", source = "user.roleIdList", qualifiedByName = "stringListToRoleIdList")
    UserInfoEntity toEntity(UserInfoDto user);

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
    default List<RoleId> stringListToUserIdList(List<String> stringList) {
        if (stringList == null) {
            return null;
        }
        return stringList.stream()
                .map(this::stringToRoleId)
                .collect(Collectors.toList());
    }

}
