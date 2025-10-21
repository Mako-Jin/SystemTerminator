package com.yaocode.sts.auth.infrastructure.converter;

import com.yaocode.sts.auth.domain.entity.UserInfoEntity;
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
    @Mapping(target = "password", source = "user.password", qualifiedByName = "passwordToString")
    @Mapping(target = "email", source = "user.email", qualifiedByName = "emailToString")
    @Mapping(target = "phoneNum", source = "user.phoneNum", qualifiedByName = "phoneNumToString")
    UserInfoPo toPo(UserInfoEntity user);

    /**
     * entity转Po
     * @param userPo UserInfoPo
     * @return UserInfoEntity
     */
    @Mapping(target = "userId", source = "userPo.userId", qualifiedByName = "stringToUserId")
    @Mapping(target = "username", source = "userPo.username", qualifiedByName = "stringToUsername")
    @Mapping(target = "password", source = "userPo.password", qualifiedByName = "stringToPassword")
    @Mapping(target = "email", source = "userPo.email", qualifiedByName = "stringToEmail")
    @Mapping(target = "phoneNum", source = "userPo.phoneNum", qualifiedByName = "stringToPhoneNum")
    UserInfoEntity toEntity(UserInfoPo userPo);

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
