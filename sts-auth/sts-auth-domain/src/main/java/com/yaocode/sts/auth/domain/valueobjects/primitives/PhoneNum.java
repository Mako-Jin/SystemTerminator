package com.yaocode.sts.auth.domain.valueobjects.primitives;

import com.yaocode.sts.common.domain.model.Identifier;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.regex.Pattern;

/**
 * 手机号码值对象
 * @author: Jin-LiangBo
 * @date: 2025年10月16日 20:58
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class PhoneNum extends Identifier<String> {

    private static final Pattern PHONE_PATTERN = Pattern.compile("^1[2-9]\\d{9}$");

    protected PhoneNum(String value) {
        super(value);
    }


    public static PhoneNum of(String value) {

        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("手机号码不能为空");
        }

        String normalized = normalizePhoneNumber(value);

        if (!PHONE_PATTERN.matcher(normalized).matches()) {
            throw new IllegalArgumentException("手机号码格式不正确: " + value);
        }
        return new PhoneNum(value);
    }

    private static String normalizePhoneNumber(String phoneNumber) {
        String normalized = phoneNumber.trim().replaceAll("\\s+", "");

        // 移除国际前缀
        if (normalized.startsWith("+86")) {
            normalized = normalized.substring(3);
        } else if (normalized.startsWith("0086")) {
            normalized = normalized.substring(4);
        } else if (normalized.startsWith("86")) {
            normalized = normalized.substring(2);
        }

        return normalized;
    }

    public String getMaskedNumber() {
        return this.getValue().replaceFirst("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

}
