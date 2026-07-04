package com.yaocode.sts.auth.domain.entity;

import com.yaocode.sts.auth.domain.constants.AuthI18nKeyConstants;
import com.yaocode.sts.auth.domain.constants.CommonConstants;
import com.yaocode.sts.auth.domain.enums.GenderEnums;
import com.yaocode.sts.auth.domain.enums.MaritalStatusEnums;
import com.yaocode.sts.auth.domain.enums.ThemeEnums;
import com.yaocode.sts.common.domain.valueobject.UserId;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Objects;

/**
 * 用户档案实体（属于用户聚合）
 * 对应表：auth_tbl_user_profile
 */
@Getter
public class UserProfileEntity {

    private final UserId userId;              // 与用户ID相同
    private String displayName;
    private String realName;
    private GenderEnums gender;
    private LocalDate birthDate;
    private MaritalStatusEnums maritalStatus;
    private String avatar;
    private String locale;
    private String timeZone;
    private ThemeEnums theme;
    private String extraAttributes;           // JSON格式扩展

    private UserProfileEntity(UserId userId) {
        this.userId = userId;
        this.gender = GenderEnums.UNKNOWN;
        this.maritalStatus = MaritalStatusEnums.UNKNOWN;
        this.theme = ThemeEnums.LIGHT;
        this.locale = CommonConstants.DEFAULT_LOCALE;
        this.timeZone = CommonConstants.DEFAULT_TIME_ZONE;
    }

    // ========== 工厂方法 ==========

    public static UserProfileEntity create(UserId userId) {
        return new UserProfileEntity(userId);
    }

    public static UserProfileEntity create(
            UserId userId,
            String displayName,
            String realName,
            GenderEnums gender,
            LocalDate birthDate,
            MaritalStatusEnums maritalStatus,
            String avatar,
            String locale,
            String timeZone,
            ThemeEnums theme,
            String extraAttributes
    ) {
        UserProfileEntity entity = new UserProfileEntity(userId);
        entity.displayName = displayName;
        entity.realName = realName;
        entity.gender = gender != null ? gender : GenderEnums.UNKNOWN;
        entity.birthDate = birthDate;
        entity.maritalStatus = maritalStatus != null ? maritalStatus : MaritalStatusEnums.UNKNOWN;
        entity.avatar = avatar;
        entity.locale = locale != null ? locale : CommonConstants.DEFAULT_LOCALE;
        entity.timeZone = timeZone != null ? timeZone : CommonConstants.DEFAULT_TIME_ZONE;
        entity.theme = theme != null ? theme : ThemeEnums.LIGHT;
        entity.extraAttributes = extraAttributes;
        return entity;
    }

    // ========== 业务行为 ==========

    public void updateDisplayName(String displayName) {
        if (displayName == null || displayName.trim().isEmpty()) {
            throw new IllegalArgumentException(AuthI18nKeyConstants.DISPLAY_NAME_CANNOT_BE_BLANK);
        }
        this.displayName = displayName.trim();
    }

    public void updateRealName(String realName) {
        this.realName = realName;
    }

    public void updateGender(GenderEnums gender) {
        this.gender = gender != null ? gender : GenderEnums.UNKNOWN;
    }

    public void updateBirthDate(LocalDate birthDate) {
        if (birthDate != null && birthDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(AuthI18nKeyConstants.BIRTH_DATE_CANNOT_BE_LATER_THAN_NOW);
        }
        this.birthDate = birthDate;
    }

    public void updateMaritalStatus(MaritalStatusEnums maritalStatus) {
        this.maritalStatus = maritalStatus != null ? maritalStatus : MaritalStatusEnums.UNKNOWN;
    }

    public void updateAvatar(String avatarUrl) {
        this.avatar = avatarUrl;
    }

    public void updateTheme(ThemeEnums theme) {
        this.theme = theme != null ? theme : ThemeEnums.LIGHT;
    }

    public void updateLocale(String locale) {
        this.locale = locale != null ? locale : CommonConstants.DEFAULT_LOCALE;
    }

    public void updateTimeZone(String timeZone) {
        this.timeZone = timeZone != null ? timeZone : CommonConstants.DEFAULT_TIME_ZONE;
    }

    public void updateExtraAttributes(String extraAttributes) {
        this.extraAttributes = extraAttributes;
    }

    public String getFullName() {
        return realName != null ? realName : displayName;
    }

    public int getAge() {
        if (birthDate == null) return 0;
        return LocalDate.now().getYear() - birthDate.getYear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProfileEntity that = (UserProfileEntity) o;
        return Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}