package com.yaocode.sts.auth.interfaces.model.params.login;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 移动端登录认证凭证
 * @author: Jin-LiangBo
 * @date: 2026年04月01日 11:11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MobileLoginCredential extends AbstractLoginCredential {

    @NotBlank(message = "手机号不能为空")
    private String phoneNum;

    @NotBlank(message = "验证码不能为空")
    private String verifyCode;

    // ========== 登录选项 ==========
    private Boolean rememberMe;   // 记住我

    // ========== OAuth2 参数 ==========
    private String state;
    private String scope;

    // 可选：国际区号
    private String countryCode;      // 默认 +86

    // 可选：短信会话ID（防重放）
    private String smsSessionId;

    private Boolean autoRegister;    // 是否自动注册新用户

}
