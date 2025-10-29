package com.yaocode.sts.auth.domain.valueobjects.primitives;

import com.yaocode.sts.auth.domain.constants.CommonConstants;
import com.yaocode.sts.common.domain.model.Identifier;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年10月28日 21:56
 */
public class UserGroupCode extends Identifier<String> {

    protected UserGroupCode(String value) {
        super(value);
    }

    public static UserGroupCode of (String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("用户组编码不能为空");
        }
        if (!CommonConstants.IDENTIFIER_CODE_REGEX.matcher(value).matches()) {
            throw new IllegalArgumentException("编码只支持大小写字母、数字和中横线");
        }
        return new UserGroupCode(value);
    }

}
