package com.yaocode.sts.common.domain.valueobject;

import com.yaocode.sts.common.domain.constants.DomainI18nKeyConstants;
import com.yaocode.sts.common.domain.constants.RegexConstants;
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
            throw new IllegalArgumentException(DomainI18nKeyConstants.TENANT_CODE_CANNOT_BE_BLANK);
        }
        if (!RegexConstants.CODE_PATTERN_COMPILED.matcher(value).matches()) {
            throw new IllegalArgumentException(DomainI18nKeyConstants.CODE_FORMAT_INVALID);
        }
        return new TenantCode(value);
    }

}