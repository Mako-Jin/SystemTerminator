package com.yaocode.sts.auth.application.dto.response;

import com.yaocode.sts.auth.application.dto.UserInfoDto;
import lombok.Data;

import java.util.List;

@Data
public class PreLoginResponseDto {

    // ========== 认证状态 ==========
    /**
     * 是否已自动登录成功
     */
    private Boolean isAuthenticated = false;

    /**
     * 访问令牌（自动登录成功时返回）
     */
    private String accessToken;

    /**
     * 刷新令牌
     */
    private String refreshToken;

    /**
     * 记住我令牌
     */
    private String rememberMeToken;

    /**
     * 状态令牌（用于防止CSRF和会话绑定）
     */
    private String stateToken;

    /**
     * 用户信息（自动登录成功时返回）
     */
    private UserInfoDto userInfoDto;

    // ========== 登录页面配置 ==========
    /**
     * 登录会话ID（用于跟踪登录尝试次数）
     */
    private String sessionId;

    /**
     * 当前登录失败次数
     */
    private Integer failCount;

    /**
     * 是否已绑定MFA设备
     */
    private Boolean mfaBound;

    /**
     * 是否启用记住我功能
     */
    private Boolean rememberMeEnabled;

    /**
     * 是否需要验证码
     */
    private Boolean captchaEnabled;

    /**
     * 验证码类型（图形/短信/语音）
     */
    private String captchaType;

    /**
     * 验证码key（用于获取验证码）
     */
    private String captchaKey;

    /**
     * 是否启用MFA
     */
    private Boolean mfaEnabled;

    /**
     * OTP类型（TOTP/HOTP）
     */
    private String otpType;

    /**
     * OTP时间间隔（秒）
     */
    private Integer otpInterval;

    /**
     * 是否启用Kerberos认证
     */
    private Boolean kerberosEnabled;

    /**
     * 是否启用Passkey
     */
    private Boolean passkeyEnabled;

    /**
     * Passkey允许的来源
     */
    private List<String> passkeyAllowedOrigins;

    /**
     * 社交登录提供商列表（如WECHAT, ALIPAY, GOOGLE等）
     */
    private List<String> socialProviders;

    /**
     * 机构信息
     */
    private String institution;

    /**
     * Kerberos代理列表
     */
    private List<String> kerberosProxies;

    /**
     * Logo URL
     */
    private String logoUrl;

    /**
     * 版权信息
     */
    private String copyright;

    /**
     * 登录页标题
     */
    private String loginTitle;

    /**
     * 是否启用注册功能
     */
    private Boolean registerEnabled;

    /**
     * 是否启用忘记密码功能
     */
    private Boolean forgotPasswordEnabled;

    /**
     * 是否启用短信登录
     */
    private Boolean smsLoginEnabled;

}
