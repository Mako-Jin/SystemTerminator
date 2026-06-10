package com.yaocode.sts.auth.interfaces.model.vo;

import java.util.List;

public class PreLoginVo {

    // ========== 核心：CSRF 防护令牌 ==========
    private String state;
    private Integer stateExpiresIn;  // state 有效期（秒）

    // ========== 登录功能开关配置 ==========
    private Boolean rememberMeEnabled;      // 是否开启"记住我"功能
    private Boolean captchaEnabled;         // 是否需要验证码
    private Boolean mfaEnabled;             // 是否开启 MFA
    private Boolean kerberosEnabled;        // 是否开启 Kerberos
    private Boolean passkeyEnabled;         // 是否开启 Passkey

    // ========== 验证码配置 ==========
    private String captchaType;             // 验证码类型: NONE / MATH / CHAR / SLIDER
    private String captchaKey;              // 验证码key（如果需要预获取）

    // ========== MFA 配置 ==========
    private String otpType;                 // OTP类型: TOTP / HOTP / SMS
    private Integer otpInterval;            // OTP间隔（秒）

    // ========== Passkey 配置 ==========
    private List<String> passkeyAllowedOrigins;  // 允许的 Passkey 来源

    // ========== 社交登录配置 ==========
//    private List<SocialProviderResult> socialProviders;

    // ========== 机构/租户信息 ==========
//    private InstitutionResult institution;

    // ========== Kerberos 配置 ==========
//    private List<KerberosProxyResult> kerberosProxies;

    // ========== 其他UI配置 ==========
    private String logoUrl;                 // 登录页Logo
    private String copyright;               // 版权信息
    private String loginTitle;              // 登录页标题
    /**
     * 服务端时间戳（秒）
     * 用于前端同步时间，避免客户端时间不准导致的问题
     */
    private Long serverTimestamp;

    /**
     * 会话ID
     * 用于后续登录请求关联同一个会话
     */
    private String sessionId;

    /**
     * 是否需要绑定 MFA
     * 区别于 mfaEnabled（系统开关），这个表示当前用户是否已绑定MFA
     */
    private Boolean mfaBound;

    /**
     * 当前登录失败次数
     * 用于前端提示或触发验证码
     */
    private Integer loginFailCount;

    /**
     * 最大失败次数（超过后需要验证码或锁定）
     */
    private Integer maxFailCount;

    /**
     * 账号是否被锁定
     */
    private Boolean accountLocked;

    /**
     * 锁定剩余时间（秒）
     */
    private Integer lockRemainingSeconds;

    /**
     * 注册入口是否开放
     */
    private Boolean registerEnabled;

    /**
     * 忘记密码入口是否开放
     */
    private Boolean forgotPasswordEnabled;

    /**
     * 短信/邮件登录是否支持
     */
    private Boolean smsLoginEnabled;

    /**
     * 隐私政策URL
     */
    private String privacyPolicyUrl;

    /**
     * 用户协议URL
     */
    private String termsOfServiceUrl;
}
