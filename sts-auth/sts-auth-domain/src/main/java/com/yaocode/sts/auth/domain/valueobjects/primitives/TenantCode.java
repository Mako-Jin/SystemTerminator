package com.yaocode.sts.auth.domain.valueobjects.primitives;

import com.yaocode.sts.common.domain.model.Identifier;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.regex.Pattern;

/**
 * 租户编码值对象
 * @author: Jin-LiangBo
 * @date: 2025年10月17日 21:30
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class TenantCode extends Identifier<String> {

    private static final Pattern TENANT_CODE_REGEX = Pattern.compile("^[a-zA-Z0-9-]+$");

    protected TenantCode(String value) {
        super(value);
    }

    public static TenantCode of (String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("租户编码不能为空");
        }
        if (!TENANT_CODE_REGEX.matcher(value).matches()) {
            throw new IllegalArgumentException("租户编码只支持大小写字母、数字和中横线");
        }
        return new TenantCode(value);
    }

}
