package com.yaocode.sts.auth.domain.valueobjects.primitives;

import com.yaocode.sts.auth.domain.constants.AuthI18nKeyConstants;
import com.yaocode.sts.auth.domain.constants.RegexConstants;
import com.yaocode.sts.common.domain.valueobject.Identifier;
import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * 组织编码值对象
 * @author: Jin-LiangBo
 * @date: 2025年10月27日 22:18
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class OrganizationCode extends Identifier<String> {

    private OrganizationCode(String value) {
        super(value);
    }

    public static OrganizationCode of (String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(AuthI18nKeyConstants.ORGANIZATION_CODE_CANNOT_BE_BLANK);
        }
        if (!RegexConstants.CODE_PATTERN_COMPILED.matcher(value).matches()) {
            throw new IllegalArgumentException(AuthI18nKeyConstants.CODE_FORMAT_INVALID);
        }
        return new OrganizationCode(value);
    }

}