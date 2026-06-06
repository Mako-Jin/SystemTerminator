package com.yaocode.sts.auth.domain.rules;

import java.util.regex.Pattern;

/**
 * 邮箱验证码规则
 * <p>
 * 6-8位字母+数字组合，至少包含一个字母和一个数字，区分大小写
 * </p>
 *
 * @author: Jin-LiangBo
 * @date: 2026年06月06日
 */
public class EmailVerifyCodeRule implements VerifyCodeRule {

    private static final Pattern PATTERN = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,8}$");
    private static final int DEFAULT_LENGTH = 8;
    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    @Override
    public boolean validate(String code) {
        return code != null && PATTERN.matcher(code).matches();
    }

    @Override
    public String getDescription() {
        return "6-8位字母和数字组合";
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
