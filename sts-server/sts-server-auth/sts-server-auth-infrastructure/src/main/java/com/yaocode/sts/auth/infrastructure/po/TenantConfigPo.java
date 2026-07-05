package com.yaocode.sts.auth.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yaocode.sts.common.infrastructure.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("auth_tbl_tenant_config")
@EqualsAndHashCode(callSuper = true)
public class TenantConfigPo extends BasePo {

    @TableId
    private String configId;

    /**
     * 租户ID
     */
    private String tenantId;

    // ==================== 密码策略 ====================

    /**
     * 是否启用密码过期策略
     */
    private Integer passwordExpiryEnabled;

    /**
     * 密码过期天数
     */
    private Integer passwordExpiryDays;

    /**
     * 密码最小长度
     */
    private Integer passwordMinLength;

    /**
     * 密码复杂度：LOW, MEDIUM, HIGH
     */
    private String passwordComplexity;

    /**
     * 密码历史记录数（防止重复使用）
     */
    private Integer passwordHistoryCount;

    // ==================== 会话策略 ====================

    /**
     * 会话超时时间（秒）
     */
    private Integer sessionTimeout;

    /**
     * 是否允许单设备登录
     */
    private Integer singleSessionEnabled;

    /**
     * 是否允许记住我
     */
    private Integer rememberMeEnabled;

    /**
     * 记住我最大有效期（天）
     */
    private Integer rememberMeMaxDays;

    // ==================== MFA 策略 ====================

    /**
     * 是否强制 MFA
     */
    private Integer mfaRequired;

    /**
     * 支持的 MFA 类型：TOTP, SMS, EMAIL
     */
    private Integer mfaTypes;

    // ==================== 登录策略 ====================

    /**
     * 最大登录失败次数
     */
    private Integer maxLoginAttempts;

    /**
     * 登录锁定时间（秒）
     */
    private Integer lockoutDuration;

    /**
     * 是否启用验证码
     */
    private Integer captchaEnabled;

    /**
     * 验证码类型：MATH, TEXT, SLIDER
     */
    private Integer captchaType;

    /**
     * 验证码触发条件：ALWAYS, FAILED, SENSITIVE
     */
    private Integer captchaTrigger;

    // ==================== 登录方式 ====================

    /**
     * 是否允许密码登录
     */
    private Integer passwordLoginEnabled;

    /**
     * 是否允许短信登录
     */
    private Integer smsLoginEnabled;

    /**
     * 是否允许邮箱登录
     */
    private Integer emailLoginEnabled;

    /**
     * 是否允许扫码登录
     */
    private Integer qrCodeLoginEnabled;

    /**
     * 是否允许自助注册
     */
    private Integer selfRegisterEnabled;

    /**
     * 是否允许忘记密码重置
     */
    private Integer forgotPasswordEnabled;

    // ==================== 状态 ====================

    /**
     * 配置状态：ACTIVE, INACTIVE
     */
    private Integer status;

}
