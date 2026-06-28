package com.yaocode.sts.auth.domain.entity.relation;

import com.yaocode.sts.auth.domain.enums.MFATypeEnums;
import com.yaocode.sts.common.basic.enums.OppositeEnums;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import com.yaocode.sts.common.domain.valueobject.UserId;
import com.yaocode.sts.common.tools.id.IdFactory;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class RelTenantUserEntity {

    private final Long relId;

    private final TenantId tenantId;

    private final UserId userId;

    private OppositeEnums isLocked;

    private String lockReason;

    /**
     * 用户在该租户下的头像（可覆盖全局头像）
     */
    private String tenantAvatar;

    /**
     * 用户在该租户下的昵称（可覆盖全局昵称）
     */
    private String tenantNickname;

    /**
     * 是否已绑定MFA
     */
    private OppositeEnums mfaBound;

    /**
     * MFA类型：TOTP, SMS, EMAIL
     */
    private MFATypeEnums mfaType;
    private LocalDateTime joinDate;
    private LocalDateTime leaveDate;

    private RelTenantUserEntity(Long relId, TenantId tenantId, UserId userId) {
        this.relId = relId;
        this.tenantId = tenantId;
        this.userId = userId;
        this.isLocked = OppositeEnums.NO;
        this.mfaBound = OppositeEnums.NO;
    }

    public static RelTenantUserEntity create(TenantId tenantId, UserId userId) {
        return new RelTenantUserEntity(IdFactory.generate(), tenantId, userId);
    }

    public void lock(String reason) {
        this.isLocked = OppositeEnums.YES;
        this.lockReason = reason;
    }

    public void unlock() {
        this.isLocked = OppositeEnums.NO;
        this.lockReason = null;
    }

    public void bindMFA(MFATypeEnums mfaType) {
        this.mfaBound = OppositeEnums.YES;
        this.mfaType = mfaType;
    }

    public void unbindMFA() {
        this.mfaBound = OppositeEnums.NO;
        this.mfaType = null;
    }

    public boolean isLocked() {
        return isLocked == OppositeEnums.YES;
    }

    public boolean isMfaBound() {
        return mfaBound != null && mfaBound.equals(OppositeEnums.YES);
    }

}
