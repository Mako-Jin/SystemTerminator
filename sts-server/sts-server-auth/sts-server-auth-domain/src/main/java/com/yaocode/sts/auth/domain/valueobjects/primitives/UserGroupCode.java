package com.yaocode.sts.auth.domain.valueobjects.primitives;

import com.yaocode.sts.auth.domain.constants.AuthI18nKeyConstants;
import com.yaocode.sts.auth.domain.constants.RegexConstants;
import com.yaocode.sts.common.domain.valueobject.Identifier;
import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年10月28日 21:56
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class UserGroupCode extends Identifier<String> {

    private UserGroupCode(String value) {
        super(value);
    }

    public static UserGroupCode of (String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(AuthI18nKeyConstants.USER_GROUP_CODE_CANNOT_BE_BLANK);
        }
        if (!RegexConstants.CODE_PATTERN_COMPILED.matcher(value).matches()) {
            throw new IllegalArgumentException(AuthI18nKeyConstants.CODE_FORMAT_INVALID);
        }
        return new UserGroupCode(value);
    }

}