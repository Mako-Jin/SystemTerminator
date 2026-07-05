package com.yaocode.sts.auth.domain.valueobjects.primitives;

import com.yaocode.sts.auth.domain.constants.AuthI18nKeyConstants;
import com.yaocode.sts.auth.domain.constants.AuthDomainConstants;
import com.yaocode.sts.auth.domain.constants.RegexConstants;
import com.yaocode.sts.common.domain.valueobject.Identifier;
import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * UserName值对象
 * @author: Jin-LiangBo
 * @date: 2025年10月12日 11:43
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class Username extends Identifier<String> {

    /**
     * TODO 这些应该改到租户配置里面去
     */
    private static final int USERNAME_MIN_LENGTH = AuthDomainConstants.USERNAME_MIN_LENGTH;
    private static final int USERNAME_MAX_LENGTH = AuthDomainConstants.USERNAME_MAX_LENGTH;
    private static final String USERNAME_REGEX = RegexConstants.USERNAME_PATTERN;

    private Username(String value) {
        super(value);
    }

    public static Username of(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(AuthI18nKeyConstants.USERNAME_CANNOT_BE_BLANK);
        }
        if (value.length() < USERNAME_MIN_LENGTH || value.length() > USERNAME_MAX_LENGTH) {
            throw new IllegalArgumentException(AuthI18nKeyConstants.USERNAME_RULE_CHECK_ERROR);
        }
        if (!value.matches(USERNAME_REGEX)) {
            throw new IllegalArgumentException(AuthI18nKeyConstants.USERNAME_RULE_CHECK_ERROR);
        }
        return new Username(value);
    }

}