package com.yaocode.sts.auth.domain.rules;

import com.yaocode.sts.auth.domain.constants.AuthI18nKeyConstants;
import com.yaocode.sts.auth.domain.constants.AuthDomainConstants;

import java.util.regex.Pattern;

/**
 * 图形验证码规则
 * <p>
 * 4-6位字母+数字，区分大小写
 * </p>
 *
 * @author: Jin-LiangBo
 * @date: 2026年06月06日
 */
public class CaptchaVerifyCodeRule implements VerifyCodeRule {

    private static final Pattern PATTERN = AuthDomainConstants.CAPTCHA_CODE_PATTERN;
    private static final int DEFAULT_LENGTH = AuthDomainConstants.CAPTCHA_DEFAULT_LENGTH;
    private static final String CHARS = AuthDomainConstants.CAPTCHA_CHARS;

    @Override
    public boolean validate(String code) {
        return code != null && PATTERN.matcher(code).matches();
    }

    @Override
    public String getDescription() {
        return AuthI18nKeyConstants.CAPTCHA_CODE_DESCRIPTION;
    }

    @Override
    public String generate() {
        return generateRandomCode(DEFAULT_LENGTH);
    }

    @Override
    public int getLength() {
        return DEFAULT_LENGTH;
    }

    @Override
    public boolean isCaseSensitive() {
        return true;
    }

    private String generateRandomCode(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARS.charAt(getSecureRandom().nextInt(CHARS.length())));
        }
        return sb.toString();
    }
}