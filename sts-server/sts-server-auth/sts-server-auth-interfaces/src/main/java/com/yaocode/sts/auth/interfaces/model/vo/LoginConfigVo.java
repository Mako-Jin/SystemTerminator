package com.yaocode.sts.auth.interfaces.model.vo;

import lombok.Builder;
import lombok.Data;
import java.util.List;

/**
 * 登录配置VO
 */
@Data
@Builder
public class LoginConfigVo {

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 租户名称
     */
    private String tenantName;
    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 支持的登录方式
     * PASSWORD-密码登录, SMS-短信登录, EMAIL-邮箱登录,
     * QRCODE-扫码登录, SOCIAL-社交登录
     */
    private List<String> loginMethods;

    /**
     * 默认登录方式
     */
    private String defaultLoginMethod;

    // ========== 品牌定制 ==========

    /**
     * 品牌名称
     */
    private String brandName;
    /**
     * 租户Logo
     */
    private String tenantLogo;

    /**
     * Logo URL
     */
    private String logoUrl;

    /**
     * 登录页标题
     */
    private String loginTitle;
    /**
     * 登录页欢迎语
     */
    private String welcomeMessage;
    /**
     * 登录页副标题
     */
    private String subtitle;
    /**
     * 机构名称
     */
    private String institution;

    /**
     * 版权信息
     */
    private String copyright;

    /**
     * 主题色
     */
    private String primaryColor;

    /**
     * 登录页背景图
     */
    private String loginBackgroundUrl;

    // ========== 安全状态 ==========

    /**
     * 是否启用验证码
     */
    private Boolean captchaEnabled;

    /**
     * 是否启用MFA
     */
    private Boolean mfaRequired;

    /**
     * 是否已绑定MFA（当前用户）
     */
    private Boolean mfaBound;

    // ========== 注册相关 ==========

    /**
     * 是否允许自助注册
     */
    private Boolean selfRegisterEnabled;

    /**
     * 是否允许忘记密码
     */
    private Boolean forgotPasswordEnabled;

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
     * 验证码类型：MATH-数学, TEXT-文本, SLIDER-滑块
     */
    private String captchaType;

    /**
     * 是否信任设备
     */
    private Boolean isTrustedDevice;
}
