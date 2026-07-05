package com.yaocode.sts.auth.domain.valueobjects.primitives;

import com.yaocode.sts.auth.domain.constants.AuthI18nKeyConstants;
import com.yaocode.sts.auth.domain.constants.RegexConstants;
import com.yaocode.sts.common.domain.valueobject.Identifier;
import lombok.EqualsAndHashCode;
import lombok.Value;


/**
 * 租户编码值对象
 * @author: Jin-LiangBo
 * @date: 2025年10月17日 21:30
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class TenantCode extends Identifier<String> {

    private TenantCode(String value) {
        super(value);
    }

    public static TenantCode of (String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(AuthI18nKeyConstants.TENANT_CODE_CANNOT_BE_BLANK);
        }
        if (!RegexConstants.CODE_PATTERN_COMPILED.matcher(value).matches()) {
            throw new IllegalArgumentException(AuthI18nKeyConstants.CODE_FORMAT_INVALID);
        }
        return new TenantCode(value);
    }

}