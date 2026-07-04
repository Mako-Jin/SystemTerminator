package com.yaocode.sts.auth.domain.entity;

import com.yaocode.sts.auth.domain.constants.AuthI18nKeyConstants;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ContactId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ResourceId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.Email;
import com.yaocode.sts.auth.domain.valueobjects.primitives.PhoneNum;
import com.yaocode.sts.common.basic.enums.YesNoEnums;
import lombok.Getter;

import java.util.Objects;

/**
 * 资源联系人实体（属于资源聚合）
 * 对应表：auth_tbl_resource_contact
 */
@Getter
public class ResourceContactEntity {

    private final ContactId contactId;
    private final ResourceId resourceId;
    private String contactName;
    private String docsUrl;
    private Email contactEmail;
    private PhoneNum contactPhone;
    private YesNoEnums isPrimary;

    private ResourceContactEntity(ContactId contactId, ResourceId resourceId) {
        this.contactId = contactId;
        this.resourceId = resourceId;
        this.isPrimary = YesNoEnums.NO;
    }

    // ========== 工厂方法 ==========

    public static ResourceContactEntity create(
            ResourceId resourceId,
            String contactName,
            String docsUrl,
            Email contactEmail,
            PhoneNum contactPhone
    ) {
        ResourceContactEntity entity = new ResourceContactEntity(ContactId.nextId(), resourceId);
        entity.contactName = contactName;
        entity.docsUrl = docsUrl;
        entity.contactEmail = contactEmail;
        entity.contactPhone = contactPhone;
        return entity;
    }

    public static ResourceContactEntity reconstruct(
            ContactId contactId,
            ResourceId resourceId,
            String contactName,
            String docsUrl,
            Email contactEmail,
            PhoneNum contactPhone,
            YesNoEnums isPrimary
    ) {
        ResourceContactEntity entity = new ResourceContactEntity(contactId, resourceId);
        entity.contactName = contactName;
        entity.docsUrl = docsUrl;
        entity.contactEmail = contactEmail;
        entity.contactPhone = contactPhone;
        entity.isPrimary = isPrimary != null ? isPrimary : YesNoEnums.NO;
        return entity;
    }

    // ========== 业务行为 ==========

    public void updateContactName(String contactName) {
        if (contactName == null || contactName.trim().isEmpty()) {
            throw new IllegalArgumentException(AuthI18nKeyConstants.EMERGENCY_CONTACT_NAME_CANNOT_BE_BLANK);
        }
        this.contactName = contactName.trim();
    }

    public void updateDocsUrl(String docsUrl) {
        this.docsUrl = docsUrl;
    }

    public void updateContactEmail(Email contactEmail) {
        this.contactEmail = contactEmail;
    }

    public void updateContactPhone(PhoneNum contactPhone) {
        this.contactPhone = contactPhone;
    }

    public void markPrimary() {
        this.isPrimary = YesNoEnums.YES;
    }

    public void unmarkPrimary() {
        this.isPrimary = YesNoEnums.NO;
    }

    public boolean isPrimaryContact() {
        return isPrimary == YesNoEnums.YES;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResourceContactEntity that = (ResourceContactEntity) o;
        return Objects.equals(contactId, that.contactId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contactId);
    }
}