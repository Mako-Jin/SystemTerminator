package com.yaocode.sts.auth.domain.valueobjects.composites;

import com.yaocode.sts.auth.domain.constants.AuthI18nKeyConstants;
import com.yaocode.sts.auth.domain.enums.GrantTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.AbstractAuthCredential;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ClientId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.DeviceId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.Password;
import com.yaocode.sts.auth.domain.valueobjects.primitives.Username;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.Objects;

/**
 * 用户名密码认证方式
 * @author: Jin-LiangBo
 * @date: 2026年03月30日 19:07
 */
@EqualsAndHashCode(callSuper = true)
@Value
public class PasswordAuthCredential extends AbstractAuthCredential {

    /**
     * 用户名
     */
    Username username;

    /**
     * 密码
     */
    Password password;

    /**
     * 记住我
     */
    Boolean rememberMe;

    /**
     * 图形验证码（可选）
     */
    String captcha;

    /**
     * 图形验证码Key（可选）
     */
    String captchaKey;

    /**
     * 简化构造函数（最小必要参数）
     */
    public PasswordAuthCredential(
            Username username,
            Password password,
            ClientId clientId,
            DeviceId deviceId
    ) {
        this(username, password, clientId, deviceId, false, null, null);
    }

    public PasswordAuthCredential(
            Username username,
            Password password,
            ClientId clientId,
            DeviceId deviceId,
            Boolean rememberMe,
            String captcha,
            String captchaKey
    ) {
        super(GrantTypeEnums.PASSWORD, clientId, deviceId);
        this.username = username;
        this.password = password;
        this.rememberMe = rememberMe;
        this.captcha = captcha;
        this.captchaKey = captchaKey;
    }

    /**
     * 凭证校验
     */
    @Override
    public void validate() {
        if (Objects.isNull(username)) {
            throw new IllegalArgumentException(AuthI18nKeyConstants.USERNAME_CANNOT_BE_BLANK);
        }
        if (Objects.isNull(password)) {
            throw new IllegalArgumentException(AuthI18nKeyConstants.PASSWORD_CANNOT_BE_BLANK);
        }
        if (Objects.isNull(clientId)) {
            throw new IllegalArgumentException(AuthI18nKeyConstants.CLIENT_ID_CANNOT_BE_BLANK);
        }
        username.validate(username.getValue());
        password.validate(password.getValue());
    }
}