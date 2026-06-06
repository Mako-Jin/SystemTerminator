package com.yaocode.sts.auth.domain.rules;

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

    private static final Pattern PATTERN = Pattern.compile("^\\d{6}$");
    private static final int LENGTH = 6;

    @Override
    public boolean validate(String code) {
        return code != null && PATTERN.matcher(code).matches();
    }

    @Override
    public String getDescription() {
        return "6位数字";
    }

    @Override
    public String generate() {
        int code = getSecureRandom().nextInt(1000000);
        return String.format("%06d", code);
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