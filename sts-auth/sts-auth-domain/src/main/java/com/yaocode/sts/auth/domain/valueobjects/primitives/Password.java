package com.yaocode.sts.auth.domain.valueobjects.primitives;

import com.yaocode.sts.common.domain.model.Identifier;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.regex.Pattern;

/**
 * 密码值对象
 * @author: Jin-LiangBo
 * @date: 2025年10月13日 22:35
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class Password extends Identifier<String> {

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@!%*?&.]){8,20}$");

    private static final Pattern CHINESE_PATTERN = Pattern.compile("[\\u4e00-\\u9fa5]");

    protected Password(String value) {
        super(value);
    }

    public static Password fromPlainText(String plainText) {
        validatePassword(plainText);
        return new Password(encrypt(plainText));
    }

    public static Password fromEncrypted(String encrypted) {
        return new Password(encrypted);
    }

    private static void validatePassword(String password) {
        if (CHINESE_PATTERN.matcher(password).find() || PASSWORD_PATTERN.matcher(password).matches()) {
            throw new IllegalArgumentException("auth.password.rule.check.error");
        }
    }

    public boolean verify(String plainText) {
        return this.getValue().equals(encrypt(plainText));
    }

    private static String encrypt(String plainText) {
        // 实际加密逻辑
        return "encrypted_" + plainText;
    }

}
