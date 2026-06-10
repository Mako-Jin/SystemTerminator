package com.yaocode.sts.auth.domain.valueobjects.composites;

import com.yaocode.sts.auth.domain.valueobjects.identifiers.ClientId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.DeviceId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.Password;
import com.yaocode.sts.auth.domain.valueobjects.primitives.Username;
import com.yaocode.sts.common.domain.valueobject.UserId;
import lombok.Builder;
import lombok.Value;

/**
 * 认证令牌值对象
 * 封装认证请求的所有参数
 *
 * @author: Jin-LiangBo
 * @date: 2026-06-08
 */
@Value
@Builder
public class AuthenticationToken {

    // ========== 认证类型 ==========
    String grantType;           // password / sms / remember_me / mfa

    // ========== 密码登录 ==========
    Username username;
    Password password;

    // ========== 短信登录 ==========
    String mobile;
    String smsCode;

    // ========== 记住我登录 ==========
    String rememberMeJwt;

    // ========== MFA 登录 ==========
    UserId userId;
    String mfaCode;

    // ========== CSRF 防护 ==========
    String state;               // ← 必须有
    String sessionId;           // ← 必须有

    // ========== 验证码 ==========
    String captchaCode;
    String captchaKey;

    // ========== 客户端信息 ==========
    ClientId clientId;
    DeviceId deviceId;
    String ipAddress;
    String userAgent;
}
