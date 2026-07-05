package com.yaocode.sts.auth.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yaocode.sts.common.infrastructure.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 用户MFA凭证表
 * 支持一个用户绑定多个MFA设备/方式
 */
@Data
@TableName("auth_tbl_user_mfa")
@EqualsAndHashCode(callSuper = true)
public class UserMfaPo extends BasePo {

    @TableId
    private String mfaId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * MFA类型：TOTP-时间型动态码, SMS-短信验证码, EMAIL-邮箱验证码,
     * WEBAUTHN-生物识别(FIDO2), RECOVERY-恢复码
     */
    private Integer mfaType;

    /**
     * 密钥/种子（加密存储）
     * TOTP场景：base32编码的密钥
     * SMS/EMAIL场景：可留空
     */
    private String secretKey;

    /**
     * 绑定的联系方式ID（关联UserContactPo）
     * SMS/EMAIL场景使用
     */
    private String contactId;

    /**
     * 设备名称（用户自定义，如：我的iPhone、备用设备）
     */
    private String deviceName;

    /**
     * 恢复码（加密存储，一次性展示后加密保存）
     */
    private String recoveryCodes;

    /**
     * 是否已验证（首次绑定后需验证一次）
     */
    private Integer isVerified;

    /**
     * 是否为主MFA设备
     */
    private Integer isPrimary;

    /**
     * 状态：ACTIVE-启用, INACTIVE-禁用, LOCKED-锁定
     */
    private Integer status;

    /**
     * 最后使用时间
     */
    private LocalDateTime lastUsedAt;

    /**
     * 使用次数（用于审计）
     */
    private Integer useCount;
}