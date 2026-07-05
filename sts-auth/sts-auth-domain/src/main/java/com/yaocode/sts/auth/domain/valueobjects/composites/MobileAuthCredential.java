package com.yaocode.sts.auth.domain.valueobjects.composites;

import com.yaocode.sts.auth.domain.constants.AuthI18nKeyConstants;
import com.yaocode.sts.auth.domain.constants.AuthDomainConstants;
import com.yaocode.sts.auth.domain.enums.GrantTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.AbstractAuthCredential;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ClientId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.DeviceId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.PhoneNum;
import com.yaocode.sts.auth.domain.valueobjects.primitives.VerifyCode;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.Objects;

/**
 * 移动端认证凭证
 * @author: Jin-LiangBo
 * @date: 2026年03月31日 16:08
 */
@EqualsAndHashCode(callSuper = true)
@Value
public class MobileAuthCredential extends AbstractAuthCredential {

    /**
     * 手机号（值对象）
     */
    PhoneNum phoneNumber;

    /**
     * 验证码（值对象）
     */
    VerifyCode verifyCode;

    /**
     * 记住我
     */
    Boolean rememberMe;

    /**
     * 是否自动注册新用户
     */
    boolean autoRegister;

    /**
     * 国际区号（可选，默认+86）
     */
    String countryCode;

    /**
     * 简化构造函数（最小必要参数）
     */
    public MobileAuthCredential(
            PhoneNum phoneNumber,
            VerifyCode verifyCode,
            ClientId clientId,
            DeviceId deviceId
    ) {
        this(phoneNumber, verifyCode, clientId, deviceId, false, true, null);
    }

    /**
     * 全参构造函数
     */
    public MobileAuthCredential(
            PhoneNum phoneNumber,
            VerifyCode verifyCode,
            ClientId clientId,
            DeviceId deviceId,
            Boolean rememberMe,
            boolean autoRegister,
            String countryCode
    ) {
        super(GrantTypeEnums.MOBILE, clientId, deviceId);
        this.phoneNumber = phoneNumber;
        this.verifyCode = verifyCode;
        this.rememberMe = rememberMe;
        this.autoRegister = autoRegister;
        this.countryCode = countryCode != null ? countryCode : AuthDomainConstants.DEFAULT_COUNTRY_CODE;
    }

    /**
     * 凭证校验
     */
    @Override
    public void validate() {
        if (Objects.isNull(phoneNumber)) {
            throw new IllegalArgumentException(AuthI18nKeyConstants.PHONE_NUMBER_CANNOT_BE_BLANK);
        }
        if (Objects.isNull(verifyCode)) {
            throw new IllegalArgumentException(AuthI18nKeyConstants.VERIFY_CODE_CANNOT_BE_BLANK);
        }
        if (Objects.isNull(clientId)) {
            throw new IllegalArgumentException(AuthI18nKeyConstants.CLIENT_ID_CANNOT_BE_BLANK);
        }
        // 值对象内部自校验
        phoneNumber.validate(phoneNumber.getValue());
        verifyCode.validate(verifyCode.getValue());
    }

}