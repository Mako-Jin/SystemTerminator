package com.yaocode.sts.auth.application.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 租户配置 DTO
 */
@Data
@Builder
public class TenantConfigDto {

    /**
     * 租户 ID
     */
    private String tenantId;

    /**
     * 机构/公司名称
     */
    private String institution;

    /**
     * 登录页标题
     */
    private String loginTitle;

    /**
     * 系统名称
     */
    private String systemName;

    /**
     * Logo URL
     */
    private String logoUrl;

    /**
     * 登录页背景图 URL
     */
    private String loginBackgroundUrl;

    /**
     * 版权信息
     */
    private String copyright;

    /**
     * 主题色（十六进制）
     */
    private String primaryColor;

    /**
     * 是否启用自定义 CSS
     */
    private Boolean customCssEnabled = false;

    /**
     * 自定义 CSS 内容
     */
    private String customCss;

    /**
     * 登录页提示信息
     */
    private String loginNotice;

    // ===== 租户基本信息 =====
    private String tenantName;
    private String tenantCode;
    private String tenantLogo;

    // ===== 品牌配置 =====
    private String brandName;
    private String welcomeMessage;
    private String subtitle;

    // ===== 登录方式 =====
    /**
     * 支持的登录方式
     * PASSWORD-密码, SMS-短信, EMAIL-邮箱, QRCODE-扫码, SOCIAL-社交
     */
    private List<String> loginMethods;
    /**
     * 默认登录方式
     */
    private String defaultLoginMethod;

    // ===== 安全状态 =====
    /**
     * 是否被锁定
     */
    private Boolean isLocked;
    /**
     * 锁定原因
     */
    private String lockReason;
    /**
     * 剩余尝试次数
     */
    private Integer remainingAttempts;
    /**
     * 解锁时间
     */
    private String unlockTime;
    /**
     * 是否需要验证码
     */
    private Boolean captchaRequired;
    /**
     * 验证码类型
     */
    private String captchaType;
    /**
     * 是否需要MFA
     */
    private Boolean mfaRequired;
    /**
     * 是否已绑定MFA
     */
    private Boolean mfaBound;
    /**
     * 是否信任设备
     */
    private Boolean isTrustedDevice;

    // ===== 注册相关 =====
    /**
     * 是否允许自助注册
     */
    private Boolean selfRegisterEnabled;
    /**
     * 是否允许忘记密码
     */
    private Boolean forgotPasswordEnabled;

}
