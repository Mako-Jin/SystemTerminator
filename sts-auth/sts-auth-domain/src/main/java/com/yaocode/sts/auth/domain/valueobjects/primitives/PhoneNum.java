package com.yaocode.sts.auth.domain.valueobjects.primitives;

import com.yaocode.sts.auth.domain.constants.AuthI18nKeyConstants;
import com.yaocode.sts.auth.domain.constants.CommonConstants;
import com.yaocode.sts.auth.domain.constants.RegexConstants;
import com.yaocode.sts.common.basic.constants.SymbolConstants;
import com.yaocode.sts.common.domain.valueobject.Identifier;
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

    private static final Pattern PHONE_PATTERN = RegexConstants.PHONE_CHINA_PATTERN_COMPILED;

    private PhoneNum(String value) {
        super(value);
    }


    public static PhoneNum of(String value) {

        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(AuthI18nKeyConstants.PHONE_NUMBER_CANNOT_BE_BLANK);
        }

        String normalized = normalizePhoneNumber(value);

        if (!PHONE_PATTERN.matcher(normalized).matches()) {
            throw new IllegalArgumentException(AuthI18nKeyConstants.PHONE_NUMBER_FORMAT_INVALID);
        }
        return new PhoneNum(normalized);
    }

    private static String normalizePhoneNumber(String phoneNumber) {
        String normalized = phoneNumber.trim().replaceAll(RegexConstants.WHITESPACE_PATTERN, SymbolConstants.EMPTY_STR);

        // 移除国际前缀
        if (normalized.startsWith(CommonConstants.COUNTRY_CODE_PLUS)) {
            normalized = normalized.substring(3);
        } else if (normalized.startsWith(CommonConstants.COUNTRY_CODE_DOUBLE_ZERO)) {
            normalized = normalized.substring(4);
        } else if (normalized.startsWith(CommonConstants.COUNTRY_CODE_SIMPLE)) {
            normalized = normalized.substring(2);
        }

        return normalized;
    }

    public String getMaskedNumber() {
        return this.getValue().replaceFirst(RegexConstants.PHONE_MASK_PATTERN, RegexConstants.PHONE_MASK_REPLACEMENT);
    }

}