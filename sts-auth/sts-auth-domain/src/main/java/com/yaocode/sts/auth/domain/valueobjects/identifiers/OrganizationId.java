package com.yaocode.sts.auth.domain.valueobjects.identifiers;

import com.yaocode.sts.auth.domain.constants.AuthI18nKeyConstants;
import com.yaocode.sts.common.domain.valueobject.Identifier;
import com.yaocode.sts.common.tools.id.IdFactory;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.util.StringUtils;

/**
 * OrganizationId值对象
 * @author: Jin-LiangBo
 * @date: 2025年10月12日 11:43
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class OrganizationId extends Identifier<String> {

    private OrganizationId(String value) {
        super(value);
    }

    public static OrganizationId of(String value) {
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException(AuthI18nKeyConstants.IDENTIFIER_VALUE_CANNOT_BE_NULL);
        }
        return new OrganizationId(value);
    }

    public static OrganizationId nextId() {
        return new OrganizationId(IdFactory.generate());
    }

}