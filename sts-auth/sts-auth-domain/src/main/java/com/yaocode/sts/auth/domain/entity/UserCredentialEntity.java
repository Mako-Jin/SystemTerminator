package com.yaocode.sts.auth.domain.entity;

import com.yaocode.sts.auth.domain.enums.CredentialTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.CredentialId;
import com.yaocode.sts.common.basic.enums.OppositeEnums;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import com.yaocode.sts.common.domain.valueobject.UserId;
import lombok.Getter;

import java.util.Objects;

/**
 * 用户证件实体（属于用户聚合）
 * 对应表：auth_tbl_user_credential
 */
@Getter
public class UserCredentialEntity {

    private final CredentialId credentialId;
    private final UserId userId;
    private TenantId tenantId;
    private CredentialTypeEnums credentialType;
    private String encryptedCredentialNumber;  // AES加密存储
    private String credentialName;
    private String issueDate;
    private String expireDate;
    private OppositeEnums isPrimary;
    private String credentialImage;

    private UserCredentialEntity(CredentialId credentialId, UserId userId) {
        this.credentialId = credentialId;
        this.userId = userId;
        this.isPrimary = OppositeEnums.NO;
    }

    // ========== 工厂方法 ==========

    public static UserCredentialEntity create(
            UserId userId,
            TenantId tenantId,
            CredentialTypeEnums credentialType,
            String encryptedNumber,
            String credentialName,
            String issueDate,
            String expireDate,
            String credentialImage
    ) {
        UserCredentialEntity entity = new UserCredentialEntity(CredentialId.nextId(), userId);
        entity.tenantId = tenantId;
        entity.credentialType = credentialType;
        entity.encryptedCredentialNumber = encryptedNumber;
        entity.credentialName = credentialName;
        entity.issueDate = issueDate;
        entity.expireDate = expireDate;
        entity.credentialImage = credentialImage;
        return entity;
    }

    public static UserCredentialEntity reconstruct(
            CredentialId credentialId,
            UserId userId,
            TenantId tenantId,
            CredentialTypeEnums credentialType,
            String encryptedCredentialNumber,
            String credentialName,
            String issueDate,
            String expireDate,
            OppositeEnums isPrimary,
            String credentialImage
    ) {
        UserCredentialEntity entity = new UserCredentialEntity(credentialId, userId);
        entity.tenantId = tenantId;
        entity.credentialType = credentialType;
        entity.encryptedCredentialNumber = encryptedCredentialNumber;
        entity.credentialName = credentialName;
        entity.issueDate = issueDate;
        entity.expireDate = expireDate;
        entity.isPrimary = isPrimary != null ? isPrimary : OppositeEnums.NO;
        entity.credentialImage = credentialImage;
        return entity;
    }

    // ========== 业务行为 ==========

    public void markPrimary() {
        this.isPrimary = OppositeEnums.YES;
    }

    public void unmarkPrimary() {
        this.isPrimary = OppositeEnums.NO;
    }

    public boolean isPrimaryCredential() {
        return isPrimary == OppositeEnums.YES;
    }

    public boolean isExpired() {
        if (expireDate == null) return false;
        return expireDate.compareTo(java.time.LocalDate.now().toString()) < 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCredentialEntity that = (UserCredentialEntity) o;
        return Objects.equals(credentialId, that.credentialId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(credentialId);
    }
}
