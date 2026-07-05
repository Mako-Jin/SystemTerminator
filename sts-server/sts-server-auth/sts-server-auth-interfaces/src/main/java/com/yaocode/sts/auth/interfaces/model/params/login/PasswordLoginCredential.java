package com.yaocode.sts.auth.interfaces.model.params.login;

import com.yaocode.sts.auth.interfaces.constants.AuthApiI18nKeyConstants;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 密码认证凭证
 * @author: Jin-LiangBo
 * @date: 2026年04月01日 11:10
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PasswordLoginCredential extends AbstractLoginCredential {

    @NotBlank(message = AuthApiI18nKeyConstants.AUTH_VALUE_OBJECT_USERNAME_CANNOT_BE_BLANK)
    private String username;

    @NotBlank(message = AuthApiI18nKeyConstants.AUTH_VALUE_OBJECT_PASSWORD_CANNOT_BE_BLANK)
    private String password;

    private Boolean rememberMe;

    private String captcha;
    private String captchaKey;

    private String state;
    private String scope;

}