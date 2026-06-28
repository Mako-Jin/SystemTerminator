package com.yaocode.sts.auth.domain.entity;

import com.yaocode.sts.auth.domain.enums.ContactTypeEnums;
import com.yaocode.sts.auth.domain.enums.SocialContactTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ContactId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.Email;
import com.yaocode.sts.auth.domain.valueobjects.primitives.PhoneNum;
import com.yaocode.sts.common.basic.enums.YesNoEnums;
import com.yaocode.sts.common.domain.valueobject.UserId;
import lombok.Getter;

import java.util.Objects;

/**
 * 用户联系方式实体（属于用户聚合）
 * 对应表：auth_tbl_user_contact
 */
@Getter
public class UserContactEntity {

    private final ContactId contactId;
    private final UserId userId;
    private ContactTypeEnums contactType;
    private SocialContactTypeEnums socialType;    // 仅当 contactType = SOCIAL 时使用
    private String contactValue;
    private YesNoEnums isVerified;
    private YesNoEnums isPrimary;
    private String remark;

    private UserContactEntity(ContactId contactId, UserId userId) {
        this.contactId = contactId;
        this.userId = userId;
        this.isVerified = YesNoEnums.NO;
        this.isPrimary = YesNoEnums.NO;
    }

    // ========== 工厂方法 ==========

    /**
     * 创建手机联系方式
     */
    public static UserContactEntity createMobile(UserId userId, PhoneNum phoneNum) {
        UserContactEntity entity = new UserContactEntity(ContactId.nextId(), userId);
        entity.contactType = ContactTypeEnums.MOBILE;
        entity.contactValue = phoneNum.getValue();
        return entity;
    }

    /**
     * 创建邮箱联系方式
     */
    public static UserContactEntity createEmail(UserId userId, Email email) {
        UserContactEntity entity = new UserContactEntity(ContactId.nextId(), userId);
        entity.contactType = ContactTypeEnums.EMAIL;
        entity.contactValue = email.getValue();
        return entity;
    }

    /**
     * 创建座机联系方式
     */
    public static UserContactEntity createPhone(UserId userId, String phone) {
        UserContactEntity entity = new UserContactEntity(ContactId.nextId(), userId);
        entity.contactType = ContactTypeEnums.TELEPHONE;
        entity.contactValue = phone;
        return entity;
    }

    /**
     * 创建社交联系方式
     */
    public static UserContactEntity createSocial(
            UserId userId,
            SocialContactTypeEnums socialType,
            String account
    ) {
        UserContactEntity entity = new UserContactEntity(ContactId.nextId(), userId);
        entity.contactType = ContactTypeEnums.SOCIAL;
        entity.socialType = socialType;
        entity.contactValue = account;
        return entity;
    }

    public static UserContactEntity reconstruct(
            ContactId contactId,
            UserId userId,
            ContactTypeEnums contactType,
            SocialContactTypeEnums socialType,
            String contactValue,
            YesNoEnums isVerified,
            YesNoEnums isPrimary,
            String remark
    ) {
        UserContactEntity entity = new UserContactEntity(contactId, userId);
        entity.contactType = contactType;
        entity.socialType = socialType;
        entity.contactValue = contactValue;
        entity.isVerified = isVerified != null ? isVerified : YesNoEnums.NO;
        entity.isPrimary = isPrimary != null ? isPrimary : YesNoEnums.NO;
        entity.remark = remark;
        return entity;
    }

    // ========== 业务行为 ==========

    public void verify() {
        this.isVerified = YesNoEnums.YES;
    }

    public void markPrimary() {
        this.isPrimary = YesNoEnums.YES;
    }

    public void unmarkPrimary() {
        this.isPrimary = YesNoEnums.NO;
    }

    public void updateRemark(String remark) {
        this.remark = remark;
    }

    public boolean isMobile() {
        return contactType == ContactTypeEnums.MOBILE;
    }

    public boolean isEmail() {
        return contactType == ContactTypeEnums.EMAIL;
    }

    public boolean isSocial() {
        return contactType == ContactTypeEnums.SOCIAL;
    }

    public boolean isVerified() {
        return isVerified == YesNoEnums.YES;
    }

    public boolean isPrimaryContact() {
        return isPrimary == YesNoEnums.YES;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserContactEntity that = (UserContactEntity) o;
        return Objects.equals(contactId, that.contactId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contactId);
    }
}
