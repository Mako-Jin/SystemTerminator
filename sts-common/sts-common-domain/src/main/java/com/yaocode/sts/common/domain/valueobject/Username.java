package com.yaocode.sts.common.domain.valueobject;

import com.yaocode.sts.common.domain.constants.CommonDomainConstants;
import com.yaocode.sts.common.domain.constants.DomainI18nKeyConstants;
import com.yaocode.sts.common.domain.constants.RegexConstants;
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
    private static final int USERNAME_MIN_LENGTH = CommonDomainConstants.USERNAME_MIN_LENGTH;
    private static final int USERNAME_MAX_LENGTH = CommonDomainConstants.USERNAME_MAX_LENGTH;
    private static final String USERNAME_REGEX = RegexConstants.USERNAME_PATTERN;

    private Username(String value) {
        super(value);
    }

    public static Username of(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(DomainI18nKeyConstants.USERNAME_CANNOT_BE_BLANK);
        }
        if (value.length() < USERNAME_MIN_LENGTH || value.length() > USERNAME_MAX_LENGTH) {
            throw new IllegalArgumentException(DomainI18nKeyConstants.USERNAME_RULE_CHECK_ERROR);
        }
        if (!value.matches(USERNAME_REGEX)) {
            throw new IllegalArgumentException(DomainI18nKeyConstants.USERNAME_RULE_CHECK_ERROR);
        }
        return new Username(value);
    }

}