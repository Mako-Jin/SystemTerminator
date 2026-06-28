package com.yaocode.sts.auth.domain.valueobjects.primitives;

import com.yaocode.sts.auth.domain.constants.CommonConstants;
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
            throw new IllegalArgumentException("组织编码不能为空");
        }
        if (!CommonConstants.IDENTIFIER_CODE_REGEX.matcher(value).matches()) {
            throw new IllegalArgumentException("组织编码只支持大小写字母、数字和中横线");
        }
        return new OrganizationCode(value);
    }

}
