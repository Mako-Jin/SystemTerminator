package com.yaocode.sts.auth.domain.valueobjects.primitives;

import com.yaocode.sts.auth.domain.constants.CommonConstants;
import com.yaocode.sts.common.domain.model.Identifier;

import java.util.regex.Pattern;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年10月25日 11:38
 */
public class RoleCode extends Identifier<String> {

    private static final Pattern TENANT_CODE_REGEX = Pattern.compile("^[a-zA-Z0-9-]+$");

    protected RoleCode(String value) {
        super(value);
    }

    public static RoleCode of (String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("角色编码不能为空");
        }
        if (!CommonConstants.IDENTIFIER_CODE_REGEX.matcher(value).matches()) {
            throw new IllegalArgumentException("编码只支持大小写字母、数字和中横线");
        }
        return new RoleCode(value);
    }

}
