package com.yaocode.sts.auth.domain.enums;

import com.yaocode.sts.auth.domain.rules.CaptchaVerifyCodeRule;
import com.yaocode.sts.auth.domain.rules.EmailVerifyCodeRule;
import com.yaocode.sts.auth.domain.rules.SmsVerifyCodeRule;
import com.yaocode.sts.auth.domain.rules.VerifyCodeRule;
import lombok.Getter;

/**
 * 验证码类型枚举
 */
@Getter
public enum VerifyCodeTypeEnums {

    LOGIN("登录验证码", new SmsVerifyCodeRule()),
    REGISTER("注册验证码", new SmsVerifyCodeRule()),
    BIND("绑定手机验证码", new SmsVerifyCodeRule()),
    UNBIND("解绑验证码", new SmsVerifyCodeRule()),
    RESET_PWD("重置密码验证码", new EmailVerifyCodeRule()),
    PAY("支付验证码", new SmsVerifyCodeRule()),
    CAPTCHA("图形验证码", new CaptchaVerifyCodeRule());

    private final VerifyCodeRule rule;  // 每个枚举持有自己的规则
    private final String description;

    VerifyCodeTypeEnums(
        String description,
        VerifyCodeRule rule
    ) {
        this.rule = rule;
        this.description = description;
    }

}