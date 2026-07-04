package com.yaocode.sts.auth.domain.valueobjects.identifiers;

import com.yaocode.sts.auth.domain.constants.AuthI18nKeyConstants;
import com.yaocode.sts.common.domain.valueobject.Identifier;
import com.yaocode.sts.common.tools.id.IdFactory;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.util.StringUtils;

@Data
@EqualsAndHashCode(callSuper = true)
public class TenantConfigId extends Identifier<String> {

    private TenantConfigId(String value) {
        super(value);
    }

    public static TenantConfigId of(String value) {
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException(AuthI18nKeyConstants.IDENTIFIER_VALUE_CANNOT_BE_NULL);
        }
        return new TenantConfigId(value);
    }

    public static TenantConfigId nextId() {
        return new TenantConfigId(IdFactory.generate());
    }

}