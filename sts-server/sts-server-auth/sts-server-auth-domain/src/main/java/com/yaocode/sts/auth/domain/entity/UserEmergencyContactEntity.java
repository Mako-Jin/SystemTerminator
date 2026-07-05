package com.yaocode.sts.auth.domain.entity;

import com.yaocode.sts.auth.domain.constants.AuthI18nKeyConstants;
import com.yaocode.sts.auth.domain.enums.RelationshipEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.EmergencyContactId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.PhoneNum;
import com.yaocode.sts.common.domain.valueobject.UserId;
import lombok.Getter;

import java.util.Objects;

/**
 * 用户紧急联系人实体（属于用户聚合）
 * 对应表：auth_tbl_user_emergency_contact
 */
@Getter
public class UserEmergencyContactEntity {

    private final EmergencyContactId emergencyContactId;
    private final UserId userId;
    private String contactName;
    private RelationshipEnums relationship;
    private PhoneNum phoneNum;
    private String email;          // 备用邮箱
    private Integer sortOrder;     // 紧急优先级（数字越小越优先）

    private UserEmergencyContactEntity(EmergencyContactId emergencyContactId, UserId userId) {
        this.emergencyContactId = emergencyContactId;
        this.userId = userId;
    }

    // ========== 工厂方法 ==========

    public static UserEmergencyContactEntity create(
            UserId userId,
            String contactName,
            RelationshipEnums relationship,
            PhoneNum phoneNum,
            String email,
            Integer sortOrder
    ) {
        if (contactName == null || contactName.trim().isEmpty()) {
            throw new IllegalArgumentException(AuthI18nKeyConstants.EMERGENCY_CONTACT_NAME_CANNOT_BE_BLANK);
        }
        if (relationship == null) {
            throw new IllegalArgumentException(AuthI18nKeyConstants.EMERGENCY_CONTACT_RELATIONSHIP_CANNOT_BE_BLANK);
        }
        UserEmergencyContactEntity entity = new UserEmergencyContactEntity(
                EmergencyContactId.nextId(), userId
        );
        entity.contactName = contactName.trim();
        entity.relationship = relationship;
        entity.phoneNum = phoneNum;
        entity.email = email;
        entity.sortOrder = sortOrder != null ? sortOrder : 0;
        return entity;
    }

    public static UserEmergencyContactEntity reconstruct(
            EmergencyContactId emergencyContactId,
            UserId userId,
            String contactName,
            RelationshipEnums relationship,
            PhoneNum phoneNum,
            String email,
            Integer sortOrder
    ) {
        UserEmergencyContactEntity entity = new UserEmergencyContactEntity(emergencyContactId, userId);
        entity.contactName = contactName;
        entity.relationship = relationship;
        entity.phoneNum = phoneNum;
        entity.email = email;
        entity.sortOrder = sortOrder != null ? sortOrder : 0;
        return entity;
    }

    // ========== 业务行为 ==========

    public void updateContactName(String contactName) {
        if (contactName == null || contactName.trim().isEmpty()) {
            throw new IllegalArgumentException(AuthI18nKeyConstants.EMERGENCY_CONTACT_NAME_CANNOT_BE_BLANK);
        }
        this.contactName = contactName.trim();
    }

    public void updateRelationship(RelationshipEnums relationship) {
        if (relationship == null) {
            throw new IllegalArgumentException(AuthI18nKeyConstants.EMERGENCY_CONTACT_RELATIONSHIP_CANNOT_BE_BLANK);
        }
        this.relationship = relationship;
    }

    public void updatePhoneNum(PhoneNum phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updateSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder != null ? sortOrder : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEmergencyContactEntity that = (UserEmergencyContactEntity) o;
        return Objects.equals(emergencyContactId, that.emergencyContactId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(emergencyContactId);
    }
}