package com.yaocode.sts.auth.domain.valueobjects.identifiers;

import com.yaocode.sts.auth.domain.constants.AuthI18nKeyConstants;
import com.yaocode.sts.common.domain.valueobject.Identifier;
import com.yaocode.sts.common.tools.id.IdFactory;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.util.StringUtils;

@Value
@EqualsAndHashCode(callSuper = true)
public class ClientId extends Identifier<String> {

    private ClientId(String value) {
        super(value);
    }

    public static ClientId of(String value) {
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException(AuthI18nKeyConstants.IDENTIFIER_VALUE_CANNOT_BE_NULL);
        }
        return new ClientId(value);
    }

    public static ClientId nextId() {
        return new ClientId(IdFactory.generate());
    }

}