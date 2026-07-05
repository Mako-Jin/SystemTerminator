package com.yaocode.sts.auth.domain.rules;

import com.yaocode.sts.auth.domain.constants.AuthI18nKeyConstants;
import com.yaocode.sts.auth.domain.constants.AuthDomainConstants;

import java.util.regex.Pattern;

/**
 * 短信验证码规则
 * <p>
 * 6位纯数字，不区分大小写
 * </p>
 *
 * @author: Jin-LiangBo
 * @date: 2026年06月06日
 */
public class SmsVerifyCodeRule implements VerifyCodeRule {

    private static final Pattern PATTERN = AuthDomainConstants.SMS_CODE_PATTERN;
    private static final int LENGTH = AuthDomainConstants.SMS_CODE_LENGTH;

    @Override
    public boolean validate(String code) {
        return code != null && PATTERN.matcher(code).matches();
    }

    @Override
    public String getDescription() {
        return AuthI18nKeyConstants.SMS_CODE_DESCRIPTION;
    }

    @Override
    public String generate() {
        int code = getSecureRandom().nextInt((int) Math.pow(10, LENGTH));
        return String.format(AuthDomainConstants.SMS_CODE_FORMAT_PATTERN, code);
    }

    @Override
    public int getLength() {
        return LENGTH;
    }

    @Override
    public boolean isCaseSensitive() {
        return false;
    }
}